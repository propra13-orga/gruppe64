package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


@SuppressWarnings({ "serial"})
public class Map extends JPanel {

	/*
	 * TODO
	 * - getMapwidth() usw. da private 
	 * - einlesen aus einer Datei
	 * - ?Siehe WIKI?
	 */
	
	public int getSpritewidth() {
		return spritewidth;
	}
	public int getSpriteheight() {
		return spriteheight;
	}
	
	protected int mapwidth=10;
	protected int mapheight=7;

	static protected int spritewidth=50;
	static protected int spriteheight=50;
	protected char[][] mapArray;
	protected ArrayList<Moveable> moveables;
	protected ArrayList<Item> items;
	protected ArrayList<Door> activeAreas;
	
	// TODO sortiern nach Room etc.
	protected Timer hauTimer;
	protected TimerTask hau;
	protected Timer moveTimer;
	protected TimerTask move;
	

	/*  Beispiel: x_max = mapwidth = 4 und y_max = mapheight = 3:
	 * 
	 *  [0][0]   [0][1]   [0][2]  [0][3]
	 *  [1][0]   [1][1]   [1][2]  [1][3]
	 *  [2][0]   [2][1]   [2][2]  [2][3]
	 */
	//char map[][]=new char [mapwidth][mapheight];

	protected ArrayList<Player> playerList;
	protected Game game;
	protected CopyOnWriteArrayList<Door> doorList;
	/**
	 * Erzeuge neues JPanel und ordne es an, hier kann auch das auslesen aus Datei gestartet werden
	 */
/*	public Map(int spritewidth, int spriteheight){
		this();
		this.spritewidth= spritewidth;
		this.spriteheight= spriteheight;
		//this.setBounds(0, 0, 500, 350);//this.setBounds(0, 0, mapwidth*spritewidth, mapheight*spriteheight);
		
		this.setBorder(BorderFactory.createLineBorder(Color.blue)); 
	}*/
	
//	public Map(Level aLevel, int mapW, int mapH, int spritewidth, int spriteheight){
//		this();
//
//		this.spritewidth= spritewidth;
//		this.spriteheight= spriteheight;
//		//put at 0,0 
//		this.setBounds(0, 0, mapW, mapH);
//
//	
//	}

	public Map(char[][] mapArray){
		this();
		this.mapArray = mapArray;
		//groessen 
		mapheight = mapArray.length;
		mapwidth = mapArray[0].length;
		this.setBounds(0, 0, mapwidth*spritewidth, mapheight*spriteheight);
	}
	public Map(){
		playerList = new ArrayList<Player>();
		this.moveables = new ArrayList<Moveable>();
		activeAreas = new ArrayList<Door>();
		this.items = new ArrayList<Item>();
		
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setVisible(true);
		//System.out.print("ThreadGesammt" +Thread.activeCount());
	}
	public ArrayList<Moveable> getMovables(){
		return moveables;
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	
	public Sprite getSprite(int X, int Y){
		
		//if (x>=mapwidth || x<0 || y>=mapheight || y<0) return "Auserhalb Spielfeld";
		char field=mapArray[Y][X];
		switch (field){
			case 'e':
			case 'E':  
				
			case 'x': 
			case 'X': 
			case 'a':
			case 'A': 
			case 'O':	
			
			case 'r': return new Sprite (this.spritewidth, this.spriteheight, field);	
			case 'g':	
			case 'G': return new Enemy(0, 0, 50, 50);
			//ITEMS
			case 'S': 
			case 'Y':
			case 'M':
			case 'H': 
			case 's':
			case 'R':
				return new Item (field);
			
			default: return null;
		}
	}
	
	
	public void drawMap(){
		
		int x,y;
		for(int i=0; i<mapwidth;i++){
			x=i*this.spritewidth;
			for(int j=0; j< mapheight; j++){
				y=j*this.spriteheight;
				Sprite sp1 = this.getSprite(i,j);
				
				if(sp1!=null){
					//System.out.print("("+x+","+y+")- {"+i+","+j+"}");
					sp1.setLocation(x,y);
					this.add(sp1);
					
				}
			//System.out.print("\n");
			}
		}
		

		
		
	}

	public char wouldTouch(Rectangle rect){
		return wouldTouch(rect.x,rect.y,rect.width,rect.height);
	}
	/**
	 * Gibt aus welches Spriteberuert wird
	 */
	public char wouldTouch(int x, int y, int playersizex, int playersizey) {
		int X,Y;
		
		if (x<0 || (x+playersizex) > ((mapwidth)*spritewidth)   ) return 'x';
		if (y<0 || (y+playersizey) > (mapheight*spriteheight) ) return 'x';
		//Oben-Links
		X= (int) (x/this.spritewidth);
		Y= (int) (y/this.spriteheight);
		char OL = mapArray[Y][X];
		//Oben-Rechts
		X=(int)	((x+playersizex-1)/this.spritewidth);
		Y=(int)(y/this.spriteheight);
		char OR = mapArray[Y][X];
		//Unten-Links
		X= (int) (x/this.spritewidth);
		Y= (int) ((y+playersizey-1)/this.spriteheight);
		char UL = mapArray[Y][X];
		//Unten-Rechts
		X= (int) ((x+playersizex-1)/this.spritewidth);
		Y= (int) ((y+playersizey-1)/this.spriteheight);
		char UR = mapArray[Y][X];
		
		//Wichtig ist die Reihenfolge, was wichtiger ist oben
		if	(OL=='x' || OR=='x' || UL=='x' || UR=='x') return 'x';
		else if(OL=='a' || OR=='a' || UL=='a' || UR=='a') return 'a';
		else if	(OL=='e' || OR=='e' || UL=='e' || UR=='e') return 'e';
		else if (OL=='S' || OR=='S' || UL=='S' || UR=='S') return 'S';
		else if (OL=='M' || OR=='M' || UL=='M' || UR=='M') return 'M';
		else if (OL=='Y' || OR=='Y' || UL=='Y' || UR=='Y') return 'Y';
		else if (OL=='H' || OR=='H' || UL=='H' || UR=='H') return 'H';
		else if (OL=='O' || OR=='O' || UL=='O' || UR=='O') return 'O';
		else return' ';

	}
	
	
	
	
	/*public void updateState(Moveable character) {
		char touchedSprite = wouldTouch(player.getRectangle());	
		//System.out.println(player.getVisibleRect().toString());
		
		switch(touchedSprite){
	
		case 'g': case 'G':
			game.gameOver();
		break;
		case 'a':case 'A':
			game.nextLevel();
		break;
		
	
			
		default:
		break;
		}
		
		
	}*/

	
	/**
	 *  extends the  add method from JPanel, to do specific tasks for e.g. Movables or Doors
	 */
	public Component add(Sprite sp){
		Component component=super.add(sp);
		//player.set
		Class<? extends Sprite> cClass = sp.getClass();
		if(Moveable.class.isAssignableFrom(cClass)){
			((Moveable)sp).setMap();
			moveables.add((Moveable) sp);
		}
	
		if(cClass.equals(Player.class)){
			//TODO generic
			//sp.setLocation(0,150);
			this.playerList.add((Player)sp);
		}
		if(cClass.equals(Item.class)){
			items.add((Item) sp);
		}
		if(cClass.equals(Door.class)){
			activeAreas.add((Door)sp);
		}

		return component;
	}
	
	public void remove(Player player){
		super.remove(player);
		//suche aus PlayerArray
		moveables.remove(player);
		super.revalidate();
	}
	public void remove(Sprite sprite){
		super.remove(sprite);
		super.revalidate();
	}
	public void remove(Item it){
		super.remove(it);
		//suche aus ItemArray
		items.remove(it);
		repaint();
	}
	public void remove(Moveable mov){
		super.remove(mov);
		repaint();
		moveables.remove(mov);
	}
	

	
	public int[] getPosOf(char c){
		
		int[] pos = new int[2];
		
		for (int i=0; i<mapheight; i++){
			for (int j=0; j<mapwidth; j++){
				
				if (mapArray[i][j]==c){
					pos[0]=j; pos[1]=i;
					return pos;
				}
			}
		}
		return null;
	}

	public void leaveMap(Player player) {
		remove(player);
		
	}
	public void startMotion(){
		hau = new TimerTask() {
			public void run() {
				if(moveables.size()>1){
					try{
						for(Moveable mov:moveables){		
							if(mov.getClass().equals(Enemy.class))	mov.attemptAttack();
						}
					}catch (Exception e){
						
					}
				}
			}
		};
		hauTimer=new Timer();
		hauTimer.schedule(hau, 1000, 1000);
		
		move = new TimerTask() {
			public void run() {
				try{
					for (Moveable mov: Map.this.getMovables()){
						mov.updateMot();
					
					}
				}catch (Exception e){
					
				}
			}
		};
		moveTimer = new Timer();
		moveTimer.schedule(move, 0, 5);
	}
	public void stopMotion(){
		moveTimer.cancel();
		moveTimer.purge();
		if(hau!=null){
			hau.cancel();
		}
		if(hauTimer!=null){
			hauTimer.cancel();
		}
	}

	public Map isOnOpenDoor(Player pl) {
		if (wouldTouch(pl.getRectangle())=='a'){
			return this;
		}
		return null;
	}
	public Door isOnActiveArea(Player pl){
		for(Door iDoor: activeAreas){
			if(pl.getRectangle().intersects(iDoor.getRectangle()))
				return iDoor;
		}
		return null;
	}
	
	
}


