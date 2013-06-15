package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Sprite extends JComponent {

	

	/**
	 * aktuelle Geschwindigkeit
	 */
	protected int[] vel=new int[2];

	/**
	 * Ausmasse
	 */
	protected int[] Dim=new int[2];
	private Rectangle rectangle; 
	/**
	 * Sprite name
	 */
	char name;
	
	/*//Wird meist fuer Gelaende verwendet
	public Sprite(char name) {
		//nur gerade  Werte
		xDim=50;
		yDim=50;
		this.name=name;		
		this.setBounds(0,0,xDim,yDim);
	}
	// Konstruktor mit Position
	public Sprite(char name, int x, int y){
		this.name=name;
		this.x=x;
		this.y=y;
		this.setLocation( 100, 100);
	}*/
	// Konstruktor Namenloser 
	public Sprite(int x, int y, int xDim, int yDim){
		this.rectangle = new Rectangle(x,y,xDim,yDim);
		this.setBounds( x, y, xDim, yDim);
	}
	/*//leerer Konstruktor fuer z.B. Spieler, der ja immer am Eingang startet
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
	}*/
		
	
	public int[] getDim(){
		return Dim;
	}
	
	public void setDim(int xDim,int yDim){
		this.Dim[0] = xDim;
		this.Dim[1] = yDim;
	}
	
	public int[] getVel(){
		return vel;
	}
	
	public void setVel(int vx, int vy){
		this.vel[0]=vx;
		this.vel[1]=vy;
	}
	
	
	public Sprite(int xDim, int yDim, char name){
		this.name=name;
		this.setDim(xDim,yDim);
		this.rectangle = new Rectangle(0,0,xDim,yDim);
		this.setBounds(0,0,xDim,yDim);
	}
			
	public Rectangle getRectangle(){
		return new Rectangle(this.getX(),this.getY(),Dim[0],Dim[1]);
	}
	public void paintComponent(Graphics g){
		//Zeichnet jenach Typ
		switch (this.name){
			case 'x':
			case 'X':	
					g.setColor(Color.black); 
					g.fillRect(0, 0, Dim[0],Dim[1]);
			break;
			
			case 'e':
				g.setColor(Color.gray); 
				g.fillRect(0, 0,Dim[0],Dim[1]);
			break;
			
			case 'a': 
				g.setColor(Color.green); 
				g.fillRect(0, 0,Dim[0],Dim[1]);
			break;
			
			case 'g':
	
				Image img1 = Toolkit.getDefaultToolkit().getImage("res/nyan_cat2.gif");
			    g.drawImage(img1, 0, 0, this);
			    g.finalize();	
				//		g.setColor(Color.red); 
		//		g.fillRect(0, 0, xDim, yDim);
			break;	
			
	//		case 'r':
				
		/*		g.setColor(Color.cyan);
				g.drawOval(0, 0, 10, 50);//Oval Zeichnen
				g.fillOval(10,10,25,25);
		*/
	//		break;
			
			
		}
		
	}
	
	
		
	
	
}
