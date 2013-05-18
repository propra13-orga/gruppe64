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
	char map[][]= {	{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
					{'x', 'x', ' ', ' ', 'g', ' ', ' ', ' ', ' ', 'x'},
					{'e', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'a'},
					{'x', 'x', ' ', ' ', ' ', ' ', 'x', 'x', 'x', 'x'},
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
					{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}};
	/**
	 * Erzeuge neues JPanel und ordne es an, hier kann auch das auslesen aus Datei gestartet werden
	 */
	public Map(){
		super();
		this.setBounds(0, 0, 1000, 1000);
		/*System.out.print("ThreadGesammt" +Thread.activeCount());
		this.setBackground(Color.PINK);
		this.setVisible(true);*/
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
			char field=map[X][Y];
			switch (field){
				
				case 'x': 
				case 'X': 
				case 'e':
				case 'E': 
				case 'a':
				case 'A': 
				case 'g':
				case 'G': return new Sprite (field);
				default: return null;
			}
		}
	public Sprite getSprite(int X, int Y, int spritewidth, int spriteheight){
		
		//if (x>=mapwidth || x<0 || y>=mapheight || y<0) return "Auserhalb Spielfeld";
		char field=map[X][Y];
		switch (field){
			
			case 'x': 
			case 'X': 
			case 'e':
			case 'E': 
			case 'a':
			case 'A': 
			case 'g':
			case 'G': return new Sprite (spritewidth, spriteheight, field);
			default: return null;
		}
	}
	
	
	public void drawMap(int spritewidth, int spriteheight){
		
		int x,y;
		for(int i=0; i<mapwidth;i++){
			x=i*spritewidth;
			for(int j=0; j< mapheight; j++){
				y=j*spriteheight;
				Sprite sp1 = this.getSprite(i,j, spritewidth, spriteheight);
				
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
	public boolean isCrossable(int i, int j) {
		// TODO Kollisionsabfrage
		//unterscheidung ob Spieler sichbar ist?
		return true;
	}
	public Component add(Player player){
		Component component=super.add(player);
		//TODO
		return component;
	}
	public Component addComponent(Sprite sprite){
		Component component = super.add(sprite);
		//TODO
		return component;
	}
	public void remove(Player player){
		super.remove(player);
	}
	public void remove(Sprite sprite){
		super.remove(sprite);
	}
}


