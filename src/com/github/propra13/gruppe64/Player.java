package com.github.propra13.gruppe64;								// # 0001

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Sprite {
	//der bei der Bewegung bachtenswerter Offset zum (x,y)
	//int x_off, y_off;
	/**
	 * @uml.property  name="map"
	 * @uml.associationEnd  readOnly="true"
	 */

	private Map map;
	//Test Konstruktor
	public Player(int x, int y, Map map){
		super(x,y,30,30);
		this.map = map;
		
	}
	
	public boolean putOnMap(int x, int y, Map map){
		//TODO ist die Stelle �berhaupt sinnvoll -> map.isCrossable
		return true;
	}
	public void setMot(int i) {
		switch (i){
			case 0: vy= 1; break;
			case 1: vx= 1; break;
			case 2: vy=-1; break;
			case 3: vx=-1; break;
		}
		
	}
	
	public void updMot(){
		int x_off=0;
		int y_off=0;
		x=this.getX();
		y=this.getY();
		/*
		if(vy==-1) 	y_off=yDim;
		if(vx==1) 	x_off=xDim;
		*/
		//System.out.println(map.wouldTouch(x+x_off+vx,y+y_off-vy,xDim,yDim));
		System.out.println("x="+x);System.out.println("y="+y);
		if(map.wouldTouch(x+x_off+vx,y+y_off-vy,xDim,yDim)!='x'){
			x = x+vx;
			y = y-vy;
			
			this.setLocation(x,y); 
		}
		map.updateState();
	}

	public void unsetMot(int i){
		switch (i){
			case 0: vy= 0; break;
			case 1: vx= 0; break;
		}
	}

	public void paintComponent(Graphics g){
		//Zeichnet jenach Typ
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, xDim, yDim);
		//System.out.print("playerDraw");
	}
	
}
