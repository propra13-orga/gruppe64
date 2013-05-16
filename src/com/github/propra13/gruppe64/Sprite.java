package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Sprite extends JComponent {

	private static final long serialVersionUID = 1419758802002837841L;
	//Geschwindigkeit bei Bewegung
	double vx, vy;
	//Aktuelle Position
	double x,y;
	int xDim, yDim ;
	char name;
	public Sprite(char name) {
		this.name=name;
		
		//switch
		//unterschiedliche double dimx, dimy;
		//je nach
	}
	
	public void paintComponent(Graphics g){
		
		switch (this.name){
			case 'x': g.setColor(Color.black); g.fillRect(0, 0, xDim, yDim); break;
			case 'e': g.setColor(Color.gray);  g.fillRect(0, 0, xDim, yDim); break;
			case 'a': g.setColor(Color.green); g.fillRect(0, 0, xDim, yDim); break;
			case 'g': g.setColor(Color.red);   g.fillRect(0, 0, xDim, yDim); break;	
		}
	}
	
}
