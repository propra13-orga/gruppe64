package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	
	protected int mapwidth=10;
	protected int mapheight=7;

	protected int spritewidth=50;
	protected int spriteheight=50;
	protected char[][] mapArray;
	protected ArrayList<Moveable> moveables;
	
	
	
	
	
	/*  Beispiel: x_max = mapwidth = 4 und y_max = mapheight = 3:
	 * 
	 *  [0][0]   [0][1]   [0][2]  [0][3]
	 *  [1][0]   [1][1]   [1][2]  [1][3]
	 *  [2][0]   [2][1]   [2][2]  [2][3]
	 */
	//char map[][]=new char [mapwidth][mapheight];
	

	
	char map[][]  = readRoom(1,1);
	char map2[][] = readRoom(1,2);
	char map3[][] = readRoom(1,3);
	
	
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
		this.setBounds(0, 0, 600, 350);
		this.setBackground(Color.lightGray);
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
	
	
	
	
	public Sprite getSprite(int X, int Y){
		
		//if (x>=mapwidth || x<0 || y>=mapheight || y<0) return "Auserhalb Spielfeld";
		char field=map[Y][X];
		switch (field){
			case 'e':
			case 'E':  
				
			case 'x': 
			case 'X': 
			case 'a':
			case 'A': 
			
			case 'r': return new Sprite (this.spritewidth, this.spriteheight, field);	
			case 'g':	
			case 'G': return new Enemy(0, 0, 50, 50);
			//ITEMS
			case 'S': 
			case 'Y':
			case 'M':
			case 'H': 
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
		if	(OL=='x' || OR=='x' || UL=='x' || UR=='x') return 'x';
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
	public Component add(Sprite sp){
		Component component=super.add(sp);
		//player.set
		Class<? extends Sprite> cClass = sp.getClass();
		if(cClass.equals(Enemy.class)){
			((Enemy)sp).setMap();
			moveables.add((Moveable) sp);
		}
		if(cClass.equals(Player.class)){
			//player.set
			this.player=(Player)sp;
			((Player)sp).setMap();
			moveables.add((Moveable) sp);
		}
		//moveables.add(player);
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
		repaint();
		moveables.remove(mov);
	}
	
	
	/* 
	 * Ruft res/Karten/Level[lvl]_Raum[room].txt auf und bestimmt seine Groe��e
	 * 
	 * AUSGABE:
	 * - int[] {Zeilenanzahl des Files, max. Zeilenlaenge im File}
	 * 	ODER
	 * - null wenn res/Karten/Level[lvl]_Raum[room].txt nicht existiert
	 */
	public int[] getMapSize(int lvl, int room){
		
		int linelength = 0;
		int linenumber = 0;
		String current_line="";
		
		FileReader f;
		try {
			f = new FileReader("res/Karten/Level" +lvl+ "_Raum" +room+ ".txt");

			BufferedReader buffer = new BufferedReader(f);
		
			while( (current_line= buffer.readLine())!= null){
				
				linenumber++;
				
				int currentlength = current_line.length();
				if (linelength < currentlength) linelength = currentlength;
			}
			buffer.close();
		}
		catch (IOException e) {
			return null;
		}
		
		int[] size= {0,0};
		size[0] = linenumber; 
		size[1] = linelength; 
		
		return size;
	}
	
	/*
	 * Ruft res/Karten/Level[lvl]_Raum[room].txt auf und kopiert Inhalt in char[][]
	 * 
	 * AUSGABE:
	 * - char[mapheight][mapheight]
	 * 	ODER
	 * - null, wenn res/Karten/Level[lvl]_Raum[room].txt nicht existiert
	 */
	public char[][] readFile(int mapwidth, int mapheight, int lvl, int room){
		
		char[][] map = new char[mapheight][mapwidth];
		
		FileReader f;
		String currentline;
		try {
			f = new FileReader("res/Karten/Level"+lvl+"_Raum"+room+".txt");
			BufferedReader buffer = new BufferedReader(f);
			
			for( int j=0; j<mapheight; j++){
				currentline = buffer.readLine();
			
				for( int i=0; i< currentline.length(); i++){
					map[j][i]= currentline.charAt(i);
				}
			}
			buffer.close();
		}
		catch (IOException e) {
			return null;
		}
		catch (NullPointerException e){
			/* 
			 * Durch diese catch-Abfrage wird der Fall abgefanden, dass im txt-File
			 * weniger Zeilen sind als angegeben. Somit terminiert die NullPointerException
			 * nicht den kompletten Prozess, sondern bricht nur das beschreiben des Arrays map
			 * ab und dieses ist ja dann schon fertig...
			 */
		}
		return map;
	}
	
	/*
	 * Kopiert res/Karten/Level[lvl]_Raum[room].txt in char[][]
	 * 
	 * AUSGABE:
	 * - char[][] = Map[y][x], wobei x,y die Koordinaten des
	 * 		JPanel-Koordinatensystems sind
	 * 	ODER
	 * - null, wenn res/Karten/Level[lvl]_Raum[room].txt nicht existiert
	 */
	public char[][] readRoom(int lvl, int room){
		
		int mapwidth, mapheight;
		int[] mapsize = getMapSize(lvl, room);
		
		if(mapsize==null){return null;}
		mapheight= mapsize[0];
		mapwidth = mapsize[1];
		
		char[][] map = readFile( mapwidth, mapheight, lvl, room);
		return map;
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
	
}


