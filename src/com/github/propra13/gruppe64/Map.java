package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;


@SuppressWarnings({ "serial", "unused" })
public class Map extends JPanel{

	/*
	 * TODO
	 * - getMapwidth() usw. da private 
	 * - einlesen aus einer Datei
	 * - ?Siehe WIKI?
	 */
	
	//Gr�se der Map

	/**
	 * @uml.property  name="mapwidth"
	 */
	private int spritewidth;
	private int spriteheight;
	private int mapwidth=10;
	/**
	 * @uml.property  name="mapheight"
	 */
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
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
					{'x', 'x', ' ', ' ', 'x', ' ', ' ', ' ', ' ', 'x'},
					{'e', ' ', ' ', 'g', ' ', ' ', ' ', ' ', ' ', 'a'},
					{'x', 'x', ' ', ' ', ' ', ' ', 'x', 'x', 'x', 'x'},
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
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
	/**
	 * Erzeuge neues JPanel und ordne es an, hier kann auch das auslesen aus Datei gestartet werden
	 */
	public Map(int spritewidth, int spriteheight){
		super();
		this.spritewidth= spritewidth;
		this.spriteheight= spriteheight;
		this.setBounds(0, 0, 600, 600);
		//System.out.print("ThreadGesammt" +Thread.activeCount());
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	public Map(int spritewidth, int spriteheight, int level, Game game){
		super();
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
	
	//gibt Name des Feldes bei (x,y) zurück
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

	/**
	 * Fragt ob man da drauf darf
	 */
	public boolean isCrossable(int x, int y) {
		// TODO Kollisionsabfrage
		//unterscheidung ob Spieler sichbar ist?
		int X= (int) (x/this.spritewidth);
		int Y= (int) (y/this.spriteheight);
		if (this.map[Y][X]==' '||this.map[Y][X]=='e') return true;
		else return false;
	}
	
	public boolean isEnemy(int x, int y){
		int X= (int) (x/this.spritewidth);
		int Y= (int) (y/this.spriteheight);
		if (this.map[Y][X]=='g') return true;
		else return false;
	}
	
	public boolean isExit(int x, int y){
		int X= (int) (x/this.spritewidth);
		int Y= (int) (y/this.spriteheight);
		if (this.map[Y][X]=='a') return true;
		else return false;
	}
	
	public Component add(Player player){
		Component component=super.add(player);
		this.player=player;
		return component;
	}
	public Component addComponent(Sprite sprite){
		Component component = super.add(sprite);
		//TODO
		return component;
	}
	public void remove(Player player){
		super.remove(player);
		//SPAETER suche aus PlayerArray
	}
	public void remove(Sprite sprite){
		super.remove(sprite);
	}

	public void updateState() {
		char touchedSprite = wouldTouch(player.x, player.y,player.xDim, player.yDim);	
		
		switch(touchedSprite){
		case 'g': case 'G':
			game.gameOver();
		break;
		case 'a':case 'A':
			game.nextLevel();
		}
		
		
	}

	public char wouldTouch(int i, int j, int xDim, int yDim) {
		
		return ' ';
	}
	
	/*public void paint(Graphics g){
		this.paintChildren(g);
	}*/
}


