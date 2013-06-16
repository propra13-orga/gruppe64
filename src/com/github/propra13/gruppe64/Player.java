package com.github.propra13.gruppe64;								// # 0001

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;


@SuppressWarnings({ "serial" })
public class Player extends Moveable {
	//der bei der Bewegung bachtenswerter Offset zum (x,y)
	//int x_off, y_off;

	private StatBar statBar;
	
	private Map map;
	//Test Konstruktor
	public Player(int x, int y){
		//Groesse des Spielers 
		super(x,y,30,30);
		itemarr = new ArrayList<Item>();
		slotarr = new ArrayList<Item>();
		itemarr.add(new Item('s'));
		slotarr.add(itemarr.get(0));
		this.map = map;
		
	}
	
	public boolean putOnMap(int x, int y, Map map){
		//TODO ist die Stelle ueberhaupt sinnvoll -> map.isCrossable
		return true;
	}
	

	public void paintComponent(Graphics g){
		//Zeichnet jenach Typ
	//	g.setColor(Color.ORANGE);
	//	g.fillRect(0, 0, xDim, yDim);
		
		Image img2 = Toolkit.getDefaultToolkit().getImage("res/banana.gif");
	    g.drawImage(img2, 0, 0, this);
	    g.finalize();	
		//System.out.print("playerDraw");
	}

	public void addStatBar(StatBar statBar) {
		this.statBar = statBar;
		
	}
	
}
