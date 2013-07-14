package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MapEditorHandler extends Level  {
	Container realCp;
	JPanel legende;
	public MapEditorHandler(Container realCp,Main main){
	
		super();
		this.realCp=realCp;
		realCp.setBackground(Color.WHITE);
		realCp.setLayout(new BoxLayout(realCp, BoxLayout.Y_AXIS));
		
		realCp.add(cp=new JPanel(),realCp);
		cp.setPreferredSize(new Dimension(1200,500));
		cp.setMaximumSize(new Dimension(3000,500));cp.setLayout(null);
		cp.setBackground(Color.DARK_GRAY);cp.setVisible(true);cp.repaint(16);
		player=new Architect();
		
		Architect.ArchitectController aac=((Architect)player).new ArchitectController();
		main.setFocusable(true);
		main.requestFocus();
		main.addKeyListener( aac);
		
		realCp.add(legende=new JPanel(),realCp);
		legende.setPreferredSize(new Dimension(1200,300));
		legende.setMaximumSize(new Dimension(3000,300));//cp.setLayout(null);
		legende.setBackground(Color.BLUE);legende.setVisible(true);legende.repaint(16);
		main.pack();
	}
	
	public void editRoom(int level, int roomnumber){
		this.levelNr=level;
		storeAllRooms(level);
		roomList=getAllRooms();
		Room room=roomList.get(roomnumber);
		room.startMotion();
		room.drawMap();
		room.freeze();
		setMap(room);
		player.setLocation(50,50);
		room.setLocation(cp.getWidth()/2-player.getX(),cp.getHeight()/2-player.getY());
	}

}
