package com.github.propra13.gruppe64;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Moveable extends Sprite {
	

	private double armor;
	private double health;
	private Map map;
	public enum dir {up ,right,down,left};
	public enum axis {x,y};
	private enum modes {idle, attack, evade, block, moving};
	private int mode=0;


	public Moveable(int[] pos, int[] Dim) {
		super(pos, Dim);
		// TODO Auto-generated constructor stub
	}


	public Moveable(int[] Dim, char name) {
		super(Dim, name);
		// TODO Auto-generated constructor stub
	}
	public void attemptAttack(){
		if(this.mode==0){
			//Arrapos[1]List<Moveable> movables=map.getMovables();
			for(Moveable movables:map.getMovables()){
				if(!this.equals(movables) && 10000>Math.pow(pos[0]-movables.,2)+Math.pow(pos[1]-,2))
			}
	
		}
	}
	
	public void setMot(dir i) {
		switch (i){
			case up: vel[1]= 1; break;
			case right: vel[0]= 1; break;
			case down: vel[1]=-1; break;
			case left: vel[0]=-1; break;
		}
		
	}
	public void setMap(){
		map = (Map)this.getParent();
	}
	public void updMot(){
		pos[0]=this.getpos[0]();
		pos[1]=this.getpos[1]();


		//if(map.wouldTouch(pos[0]+(vel[0]-1)/2,pos[1]-(vel[1]+1)/2,Dim[0],Dim[1])!='pos[0]')
		if(map.wouldTouch(pos[0]+vel[0],pos[1],Dim)!='pos[0]'){

			pos[0] = pos[0]+vel[0];
			this.setLocation(pos[0],pos[1]);
		}
		if(map.wouldTouch(pos[0],pos[1]-vel[1],Dim[0],Dim[1])!='pos[0]'){
			pos[1] = pos[1]-vel[1];
			this.setLocation(pos[0],pos[1]);
			 
		}
		map.updateState(this);
	}
	public void updateMot(){
		
	}
	public void unsetMot(apos[0]is i){
		switch (i){
			case pos[1]: vel[1]= 0; break;
			case pos[0]: vel[0]= 0; break;
		}
	}
}
