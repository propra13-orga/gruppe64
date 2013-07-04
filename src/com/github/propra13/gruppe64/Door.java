package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Graphics;

public class Door extends Sprite {
	public static int NOKEY=0;
	public static int REDKEY=1;
	public static int BLUEKEY=2;
	
	private boolean open;
	private Door tDoor;
	private int key=NOKEY;
	private int tDoorNr;
	private int doorNr;
	private String special;
	
	private static int xDim = 50;
	private static int yDim =50;
	

	public Door() {
		// TODO Auto-generated constructor stub
	}

	
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
		open=true;
	}
	public void paintComponent(Graphics g){
		
				g.setColor(Color.green); 
				g.fillRect(0, 0,Dim[0],Dim[1]);
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
	public int getKey(){
		return key;
	}
	public void setKey(int key){
		this.key=key;
	}

	public String getSpecial() {
		return special;
	}
	public boolean isOpen(){
		return open;
	}

}