package com.github.propra13.gruppe64.visible;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Sprite  implements MouseListener, Serializable{

	
	

	/**
	 * Ausmasse (x,y)
	 */
	protected int[] Dim=new int[2];
	/**
	 * Sprite name
	 */
	public char name;
	protected boolean lootable=false;
	public boolean crossable=true;
	protected transient JComponent sprite;
	// Konstruktor Namenloser 
	public Map map;

	public Sprite(int x, int y, int xDim, int yDim){
		this();
		this.setDim(xDim, yDim);

		sprite.setBounds( x, y, xDim, yDim);
	}
	//leerer Konstruktor fuer z.B. Spieler, der ja immer am Eingang startet
	public Sprite(){
		sprite = new JComponent(){
			public void paintComponent(Graphics g){
				//Zeichnet jenach Typ
				switch (Sprite.this.name){
					case 'x':
					case 'X':	
						//	g.setColor(Color.black); 
						//	g.fillRect(0, 0, Dim[0],Dim[1]);
							
							Image img4 = Toolkit.getDefaultToolkit().getImage("res/bricket.jpg");
						    g.drawImage(img4, 0, 0, this);
						    g.finalize();	
						    
					break;	
					
				}
				
			}
		};
		sprite.addMouseListener(this);
	         
	}
	public Sprite(int xDim, int yDim, char name){
		this();
		this.name=name;
		this.setDim(xDim,yDim);
		sprite.setBounds(0,0,xDim,yDim);
		if(name=='x')crossable=false;
	}
	
		
	
	public int[] getDim(){
		return Dim;
	}
	
	public void setDim(int xDim,int yDim){
		this.Dim[0] = xDim;
		this.Dim[1] = yDim;
	}
	
	
	
	
	
			
	public Rectangle getRectangle(){
		return new Rectangle(sprite.getX(),sprite.getY(),Dim[0],Dim[1]);
	}
	public char getSpriteName(){
		return name;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount()>1){
			sprite.setBorder(BorderFactory.createLineBorder(Color.blue)); 
		}else{
			sprite.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
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
	public void setLocation(int x, int y) {
		sprite.setLocation(x, y);
		
	}
	public int getX(){
		return sprite.getX();
	}
	public int getY(){
		return sprite.getY();
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public void setLocation(Point lastPos) {
		sprite.setLocation(lastPos);
		
	}
		
	public JComponent getSprite() {
		return sprite;
	}
	
}
