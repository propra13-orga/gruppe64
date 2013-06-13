package com.github.propra13.gruppe64;

public class Moveable extends Sprite {
	

	private double armor;
	private double health;
	private Map map;
	public enum dir {up ,right,down,left};
	public enum axis {x,y};


	public Moveable(int x, int y, int xDim, int yDim) {
		super(x, y, xDim, yDim);
		// TODO Auto-generated constructor stub
	}


	public Moveable(int xDim, int yDim, char name) {
		super(xDim, yDim, name);
		// TODO Auto-generated constructor stub
	}
	
	public void setMot(dir i) {
		switch (i){
			case up: vy= 1; break;
			case right: vx= 1; break;
			case down: vy=-1; break;
			case left: vx=-1; break;
		}
		
	}
	public void setMap(){
		map = (Map)this.getParent();
	}
	public void updMot(){
		x=this.getX();
		y=this.getY();


		//if(map.wouldTouch(x+(vx-1)/2,y-(vy+1)/2,xDim,yDim)!='x')
		if(map.wouldTouch(x+vx,y,xDim,yDim)!='x'){

			x = x+vx;
			this.setLocation(x,y);
		}
		if(map.wouldTouch(x,y-vy,xDim,yDim)!='x'){
			y = y-vy;
			this.setLocation(x,y);
			 
		}
		map.updateState(this);
	}
	public void updateMot(){
		
	}
	public void unsetMot(axis i){
		switch (i){
			case y: vy= 0; break;
			case x: vx= 0; break;
		}
	}
}
