package com.github.propra13.gruppe64;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.github.propra13.gruppe64.visible.Architect;
import com.github.propra13.gruppe64.visible.Map;
import com.github.propra13.gruppe64.visible.PAS;
import com.github.propra13.gruppe64.visible.Room;

public class MapEditor extends Level  implements ActionListener{
	Container realCp;
	JPanel legende;
	JPanel dialogBox;
	private JTextArea textArea;
	private JScrollPane textScroll;
	private JPanel controllPane;
	private JButton bLoadLevelSet;
	private JButton bCreateLevelSet;
	private JComboBox levelsetChooser;
	
	public MapEditor(Container realCp,Main main){
	
		super();
		this.realCp=realCp;
		realCp.setBackground(Color.WHITE);
		realCp.setLayout(new BoxLayout(realCp, BoxLayout.Y_AXIS));
		
		realCp.add(cp=new JPanel(),realCp);
		cp.setPreferredSize(new Dimension(1200,500));
		cp.setMaximumSize(new Dimension(3000,500));cp.setLayout(null);
		cp.setBackground(Color.DARK_GRAY);cp.setVisible(true);cp.repaint(16);
		player=new Architect();player.setLevel(this);
		
		Architect.ArchitectController aac=((Architect)player).new ArchitectController();
		main.setFocusable(true);
		main.requestFocus();
		main.addKeyListener( aac);
		
		realCp.add(legende=new JPanel(),realCp);
		legende.setPreferredSize(new Dimension(1200,300));
		legende.setMaximumSize(new Dimension(3000,300));
		legende.setLayout(new BorderLayout());
		legende.setBackground(Color.BLUE);legende.setVisible(true);legende.repaint(1000);
		main.pack();
		
		textArea = new JTextArea();
		textScroll = new JScrollPane();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setPreferredSize(null);
		textArea.setEditable(false);
		textArea.setVisible(true);
		textArea.setEnabled(true);
		textScroll.setPreferredSize(new Dimension(200,200));
		textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textScroll.setEnabled(true);
		textScroll.setVisible(true);
		textScroll.setViewportView(textArea);
		legende.add(textScroll, BorderLayout.LINE_START);
		textArea.setText(tipps);
		
		controllPane = new JPanel();
		controllPane.setPreferredSize(new Dimension(200,200));
		controllPane.setLayout(new GridBagLayout());
		levelsetChooser = generateSetchooser();
		controllPane.add(levelsetChooser,new myGBC(0,0));
		bLoadLevelSet = new JButton("load levelset"); bLoadLevelSet.addActionListener(this);
		controllPane.add(bLoadLevelSet, new myGBC(0,1));
		bCreateLevelSet = new JButton("create levelset"); bCreateLevelSet.addActionListener(this);
		controllPane.add(bCreateLevelSet, new myGBC(1,1));
		legende.add(controllPane, BorderLayout.CENTER);
		
	}
	public void loadLevel(){
		
	}
	public void editRoom(int level, int roomnumber){
		this.levelNr=level;
		storeAllRooms(level);
		roomList=getAllRooms();
		Room room=roomList.get(roomnumber);
		
		setMap(room);
		player.setLocation(50,50);
		room.setLocation(cp.getWidth()/2-player.getX(),cp.getHeight()/2-player.getY());
	}
	@Override
	public void setMap(Map map){
		super.setMap(map);
		map.startMotion();
		map.drawMap();
		map.freeze();
		aMap.add(player);
		
	}

	public void onEditableSprite(PAS pas) {
		//TODO show in Legende 
		//Door anzeige der editable Felder; Auswahl menu der tDoorNr siehe Mapgen 
		
	}
	public void setSpriteNow(char spn, int X, int Y) {
		if(aMap==null){System.out.println("no map for Architect");return;}
		char[][] oldMapArray=aMap.getArray();
		
		int oldHeight= oldMapArray.length;
		int oldWidth = oldMapArray[0].length;
		
		int newHeight = Math.max(oldMapArray.length,Y+1);
		int newWidth = Math.max(oldMapArray[0].length,X+1);
		char[][] newMapArray = new char[newHeight][newWidth];
		for(int iy=0; iy<newHeight; iy++){
			for(int ix=0; ix< newWidth; ix++){
				if(iy>=oldHeight||ix>=oldWidth){
					if(ix==X&&iy==Y)
						newMapArray[iy][ix]=spn;
					else
						newMapArray[iy][ix]=' ';
				}	
				else if(ix==X&&iy==Y)
					newMapArray[iy][ix]=spn;
				else 
					newMapArray[iy][ix]=oldMapArray[iy][ix];
			}
		}
		aMap.removeAll();
		aMap.stopMotion();
		aMap.setArray(newMapArray);System.out.println(aMap);
		aMap.drawMap();
		aMap.freeze();
		aMap.repaint();
		aMap.startMotion();
		aMap.add(player);
		/*int x=map.getX(),y=map.getY();
		//TODO map wird neu gezeichnet, mit POSadd von map
		aLevel.setMap(new Room(aLevel,newMapArray,0));
		map.drawMap();map.freeze();
		map.setLocation(x,y);*/
	}
	public void initLegende(){

	}
	public void showDialog(PAS pas) {
		JPanel dialog = pas.getSetupDialog(mg);
		dialog.setPreferredSize(new Dimension(100,100));
		dialog.setMaximumSize(new Dimension(300,300));
		legende.add(dialog,BorderLayout.LINE_END);
		
	}
	String tipps = "Bewege den Architekten usw.\n usw.";
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	private JComboBox<String> generateSetchooser() {
		 File dirFile=new File("sav/");
		 if(!dirFile.exists()){
			 dirFile.mkdir();
		 }
		 ArrayList<String> levelNames = new ArrayList<String>();
		 CopyOnWriteArrayList<String> names = new CopyOnWriteArrayList<String>(Arrays.asList(dirFile.list()));
		 for(String name : names){
			 if(!name.matches("Level[0-9]*_Room*[0-9]*.txt"))names.remove(name);else{
				for(String levelName: levelNames){
				//	if(name.contains(levelName))
				}
			 }
		 }
		 String[] levelSetnames= names.toArray(new String[0]);
		 
		return new JComboBox<String>(levelSetnames);
	}
}
