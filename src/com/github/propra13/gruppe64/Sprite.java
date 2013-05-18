package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Sprite extends JComponent {

	private static final long serialVersionUID = 1419758802002837841L;
	
	//Aktuelle Position (px) linker obere Ecke
	/**
	 * @uml.property  name="x"
	 */
	int x;

	/**
	 * @uml.property  name="y"
	 */
	int y;
	//aktuelle Geschwindigkeit
	/**
	 * @uml.property  name="vx"
	 */
	int vx;

	/**
	 * @uml.property  name="vy"
	 */
	int vy;
	//Ausmasse
	/**
	 * @uml.property  name="xDim"
	 */
	int xDim ;

	/**
	 * @uml.property  name="yDim"
	 */
	int yDim;
	//Sprite name
	/**
	 * @uml.property  name="name"
	 */
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
	//Konstruktor der auch Laenge und Breite festlegt
	public Sprite(char name, int x, int y, int xDim, int yDim){
		this.name=name;
		this.x=x;
		this.y=y;
		this.xDim=xDim;
		this.yDim=yDim;
		this.setBounds(x,y,xDim,yDim);
	}
	
	public Sprite(int xDim, int yDim, char name){
		this.name=name;
		this.xDim=xDim;
		this.yDim=yDim;
		this.setBounds(x,y,xDim,yDim);
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
