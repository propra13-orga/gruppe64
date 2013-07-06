package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Sprite extends JComponent implements MouseListener{

	


	/**
	 * Ausmasse
	 */
	
	protected int[] Dim=new int[2];
	
	private Rectangle rectangle; 
	/**
	 * Sprite name
	 */
	char name;
	protected boolean lootable=false;
	
	
	// Konstruktor Namenloser 

	public Sprite(int x, int y, int xDim, int yDim){
		this();
		this.setDim(xDim, yDim);
		this.rectangle = new Rectangle(x,y,xDim,yDim);
		this.setBounds( x, y, xDim, yDim);
	}
	//leerer Konstruktor fuer z.B. Spieler, der ja immer am Eingang startet
	public Sprite(){
		this.addMouseListener(this);
	         
	}
	public Sprite(int xDim, int yDim, char name){
		this();
		this.name=name;
		this.setDim(xDim,yDim);
		this.rectangle = new Rectangle(0,0,xDim,yDim);
		this.setBounds(0,0,xDim,yDim);
	}
	

		
	
	public int[] getDim(){
		return Dim;
	}
	
	public void setDim(int xDim,int yDim){
		this.Dim[0] = xDim;
		this.Dim[1] = yDim;
	}
	
	
	
	
	
			
	public Rectangle getRectangle(){
		return new Rectangle(this.getX(),this.getY(),Dim[0],Dim[1]);
	}
	public char getSpriteName(){
		return name;
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
			/*
			
			*/
			case 'O':
				
				Image img9 = Toolkit.getDefaultToolkit().getImage("res/oldman.png");
			    g.drawImage(img9, 0, 0, this);
			    g.finalize();	
			break;
			
	//		case 'r':
				
		/*		g.setColor(Color.cyan);
				g.drawOval(0, 0, 10, 50);//Oval Zeichnen
				g.fillOval(10,10,25,25);
		*/
	//		break;
			
			
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount()>1){
			Sprite.this.setBorder(BorderFactory.createLineBorder(Color.blue)); 
		}else{
			Sprite.this.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
		
	
	
}
