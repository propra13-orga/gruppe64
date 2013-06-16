package com.github.propra13.gruppe64;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Enemy extends Moveable {

	public Enemy(int x, int y, int xDim, int yDim) {
		super(x,y, xDim,yDim);
		// TODO Auto-generated constructor stub
	}

	public Enemy(int xDim, int yDim, char name) {
		super(xDim,yDim, name);
		
	}
	public void paintComponent(Graphics g){
		Image img1 = Toolkit.getDefaultToolkit().getImage("res/nyan_cat2.gif");
	    g.drawImage(img1, 0, 0, this);
	    g.finalize();	
	}

}
