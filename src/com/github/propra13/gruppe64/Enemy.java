package com.github.propra13.gruppe64;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Enemy extends Moveable {

	public Enemy(int x, int y, int xDim, int yDim) {
		super(x,y, xDim,yDim);
		vel[0]=1;vel[1]=1;
	}

	public Enemy(int xDim, int yDim, char name) {
		super(xDim,yDim, name);
		
	}
	public void paintComponent(Graphics g){
		Image img1 = Toolkit.getDefaultToolkit().getImage("res/nyan_cat2.gif");
	    g.drawImage(img1, 0, 0, this);
	    g.finalize();	
	}
	@Override
	public void updateMot(){
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
		map.updateState(this);
	}
}
