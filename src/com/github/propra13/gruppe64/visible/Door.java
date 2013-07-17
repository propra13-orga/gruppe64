package com.github.propra13.gruppe64.visible;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.github.propra13.gruppe64.*;



public class Door extends Sprite implements ActiveArea,PAS{
	/**
	 * Unique ID zum Versionsvergleich
	 */
	private static final long serialVersionUID = 6013068930574390100L;
	
	/**
	 * Diverse Schluessel fuer die Tuer
	 */
	public static enum Key{no,red,green,blue}; 
	
	public enum cd{NONE,NORTH,EAST,SOUTH,WEST};
	public cd carDir=cd.NONE;
	
	boolean open;
	private Door tDoor;
	private Key key=Key.no;
	private int tDoorNr;
	private int doorNr;
	private String special;
	public  int specialNr;
	private String nick="Door";
	
	private static int xDim = 50;
	private static int yDim = 50;
	

	public Door(int x, int y, int doorNr, int leadsToNr) {
		this();
		this.doorNr=doorNr;
		this.tDoorNr=leadsToNr;
		open=true;
		this.name='D';
		this.setDim(xDim, yDim);
		sprite.setBounds( x, y, xDim, yDim);
		this.x=x;this.y=y;
	}
	public Door(int x, int y, int doorNr, String special, int specialNr){
		this();
		this.doorNr=doorNr;
		this.tDoorNr=-1;
		this.special=special;
		this.specialNr=specialNr;
		open=true;
		this.name='D';
		this.setDim(xDim, yDim);
		sprite.setBounds( x, y, xDim, yDim);
		this.x=x;this.y=y;
	}
	public Door(){super();
		sprite= new SpriteComponent(this);}
			
			public void paint(Graphics g){
						if(Door.this.open)g.setColor(Color.green);else g.setColor(Color.red);
						g.fillRect(0, 0,Door.this.Dim[0],Door.this.Dim[1]);g.setColor(Color.gray); 
						g.setFont(new Font ("Arial", Font.PLAIN , 11));
						g.drawString(Door.this.special+","+Door.this.specialNr, 1, 10);
						g.finalize();
			}
		
		
	
	public int getLeadToNr() {
		return tDoorNr;
	}
	public int getDoorNr() {
		return doorNr;
	}
	public void setTarget(Door pDoor) {
		tDoor=pDoor;
		
	}
	public Door getTarget(){
		if(tDoor==null)
			return this;
		return tDoor;
	}
	public Key getKey(){
		return key;
	}
	public void setKey(Key key){
		this.key=key;
	}

	public String getSpecial() {
		return special;
	}
	public boolean open(Key key){
		return open;
	}



	@Override
	public void onTouch(Movable mv) {
		if(mv instanceof Architect){
			MapEditor meh= (MapEditor)((Player)mv).getLevel();
			meh.onEditableSprite(this);
		}
		
	}


	@Override
	public void onAction(Movable mv) {

		if(Player.class.isAssignableFrom(mv.getClass())){
			map.enterDoor(this, (Player)mv);
		}

	}
	@Override
	public boolean onTouchAction() {
		return false;
	}
	@Override
	public boolean onActionAction() {
		return true;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount()>1){
			if(map instanceof Room){
			Level l=((Room)map).getFocusPlayer().getLevel();
			if(l instanceof MapEditor)
				((MapEditor)l).showDialog(this);
			}	
		}
		super.mouseClicked(arg0);
	}
	@Override
	public JPanel getSetupDialog(MapGenerator mg, MapEditor me) {
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();


		JLabel label = new JLabel("null?");
		c.anchor=GridBagConstraints.EAST;
		c.fill=GridBagConstraints.NONE;
		c.gridheight = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		pane.add( label , c);
		

		JTextField tdNr = new JTextField("tDoorNr");
		tdNr.addKeyListener(map.game.main.controller);
		tdNr.setToolTipText("tDoorNr");
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		pane.add( tdNr , c);
		
		JTextField dNr = new JTextField("DoorNr");
		dNr.addKeyListener(map.game.main.controller);
		dNr.setToolTipText("doorNr");
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 2;
		pane.add( dNr , c);

		JTextField sp = new JTextField("special");
		sp.addKeyListener(map.game.main.controller);
		sp.setToolTipText("special");
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 3;
		pane.add( sp , c);
		
		JTextField spNr = new JTextField("specialNr");
		spNr.addKeyListener(map.game.main.controller);
		spNr.setToolTipText("specialNr");
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 4;
		pane.add( spNr , c);
		
		JCheckBox cb1=new JCheckBox();
		c.gridx = 1;
		c.gridy = 1;
		pane.add( cb1 , c);
		
		JCheckBox cb2=new JCheckBox();
		c.gridx = 1;
		c.gridy = 2;
		pane.add( cb2 , c);
		
		JCheckBox cb3=new JCheckBox();
		c.gridx = 1;
		c.gridy = 3;
		pane.add( cb3 , c);
		
		JCheckBox cb4=new JCheckBox();
		c.gridx = 1;
		c.gridy = 4;
		pane.add( cb4 , c);
		
		
		JButton speichern = new JButton("speichern");
		c.gridx = 1;
		c.gridy = 5;
		pane.add(speichern, c);
	
		
		
		return pane;
	}
	@Override
	public String getNick() {

		return nick;
	}
	@Override
	public void setNick(String string) {
		nick=string;
	}
	public Room getRoom() {
		return (Room)map;
	}

}