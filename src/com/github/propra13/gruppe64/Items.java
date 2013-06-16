package com.github.propra13.gruppe64;

public class Items extends Sprite {
	public int plushealth;
	public int sword ; //Schwert
	public int mana;
	public int gold;
	String displayedName;
	public Items(int x ,int y ,int xd, int yd, String displayedName)// Name des Items
	{	super(x,y,xd,yd);
		this.displayedName=displayedName;
	}
	
}
