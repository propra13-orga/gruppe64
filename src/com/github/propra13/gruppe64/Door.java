package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Graphics;

public class Door extends Sprite {
	public static int NOKEY=0;
	public static int REDKEY=1;
	public static int BLUEKEY=2;
	
	private boolean open = false;
	private Map leadsTo;
	private int key=NOKEY;
	
	private static int xDim = 50;
	private static int yDim =50;
	
	public Door(Map leadsTo, int x, int y) {
		super(x, y, xDim, yDim);
		this.leadsTo=leadsTo;
	}
	public Door() {
		// TODO Auto-generated constructor stub
	}

	
	public void paintComponent(Graphics g){
		
				g.setColor(Color.green); 
				g.fillRect(0, 0,Dim[0],Dim[1]);
	}

}
