package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Sprite extends JComponent {

	private static final long serialVersionUID = 1419758802002837841L;
	
	//Aktuelle Position (px) linker obere Ecke
	int x,y;
	//aktuelle Geschwindigkeit
	int vx, vy;
	//Ausmasse
	int xDim, yDim ;
	//Sprite name
	char name;
	
	//Wird meist fuer Gelaende verwendet
	public Sprite(char name) {
		//nur gerade  Werte
		xDim=50;
		yDim=50;
		this.name=name;		
		this.setBounds(0,0,xDim,yDim);
	}
	// Konstruktor mit Position
	public Sprite(char name, int x, int y){
		this(name);
		this.x=x;
		this.y=y;
		this.setLocation( 100, 100);
	}
	//leerer Konstruktor fuer z.B. Spieler, der ja immer am Eingang startet
	public Sprite(){
		
	}
	public void paintComponent(Graphics g){
		//Zeichnet jenach Typ
		switch (this.name){
			case 'x':case 'X':
					g.setColor(Color.black); break;
			case 'e': g.setColor(Color.gray); break;
			case 'a': g.setColor(Color.green); break;
			case 'g': g.setColor(Color.red);  break;	
			
		}
		g.fillRect(0, 0, xDim, yDim);
	}
	
}
