package com.github.propra13.gruppe64;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;

public class Item extends Sprite {
	public int plushealth;
	
	//Schwert
	public int plusmana;
	public int gold;
	

	String displayedName;
	//Groesse des Items
	int xd=50,yd=50;
	private boolean isWeapon=false;
	private int Dmg;
	private int Range;
	private Player player;
	private int price=0;
	
	public Item( char name)// Name des Items
	
	{	
		super();
		//int xd,yd;
		lootable=true;
		switch(name){
		
		case 'S': this.displayedName="Sword"; //Schwert 
		Dmg=20;
		Range=6400;
		setWeapon(true);
		
		break;
		
		case 'M': this.displayedName="Mana";//MANA
		plusmana=10;
		break;
		
		case 'Y': this.displayedName="Gold";//Gold
		gold=50;
		break;
		
		case 'H': this.displayedName="Health";//Leben
		setPrice(50);
		plushealth=10;
		break;
		
		case 's':this.displayedName="Schlagring"; //schlagring
		Dmg=5;
		Range=1600;
		setWeapon(true);
		break;
		
		case 'p':this.displayedName="Pfote";
		Dmg=20;

		Range=800;
		setWeapon(true);
		break;
		case 'R':this.displayedName="Armor";
		price=10;
		break;

		
		default:
		lootable=false;
		break;
		}
		
		this.name=name;
		//this.rectangle = new Rectangle(0,0,xd,yd);
		this.setVisible(true);
		this.setBounds(0,0,xd,yd);
		this.setToolTipText(displayedName);
		
		
	}
	/**
	 * For cloning the logical param
	 */
	public Item(Item old){
		this(old.getSpriteName());
		//this.setLootable(old.isLootable());	
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
			Image img6 = Toolkit.getDefaultToolkit().getImage("res/s.png");
		    g.drawImage(img6, 0, 0, this);
		    g.finalize();	
		    break;
		case 'R':
			xd=50;
			yd=50;
			Image img7 = Toolkit.getDefaultToolkit().getImage("res/armor.png");
		    g.drawImage(img7, 0, 0, this);
		    g.finalize();	
		    break;
			
	
		}
		
		
		
		
		
	}
	
	public boolean isLootable(){
		return lootable;
	}
	
	public void setLootable(boolean l){
		lootable=l;
	}
	
	//Methoden
	public int getDmg(){
		return Dmg;
	}
	

	public int getRange(){
		return Range;
	}

	public boolean isWeapon() {
		return isWeapon;
	}

	public void setWeapon(boolean isWeapon) {
		this.isWeapon = isWeapon;
	}
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount()>1){
			if(player!=null){
				player.use(this);
			}
			
		}
	}
	public void setOwner(Player mov) {
		this.player = mov;
		
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
