package com.github.propra13.gruppe64.visible;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import com.github.propra13.gruppe64.visible.*;

/**
 * Basis Sprite-Klasse z.B. Mauer
 * @author manad, chkell, sewue, vaabr
 */
public class Sprite  implements MouseListener, Serializable, SpriteContent{

	/**
	 * einzigartige ID zum Serialisieren
	 */
	private static final long serialVersionUID = -3969230972538288646L;
	/**
	 * Position auf map
	 */
	protected int x,y;
	/**
	 * Ausmasse (x,y)
	 */
	protected int[] Dim=new int[2];
	/**
	 * Sprite name, kodiert die Basis Fkt und Darstellung der Sprites
	 */
	public char name;
	/**
	 * Aufnehmbar oder Nicht
	 */
	protected boolean lootable=false;
	public boolean crossable=true;
	/**
	 * Die dargestellte Komponente
	 */
	protected transient SpriteComponent sprite;
	/**
	 * Eltern Map, der Komponente 
	 */
	public Map map;
	/**
	 * selbst
	 */
	protected static Sprite self;
	/**
	 * Setzt Bounds des JComponent, und den Dim[] 
	 * @param x x-Position auf Map
	 * @param y y-Position auf Map
	 * @param xDim Breite des Sprites
	 * @param yDim Hoehe des Sprites
	 */
	public Sprite(int x, int y, int xDim, int yDim){
		this();
		this.setDim(xDim, yDim);
		sprite.setBounds( x, y, xDim, yDim);
		this.x=x;this.y=y;
	}
	
	/**
	 * leerer Konstruktor default Contructor, ethhaelt setzt Zeichnung
	 */
	public Sprite(){
		sprite = new SpriteComponent(this);
		
		sprite.addMouseListener(this);
	    self=this;
	}
	public Sprite(int xDim, int yDim, char name){
		this();
		this.name=name;
		this.setDim(xDim,yDim);
		sprite.setBounds(0,0,xDim,yDim);
		if(name=='x')crossable=false;
	}
	
		
	/**
	 * Gibt die Groeße des Sprites zurueck
	 * @return Vektor size= {x_Size, y_Size}
	 */
	public int[] getDim(){
		return Dim;
	}
	
	/**
	 * Setzt die Groeße des Sprites
	 * @param xDim
	 * @param yDim
	 */
	public void setDim(int xDim,int yDim){
		this.Dim[0] = xDim;
		this.Dim[1] = yDim;
	}
			
	public Rectangle getRectangle(){
		return new Rectangle(x,y,Dim[0],Dim[1]);
	}
	public char getSpriteName(){
		return name;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount()>1){
			sprite.setBorder(BorderFactory.createLineBorder(Color.blue));
			System.out.println(this.toString());
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
	
	/**
	 * Positioniere Sprite an Position (x,y)
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		if(sprite!=null) sprite.setLocation(x, y);
		this.x=x;this.y=y;
	}
	
	/**
	 * @return x-Position
	 */
	public int getX(){
		return x;
	}
	/**
	 * @return y-Position
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Positioniere Sprite an Position lastPos
	 * @param lastPos
	 */
	public void setLocation(Point lastPos) {
		sprite.setLocation(lastPos);
		
	}
		
	public JComponent getSprite() {
		return sprite;
	}

	public void setMap(Map map) {
		this.map = map;assert (map==null);
	}

	public Map getMap() {
		return map;
	}

	public void paint(Graphics g) {
		//Zeichnet jenach Typ
		switch (this.name){
			case 'x':
				//	g.setColor(Color.black); 
				//	g.fillRect(0, 0, Dim[0],Dim[1]);
					
					Image img4 = Toolkit.getDefaultToolkit().getImage("res/bricket.jpg");
				    g.drawImage(img4, 0, 0, sprite);
				    g.finalize();	
				    
			break;	
		}
		
	}
	@Override
	public String toString(){
		return "name:"+this.name+" x:" +this.getX() + " y:" + this.getY()+" Dim:"+ Dim[0]+","+Dim[1];
	}
	//@Override
	public Object readResolve(){
       // in.defaultReadObject();
        sprite = new SpriteComponent(this);
        if(Dim!=null)sprite.setBounds(x,y,Dim[0],Dim[1]);
        return this;
	}
}
