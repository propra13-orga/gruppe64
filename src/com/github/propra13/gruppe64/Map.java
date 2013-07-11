package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;


@SuppressWarnings({ "serial"})
public abstract class Map extends JPanel {

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
	protected ArrayList<ActiveArea> activeAreas;
	
	// TODO sortiern nach Room etc.
	protected transient Timer hauTimer;
	protected transient TimerTask hau;
	protected transient Timer moveTimer;
	protected transient TimerTask move;
	

	/*  Beispiel: x_max = mapwidth = 4 und y_max = mapheight = 3:
	 * 
	 *  [0][0]   [0][1]   [0][2]  [0][3]
	 *  [1][0]   [1][1]   [1][2]  [1][3]
	 *  [2][0]   [2][1]   [2][2]  [2][3]
	 */
	//char map[][]=new char [mapwidth][mapheight];

	protected ArrayList<Player> playerList;
	protected transient Game game; //if even necessary
	protected CopyOnWriteArrayList<Door> doorList;
	/**
	 * Erzeuge neues JPanel und ordne es an, hier kann auch das auslesen aus Datei gestartet werden
	 */

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
		activeAreas = new ArrayList<ActiveArea>();
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
			case 'O':	return new NPC(field);
			case 'e':
			case 'E':  
				
			case 'x': 
			case 'X': 
			case 'a':
			case 'A': 
			
			
			
			
			case 'r': return new Sprite (Map.spritewidth, Map.spriteheight, field);	
			 
			case '(': return new Enemy(0, 0, 50, 50,field, Item.FIRE);
			case ')': return new Enemy(0, 0, 50, 50,field, Item.ICE);	
			case 'g':	
			case 'G': return new Enemy(0, 0, 50, 50,field, Item.NORMAL);
			//ITEMS
			case 'S': 
			case 'Q':
			case 'W':
			case 'Y':
			case 'M':
			case 'H': 
			case 's':
			case 'R':
			case 'T':
				return new Item (field);
			
			default: return null;
		}
	}
	
	
	public void drawMap(){
		
		int x,y;
		for(int i=0; i<mapwidth;i++){
			x=i*Map.spritewidth;
			for(int j=0; j< mapheight; j++){
				y=j*Map.spriteheight;
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
		X= (int) (x/Map.spritewidth);
		Y= (int) (y/Map.spriteheight);
		char OL = mapArray[Y][X];
		//Oben-Rechts
		X=(int)	((x+playersizex-1)/Map.spritewidth);
		Y=(int)(y/Map.spriteheight);
		char OR = mapArray[Y][X];
		//Unten-Links
		X= (int) (x/Map.spritewidth);
		Y= (int) ((y+playersizey-1)/Map.spriteheight);
		char UL = mapArray[Y][X];
		//Unten-Rechts
		X= (int) ((x+playersizex-1)/Map.spritewidth);
		Y= (int) ((y+playersizey-1)/Map.spriteheight);
		char UR = mapArray[Y][X];
		
		//Wichtig ist die Reihenfolge, was wichtiger ist oben
		if	(OL=='x' || OR=='x' || UL=='x' || UR=='x') return 'x';
		else if(OL=='a' || OR=='a' || UL=='a' || UR=='a') return 'a';
		/*else if	(OL=='e' || OR=='e' || UL=='e' || UR=='e') return 'e';
		else if (OL=='S' || OR=='S' || UL=='S' || UR=='S') return 'S';
		else if (OL=='M' || OR=='M' || UL=='M' || UR=='M') return 'M';
		else if (OL=='Y' || OR=='Y' || UL=='Y' || UR=='Y') return 'Y';
		else if (OL=='H' || OR=='H' || UL=='H' || UR=='H') return 'H';
		else if (OL=='O' || OR=='O' || UL=='O' || UR=='O') return 'O';*/
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
	@Override
	public Component add(Component sp){
		Component component=super.add(sp);
		if(!Sprite.class.isAssignableFrom(sp.getClass()))
			return component;
		Class<? extends Sprite> cClass = ((Sprite)sp).getClass();
		if(Moveable.class.isAssignableFrom(cClass)){
			((Moveable)sp).setMap();
			moveables.add((Moveable) sp);
		}
	
		if(cClass.equals(Player.class)){
			this.playerList.add((Player)sp);
			this.setComponentZOrder(sp, 0);
		}
		if(cClass.equals(Item.class)){
			items.add((Item) sp);
		}
		if(ActiveArea.class.isAssignableFrom(sp.getClass())){
			if(((ActiveArea)sp).onTouchAction() || ((ActiveArea)sp).onActionAction())
				activeAreas.add((ActiveArea)sp);
		}

		return component;
	}


	@Override
	public void remove(Component sp){
		super.remove(sp);
		if(!Sprite.class.isAssignableFrom(sp.getClass()))
			return;
		Class<? extends Sprite> cClass = ((Sprite)sp).getClass();
		
		if(cClass.equals(Player.class)){
			playerList.remove(sp);
		}
		if(cClass.equals(Item.class)){
			items.remove((Item) sp);
		}
		if(Moveable.class.isAssignableFrom(cClass)){
			((Moveable)sp).setMap();
			moveables.remove((Moveable) sp);
		}
		if(ActiveArea.class.isAssignableFrom(sp.getClass())){
			if(((ActiveArea)sp).onTouchAction() || ((ActiveArea)sp).onActionAction())
				activeAreas.add((ActiveArea)sp);
		}
		
		repaint();
		super.revalidate();
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

	
	public ActiveArea isOnActiveArea(Player pl){
		for(ActiveArea aArea: activeAreas){
			if(pl.getRectangle().intersects(aArea.getRectangle()))
				return aArea;
		}
		return null;
	}
	public void tellAll(Moveable mv,String msg) {
		for(Player pl: playerList){
			pl.getChatPane().append(mv,msg);
		}
		
	}
	abstract public void showMsg();
	
	
}


