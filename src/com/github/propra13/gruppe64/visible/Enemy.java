package com.github.propra13.gruppe64.visible;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JComponent;



public class Enemy extends Movable implements SpriteNames,SpriteContent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2216301988921162914L;
	private char name;
	/**
	 * Konstruktor fuer Gegner mit Positions und Groeßenangabe und Typ des Gegners
	 */
	
	
	public Enemy(int x, int y, int xDim, int yDim,char name) {
		this();
		this.name=name;
		
		vel[0]=1;vel[1]=1;
		this.setDim(xDim, yDim);
		sprite.setBounds( x, y, xDim, yDim);
		this.x=x;this.y=y;
	}
	/**
	 *  Konstruktor zusaetzlich mit Elementtyp Feuer oder Eis
	 */
	public Enemy(int x, int y, int xDim, int yDim, char name, int elementtype) {
		this();
		this.elementtype=elementtype;
		this.name=name;
		
		vel[0]=1;vel[1]=1;
		this.setDim(xDim, yDim);
		sprite.setBounds( x, y, xDim, yDim);
		this.x=x;this.y=y;
	}
/**
 *  Allgemeiner Gegner mit Groeße von  50 pixeln
 */
	public Enemy(int xDim, int yDim, char name) {
		this();
		this.name=name;
		
		vel[0]=1;vel[1]=1;
		this.setDim(xDim, yDim);
		sprite.setBounds( 0, 0, xDim, yDim);
	
	}
	public Enemy(){
		itemarr = new ArrayList<Item>();
		slotarr = new ArrayList<Item>();
		itemarr.add(new Item(PAW));
		slotarr.add(itemarr.get(0));
		
		sprite=new SpriteComponent(this);super.sprite=sprite;
		}

			public void paint (Graphics g){
				
				switch( Enemy.this.name){    
					case 'g':
						
						Image img1 = Toolkit.getDefaultToolkit().getImage("res/nyan_cat2.gif");
						g.drawImage(img1, 0, 0, sprite);
					    g.finalize();	
				
					break;
					
					//Eis und Feuer Gegner
					
					case '(':
						
						Image img11 = Toolkit.getDefaultToolkit().getImage("res/flamme.gif");
						g.drawImage(img11, 0, 0, sprite);
					    g.finalize();	
					break;
					
					case ')':
						
						Image img12 = Toolkit.getDefaultToolkit().getImage("res/iceking.png");
						g.drawImage(img12, 0, 0, sprite);
					    g.finalize();	
					    break;
					}
					if(health<100){
						g.drawRect(0, 0, Dim[0]-1, 10);
						g.setColor(Color.RED);
						g.fillRect(0, 0, health*Dim[0]/100, 10);
					}
				}
		
	
	
	@Override
	/**
	 * aktualisiert Bewegung des Gegners
	 */
	public void updateMot(){
		if(movMode!=modes.moving)return;
		
		
		int x=sprite.getX();
		int y=sprite.getY();
		if(map.wouldTouch(x+vel[0],y,Dim[0],Dim[1])!='x'){

			this.setLocation(x+vel[0],y);
			
		}else{
			vel[0]= -1* vel[0];
		}
		x=sprite.getX();
		y=sprite.getY();
		if(map.wouldTouch(x,y-vel[1],Dim[0],Dim[1])!='x'){

			this.setLocation(x,y-vel[1]);
		} else{
			vel[1]= -1* vel[1];
		}
		//map.updateState(this);
	}

	@Override
	public String getNick() {
		// TODO Auto-generated method stub
		return "Cat";
	}
	/**
	 * entfernt Gegner von der Map
	 */
	protected void die(){
		map.remove(this);
	}
}
