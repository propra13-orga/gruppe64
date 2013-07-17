package com.github.propra13.gruppe64.visible;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.github.propra13.gruppe64.*;



public abstract class Map implements Serializable{

	/*
	 * TODO
	 * - getMapwidth() usw. da private 
	 * - einlesen aus einer Datei
	 * - ?Siehe WIKI?
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7917755657040804742L;
	public int getSpritewidth() {
		return spritewidth;
	}
	public int getSpriteheight() {
		return spriteheight;
	}
	public boolean edit=false;
	protected int mapwidth=10;
	protected int mapheight=7;

	protected Player focusPlayer;
	protected int spritewidth=50;
	protected int spriteheight=50;
	protected char[][] mapArray;
	protected ArrayList<Movable> movables;
	protected ArrayList<Item> items;
	protected ArrayList<ActiveArea> activeAreas;
	
	// TODO sortiern nach Room etc.
	protected transient Timer hauTimer;
	protected transient TimerTask hau;
	protected transient Timer moveTimer;
	protected transient TimerTask move;
	protected transient boolean transientAreNull=false;

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

	protected transient JPanel map;
	private CopyOnWriteArrayList<Sprite> spriteArray;

	/**
	 * Erzeuge neues JPanel und ordne es an, hier kann auch das auslesen aus Datei gestartet werden
	 */

	public Map(char[][] mapArray){
		this();
		setArray(mapArray);
		getJPanel().setBounds(0, 0, mapwidth*spritewidth, mapheight*spriteheight);
		spriteArray = new CopyOnWriteArrayList<Sprite>();
		transientAreNull=true;
	}
	public Map(){
		playerList = new ArrayList<Player>();
		this.movables = new ArrayList<Movable>();
		activeAreas = new ArrayList<ActiveArea>();
		this.items = new ArrayList<Item>();
		map = new JPanel();
		getJPanel().setBackground(Color.CYAN);
		getJPanel().setLayout(null);
		getJPanel().setVisible(true);
	}
	public ArrayList<Movable> getMovables(){
		return movables;
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
			
			
			
			
			case 'r': return new Sprite (this.spritewidth, this.spriteheight, field);	
			 
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
		//if(edit){
		int x,y;
		for(int i=0; i<mapwidth;i++){
			x=i*this.spritewidth;
			for(int j=0; j< mapheight; j++){
				y=j*this.spriteheight;
				Sprite sp1 = this.getSprite(i,j);
				
				if(sp1!=null){
					sp1.setLocation(x,y);
					this.add(sp1);	
				}
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
		
		if (x<0 || (x+playersizex) > (mapwidth)*spritewidth) return 'x';
		if (y<0 || (y+playersizey) > (mapheight)*spriteheight) return 'x';
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

		else return' ';

	}
	


	
	/**
	 */
	public Sprite add(Object obj){
		Sprite sp=null;
		if(obj instanceof Sprite)
			sp = (Sprite) obj;
		else return null;
		
		getJPanel().add(sp.getSprite());
		
		if(!(sp instanceof Player))
			spriteArray.add(sp);
		sp.setMap(this);
		if(sp instanceof Movable){
			movables.add((Movable) sp);
		}
		if(sp instanceof Player){
			this.playerList.add((Player)sp);
			getJPanel().setComponentZOrder(sp.getSprite(), 0);
		}
		if(sp instanceof Item){
			items.add((Item) sp);
		}
		if(sp instanceof ActiveArea){
			if(((ActiveArea)sp).onTouchAction() || ((ActiveArea)sp).onActionAction())
				activeAreas.add((ActiveArea)sp);
		}

		return sp;
	}


	
	public void remove(Sprite sp){
		getJPanel().remove(sp.sprite);
		if(!(sp instanceof Sprite))
			return;
			
		if(sp instanceof Player){
			playerList.remove(sp);
		}
		if(sp instanceof Item){
			items.remove((Item) sp);
		}
		if(sp instanceof Movable){
			((Movable)sp).setMap(null);
			movables.remove((Movable) sp);
		}
		if(sp instanceof ActiveArea){
			if(((ActiveArea)sp).onTouchAction() || ((ActiveArea)sp).onActionAction())
				activeAreas.add((ActiveArea)sp);
		}
		
		getJPanel().repaint();
		getJPanel().revalidate();
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
				if(movables.size()>1){
					try{
						for(Movable mov:movables){		
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
					for (Movable mov: Map.this.getMovables()){
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
	public void tellAll(ActiveArea mv,String msg) {
		for(Player pl: playerList){
			pl.getChatterBox().writeChat(mv,msg);
		}
		
	}
	
	public Door getEntrance(){
		for(ActiveArea iAA: activeAreas){
			if(iAA.getClass().equals(Door.class)){Door tDoor=(Door)iAA;
				if(tDoor.getSpecial()!=null)if(tDoor.getSpecial().equals("entrance"))
					return (Door)iAA;
			}
		}return null;
	}
	public abstract void enterDoor(Door door, Player mv);
	
	public void freeze( ){
		for(Movable a:movables){
			a.movMode=Movable.modes.idle;
		}
	}
	public char[][] getArray() {
		return mapArray;
	}
	public void setArray(char[][] mapArray){
		this.mapArray = mapArray;
		//groessen 
		mapheight = mapArray.length;
		mapwidth = mapArray[0].length;
	}
	
	public boolean isCrossable(Point a, Point b){
		if(!getJPanel().contains(a)) return false;
		for(Sprite sp: spriteArray){
			if(sp.getRectangle().contains(a))if(!sp.crossable)return false;
		}
		if(!getJPanel().contains(b)) return false;
		for(Sprite sp: spriteArray){
			if(sp.getRectangle().contains(b))if(!sp.crossable)return false;
		}
		return true;
	}
	public Sprite getSpriteAt(Point a){
		for(Sprite sp: spriteArray){
			if(sp.getRectangle().contains(a))return sp;
		}return null;
	}
	public void removeAll(){
		getJPanel().removeAll();
		playerList.removeAll(playerList);
		movables.removeAll(movables);
		activeAreas.removeAll(activeAreas);
	}
	/*public Component add(Player player) {
			 getJPanel().add(player.getSprite());
			 player.setMap(this);
		return null;

	}*/
	public void remove(Player player) {
		// TODO Auto-generated method stub
		
	}
	public int getHeight() {
		// TODO Auto-generated method stub
		return getJPanel().getHeight();
	}
	public int getWidth(){
		return getJPanel().getWidth();
	}
	public void setSize(int width, int height) {
		getJPanel().setSize( width, height);
		
	}
	public int getX() {
		return getJPanel().getX();
	}
	public int getY() {
		return getJPanel().getY();
	}
	public void setLocation(int x, int y) {
		getJPanel().setLocation(x,y);
		
	}
	public JPanel getJPanel() {
		return map;
	}
	/*public void setMap(JComponent map) {
		this.map = map;
	}*/
	public Component getParent() {
		return map.getParent();
	}
	public Point getPlayerPos(){
		return new Point(focusPlayer.getX(), focusPlayer.getY());
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        map = new JPanel();
        getJPanel().setBackground(Color.CYAN);
		getJPanel().setLayout(null);
		getJPanel().setVisible(true);
		getJPanel().setBounds(0, 0, mapwidth*spritewidth, mapheight*spriteheight);
		//drawMap();
		
		for(Sprite sp: spriteArray){
			sp.readResolve();
			add(sp);
		}
    }
	public void setFocusPlayer(Player player) {
		focusPlayer=player;
		
	}
	public Player getFocusPlayer(){
		return focusPlayer; 
	}
	public void addAll(ArrayList<NPlayer> playerList){
		for(Player pl: playerList){
			this.add(pl);
		}
	}
	public void updLocalPlayer(NPlayer npl){
		Player tPlayer=null;
		for(Player iPl:playerList){
			if(iPl instanceof NPlayer){
				if(((NPlayer)iPl).clientAddress.equals(((NPlayer)npl).clientAddress))
				tPlayer=iPl;
			}
		}
		if(tPlayer!=null){
			//TODO Kopiere POS etc
			playerList.remove(tPlayer);
			movables.remove(tPlayer);
			add(npl);
		}
	}
}


