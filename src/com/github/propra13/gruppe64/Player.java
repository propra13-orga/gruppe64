package com.github.propra13.gruppe64;								// # 0001

public class Player extends Sprite {

	public Player(char name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void setMot(int i) {
		switch (i){
			case 0: vy= 1; break;
			case 1: vx= 1; break;
			case 2: vy=-1; break;
			case 3: vx=-1; break;
		}
		
	}
	
	public void updMot(){
		int x_off=0;
		int y_off=0;
		if(vy==-1) 	y_off=yDim;
		if(vx==1) 	x_off=xDim;
		if(was_ist_bei(x+x_off+vx,y+y_off-vy)!='x'){
			x = x+vx;
			y = y-vy;
		}
	}

	public void unsetMot(int i){
		switch (i){
			case 0: vy= 0; break;
			case 1: vx= 0; break;
		}
	}
	
}
