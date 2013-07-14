package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Enemy extends Movable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2216301988921162914L;
	private char name;
	
	
	public Enemy(int x, int y, int xDim, int yDim,char name) {
		super(x,y, xDim,yDim);
		this.name=name;
		vel[0]=1;vel[1]=1;
		itemarr = new ArrayList<Item>();
		slotarr = new ArrayList<Item>();
		itemarr.add(new Item('p'));
		slotarr.add(itemarr.get(0));
	}
	
	public Enemy(int x, int y, int xDim, int yDim, char name, int elementtype) {
		super(x,y, xDim,yDim);
		this.name=name;
		this.elementtype=elementtype;
		vel[0]=1;vel[1]=1;
		itemarr = new ArrayList<Item>();
		slotarr = new ArrayList<Item>();
		itemarr.add(new Item('p'));
		slotarr.add(itemarr.get(0));
	}

	public Enemy(int xDim, int yDim, char name) {
		super(xDim,yDim, name);
		itemarr = new ArrayList<Item>();
		slotarr = new ArrayList<Item>();
		itemarr.add(new Item('p'));
		slotarr.add(itemarr.get(0));
		
	}
	public void paintComponent(Graphics g){
		
	switch( name){    
		case 'g':
			
			Image img1 = Toolkit.getDefaultToolkit().getImage("res/nyan_cat2.gif");
			g.drawImage(img1, 0, 0, this);
		    g.finalize();	
	
		break;
		
		//Eis und Feuer Gegner
		
		case '(':
			
			Image img11 = Toolkit.getDefaultToolkit().getImage("res/flamme.gif");
			g.drawImage(img11, 0, 0, this);
		    g.finalize();	
		break;
		
		case ')':
			
			Image img12 = Toolkit.getDefaultToolkit().getImage("res/iceking.png");
			g.drawImage(img12, 0, 0, this);
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
	public void updateMot(){
		if(movMode!=modes.moving)return;
		
		
		int x=this.getX();
		int y=this.getY();
		if(map.wouldTouch(x+vel[0],y,Dim[0],Dim[1])!='x'){

			this.setLocation(x+vel[0],y);
			
		}else{
			vel[0]= -1* vel[0];
		}
		x=this.getX();
		y=this.getY();
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
	protected void die(){
		map.remove(this);
	}
}
