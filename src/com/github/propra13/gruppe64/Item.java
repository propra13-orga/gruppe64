package com.github.propra13.gruppe64;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.*;

public class Item extends Sprite {
	public int plushealth;
	//Schwert
	public int mana;
	public int gold;

	String displayedName;
	//Größe des Items
	int xd,yd;
	private boolean isWeapon=false;
	private int Dmg;
	private int Range;
	
	public Item(int xd,int yd, char name)// Name des Items
	
	{	
		super(xd,yd,name);
		//int xd,yd;
		
		switch(name){
		
		case 'S': this.displayedName="Sword"; //Schwert 
		Dmg=20;
		Range=80;
		isWeapon=true;
		
		break;
		
		case 'M': this.displayedName="Mana";//MANA
		mana=10;
		break;
		
		case 'Y': this.displayedName="Gold";//Gold
		gold=10;
		break;
		
		case 'H': this.displayedName="Health";//Leben
		plushealth=10;
		break;
		
		case 's':this.displayedName="Schlagring"; //schlagring
		Dmg=5;
		Range=30;
		isWeapon=true;
		break;
		
		
		
		
		}
		this.name=name;
		//this.rectangle = new Rectangle(0,0,xd,yd);
		this.setVisible(true);
		this.setBounds(0,0,xd,yd);
		
		
	}
	
	public void paintComponent(Graphics g){
		
		switch(this.name){
		
		case 'S':
			xd=30;
			yd=30;
			Image img2 = Toolkit.getDefaultToolkit().getImage("res/sword.png");
		    g.drawImage(img2, 0, 0, this);
		    g.finalize();	
		    break;
		    
		case 'M':
			xd=30;
			yd=30;
			Image img3 = Toolkit.getDefaultToolkit().getImage("res/M.png");
		    g.drawImage(img3, 0, 0, this);
		    g.finalize();	
		    break;
		
		case 'Y':
			xd=30;
			xd=30;
			Image img4 = Toolkit.getDefaultToolkit().getImage("res/gold.png");
		    g.drawImage(img4, 0, 0, this);
		    g.finalize();	
		    break;
		
		case 'H':
			xd=50;
			yd=50;
			Image img5 = Toolkit.getDefaultToolkit().getImage("res/H.png");
		    g.drawImage(img5, 0, 0, this);
		    g.finalize();	
		    break;
		case 's':
			xd=50;
			yd=50;
			Image img6 = Toolkit.getDefaultToolkit().getImage("res/nyan_cat2.gif");
		    g.drawImage(img6, 0, 0, this);
		    g.finalize();	
		    break;
			
	
		}
		
		
		
		
		
	}
	
	//Methoden
	public int getDmg(){
		return Dmg;
	}
	

	public int getRange(){
		return Range;
	}
}
