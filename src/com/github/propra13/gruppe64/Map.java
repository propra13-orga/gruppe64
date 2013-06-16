package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings({ "serial", "unused" })
public class Map extends JPanel {

	/*
	 * TODO
	 * - getMapwidth() usw. da private 
	 * - einlesen aus einer Datei
	 * - ?Siehe WIKI?
	 */
	

	protected int spritewidth;
	protected int spriteheight;
	protected char[][] mapArray;
	protected ArrayList<Moveable> moveables;
	
	
	private int mapwidth=10;
	private int mapheight=7;
	
	
	/*  Beispiel: x_max = mapwidth = 4 und y_max = mapheight = 3:
	 * 
	 *  [0][0]   [0][1]   [0][2]  [0][3]
	 *  [1][0]   [1][1]   [1][2]  [1][3]
	 *  [2][0]   [2][1]   [2][2]  [2][3]
	 */
	//char map[][]=new char [mapwidth][mapheight];
	
	/**
	 * @uml.property  name="map" multiplicity="(0 -1)" dimension="2"
	 */
	
	char map[][]={	{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
					{'x', 'x', ' ', ' ', ' ', ' ', 'x', ' ', 'x', 'x'},
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
					{'e', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'a'},
					{'x', 'x', ' ', ' ', ' ', ' ', 'x', ' ', 'x', 'x'},
					{'x', 'x', ' ', 'g', ' ', ' ', ' ', ' ', ' ', 'x'},
					{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}};
	
	char map2[][]={	{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
					{'x', ' ', ' ', 'g', ' ', ' ', ' ', ' ', ' ', 'x'},
					{'x', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', 'x'},
					{'e', ' ', ' ', 'g', 'x', ' ', 'x', 'g', ' ', 'a'},
					{'x', 'x', ' ', 'x', 'x', ' ', 'x', 'x', 'x', 'x'},
					{'x', 'x', ' ', ' ', 'x', ' ', ' ', ' ', ' ', 'x'},
					{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}};
	
	char map3[][]={	{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
					{'x', 'x', ' ', ' ', 'g', ' ', 'x', 'x', 'x', 'x'},
					{'e', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'a'},
					{'x', 'x', ' ', ' ', ' ', ' ', 'x', 'x', 'x', 'x'},
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
					{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}};
	private Player player;
	private Game game;
	private Level aLevel;
	/**
	 * Erzeuge neues JPanel und ordne es an, hier kann auch das auslesen aus Datei gestartet werden
	 */
	public Map(int spritewidth, int spriteheight){
		this();
		this.spritewidth= spritewidth;
		this.spriteheight= spriteheight;
		this.setBounds(0, 0, 600, 600);
		//System.out.print("ThreadGesammt" +Thread.activeCount());
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	public Map(Level aLevel, int mapW, int mapH, int spritewidth, int spriteheight){
		this();
		this.aLevel = aLevel;
		this.spritewidth= spritewidth;
		this.spriteheight= spriteheight;
		//put at 0,0 
		this.setBounds(0, 0, mapW, mapH);
		//System.out.print("ThreadGesammt" +Thread.activeCount());
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	public Map(int spritewidth, int spriteheight, int level, Game game){
		this();
		this.spritewidth= spritewidth;
		this.spriteheight= spriteheight;
		this.setBounds(0, 0, 600, 600);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setVisible(true);
		
		this.game=game;
		
		switch(level){
		case 1: break;
		case 2: this.map=this.map2; break;
		case 3: this.map=this.map3; break;
		}
	}
	public Map(char[][] mapArray){
		
	}
	public Map(){
		this.moveables = new ArrayList<Moveable>();
	}
	public ArrayList<Moveable> getMovables(){
		return moveables;
	}
	
	//gibt Name des Feldes bei (x,y) zurueck
	public  String getField(int x, int y){
		
		if (x>=mapwidth || x<0 || y>=mapheight || y<0) return "Auserhalb Spielfeld";
		
		switch (map[y][x]){
			case ' ': return "frei";
			case 'x': 
			case 'X': return "versperrt";
			case 'e':
			case 'E': return "eingang";
			case 'a':
			case 'A': return "ausgang";
			case 'g':
			case 'G': return "gegener";
			default: return "undefiniertes Terrain";
		}
		
	}
	
	
	public Sprite getSprite(int X, int Y){
		
		//if (x>=mapwidth || x<0 || y>=mapheight || y<0) return "Auserhalb Spielfeld";
		char field=map[Y][X];
		switch (field){
			
			case 'x': 
			case 'X': 
			case 'e':
			case 'E': 
			case 'a':
			case 'A': 
			case 'g':
			case 'r':	

			case 'G': return new Sprite (this.spritewidth, this.spriteheight, field);
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
		char OL = map[Y][X];
		//Oben-Rechts
		X=(int)	((x+playersizex-1)/this.spritewidth);
		Y=(int)(y/this.spriteheight);
		char OR = map[Y][X];
		//Unten-Links
		X= (int) (x/this.spritewidth);
		Y= (int) ((y+playersizey-1)/this.spriteheight);
		char UL = map[Y][X];
		//Unten-Rechts
		X= (int) ((x+playersizex-1)/this.spritewidth);
		Y= (int) ((y+playersizey-1)/this.spriteheight);
		char UR = map[Y][X];
		
		//Wichtig ist die Reihenfolge, was wichtiger ist oben
		if		(OL=='g' || OR=='g' || UL=='g' || UR=='g') return 'g';
		else if	(OL=='x' || OR=='x' || UL=='x' || UR=='x') return 'x';
		else if(OL=='a' || OR=='a' || UL=='a' || UR=='a') return 'a';
		else if	(OL=='e' || OR=='e' || UL=='e' || UR=='e') return 'e';
		else return' ';
	}
	
	
	
	
	
	public void updateState(Moveable character) {
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
		
		
	}
	/**
	 * Changes the State of the Character, 
	 * @param character
	 */
	void influence(Moveable character) {
		// TODO Auto-generated method stub
		
	}

	void updateState() {
		// TODO Auto-generated method stub
		
	}
	
	
	/*
	 * JPanel overwrites for add/remove
	 */
	
	public Component add(Player player){
		Component component=super.add(player);
		//player.set
		this.player=player;
		player.setMap();
		moveables.add(player);
		return component;
	}
	public Component add(Moveable mov){
		Component c=super.add(mov);
		mov.setMap();
		moveables.add(mov);
		return c;
	}
	public Component addComponent(Sprite sprite){
		Component component = super.add(sprite);
		//TODO
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
	public void remove(Moveable mov){
		super.remove(mov);
		super.revalidate();
		moveables.remove(mov);
	}
}


