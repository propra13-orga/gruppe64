package com.github.propra13.gruppe64.visible;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import com.github.propra13.gruppe64.ActiveArea;


public class Item extends Sprite implements ActiveArea,SpriteNames{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5927594103060083981L;

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
	private PlayerSprite player;
	private int price=0;
	
	static public int NORMAL=0,FIRE=1,ICE=2;
	public int elementtype=NORMAL;

	public Item( char name)// Name des Items
	
	{	
		this();
		//int xd,yd;
		lootable=true;
		switch(name){
		
		case 'S': this.displayedName="Sword"; //Schwert 
		Dmg=20;
		Range=6400;
		setWeapon(true);
		break;
		
		case FIRESWORD: this.displayedName="FireSword"; //Schwert 
		elementtype=FIRE;
		Dmg=20;
		Range=6400;
		setWeapon(true);
		break;
		
		case 'W': this.displayedName="IceSword"; //Schwert 
		elementtype=ICE;
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
		//elementtype=1;
		Dmg=50;
		Range=2000;
		setWeapon(true);
		break;
		
		case 'R':this.displayedName="FireArmor";
		price=10;
		break;

		case 'T':this.displayedName="IceArmor";
		price=10;
		break;
		
		default:
		lootable=false;
		break;
		}
		
		this.name=name;
		//this.rectangle = new Rectangle(0,0,xd,yd);
		sprite.setVisible(true);
		sprite.setBounds(0,0,xd,yd);
		sprite.setToolTipText(displayedName);
		
		
	}
	/**
	 * For cloning the logical param
	 */
	public Item(Item old){

		this(old.getSpriteName());
		
	}
	public Item(){
		sprite = new SpriteComponent(this);
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

	//pr��fe R��stung
	
	public boolean isArmor(){
		if(displayedName.equals("FireArmor")||displayedName.equals("IceArmor")){
			return true;
		}
		else return false;
		
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
	public void setOwner(PlayerSprite mov) {
		this.player = mov;
		
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public void onTouch(Movable mv) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAction(Movable mv) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onTouchAction() {
		//TODO map specific
		return true;
	}
	@Override
	public boolean onActionAction() {
		//TODO map specific
		return false;
	}
	@Override
	public String getNick() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setNick(String string) {
		// TODO Auto-generated method stub
		
	}
	public  void paint(Graphics g){
						
				switch(this.name){
				
				case 'S':
				
					Image img2 = Toolkit.getDefaultToolkit().getImage("res/sword.png");
				    g.drawImage(img2, 0, 0, sprite);
				    g.finalize();	
				    break;
				    
				case 'M':
					
					Image img3 = Toolkit.getDefaultToolkit().getImage("res/M.png");
				    g.drawImage(img3, 0, 0, sprite);
				    g.finalize();	
				    break;
				case 'Y':
					
					Image img4 = Toolkit.getDefaultToolkit().getImage("res/gold.png");
				    g.drawImage(img4, 0, 0, sprite);
				    g.finalize();	
				    break;
				
				case 'H':
					
					Image img5 = Toolkit.getDefaultToolkit().getImage("res/H.png");
				    g.drawImage(img5, 0, 0, sprite);
				    g.finalize();	
				    break;
				case 's':
					
					Image img6 = Toolkit.getDefaultToolkit().getImage("res/s.png");
				    g.drawImage(img6, 0, 0, sprite);
				    g.finalize();	
				    break;
			//R��stungen
				case 'R':
					
					Image img7 = Toolkit.getDefaultToolkit().getImage("res/firearmor.png");
				    g.drawImage(img7, 0, 0, sprite);
				    g.finalize();	
				    break;
				    
				case 'T':
					
					Image img8 = Toolkit.getDefaultToolkit().getImage("res/icearmor.png");
				    g.drawImage(img8, 0, 0, sprite);
				    g.finalize();	
				    break;
				 
				    
				case 'Q':
					
					Image img9 = Toolkit.getDefaultToolkit().getImage("res/firesword.png");
				    g.drawImage(img9, 0, 0, sprite);
				    g.finalize();	
				    break;
				    
				    
				case 'W':
					
					Image img10 = Toolkit.getDefaultToolkit().getImage("res/icesword.png");
				    g.drawImage(img10, 0, 0, sprite);
				    g.finalize();	
				    break;
			
				}
		
			}
	}
	

