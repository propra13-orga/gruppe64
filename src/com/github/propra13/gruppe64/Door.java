package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Door extends Sprite implements ActiveArea,PAS{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6013068930574390100L;
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
	private static int yDim =50;
	

	public Door(int x, int y, int doorNr, int leadsToNr) {
		super(x,y,xDim, yDim);
		this.doorNr=doorNr;
		this.tDoorNr=leadsToNr;
		open=true;
	}
	public Door(int x, int y, int doorNr, String special, int specialNr){
		super(x,y,xDim, yDim);
		this.doorNr=doorNr;
		this.tDoorNr=-1;
		this.special=special;
		this.specialNr=specialNr;
		open=true;
	}
	public void paintComponent(Graphics g){
		
				if(open)g.setColor(Color.green);else g.setColor(Color.red);
				g.fillRect(0, 0,Dim[0],Dim[1]);g.setColor(Color.gray); 
				g.setFont(new Font ("Arial", Font.PLAIN , 11));
				g.drawString(special+","+specialNr, 1, 10);
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

	public void setCarDir(){
		Container parent;
		if((parent=this.getParent())!=null){
			if(getX()<this.getWidth()*2){
				carDir=cd.WEST;
			}
			if(getX()>parent.getWidth()-2*this.getWidth()){
				carDir=cd.EAST;
			}
		}
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
			((Map)this.getParent()).enterDoor(this, (Player)mv);
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
			Level l=((Room)this.getParent()).level;
			if(l instanceof MapEditor)
				((MapEditor)l).showDialog(this);
		}
	}
	@Override
	public JPanel getSetupDialog(MapGenerator mg) {
		
		return null;
	}
	@Override
	public String getNick() {

		return nick;
	}
	@Override
	public void setNick(String string) {
		nick=string;
	}

}