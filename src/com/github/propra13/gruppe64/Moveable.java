package com.github.propra13.gruppe64;

import java.util.ArrayList;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Moveable extends Sprite {
	

	private double armor;
	private double health;
	private Map map;
	public enum dir {up ,right,down,left};
	public enum axis {x,y};
	private enum modes {idle, attack, evade, block, moving};
	private int mode=0;


	public Moveable(int posx, int posy,int Dimx, int Dimy) {
		super(posx, posy, Dimx, Dimy);
		// TODO Auto-generated constructor stub
	}


	public Moveable(int Dimx, int Dimy, char name) {
		super(Dimx, Dimy, name);
		// TODO Auto-generated constructor stub
	}
	public void attemptAttack(){
		if(this.mode==0){
			int x=this.getX();
			int y=this.getY();
			for(Moveable movables:map.getMovables()){
				if(!this.equals(movables) && 10000>Math.pow(x+Dim[0]/2-movables.getX()+movables.Dim[0]/2,2)+Math.pow(y+Dim[1]/2-movables.getY()+movables.Dim[1]/2,2))
					JOptionPane.showMessageDialog(null, "baem!", "baem!", JOptionPane.OK_CANCEL_OPTION);
				
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
	/**
	 * Ist die beabsichtigte Bewegung moeglich
	 */
	public void updMot(){
		int x=this.getX();
		int y=this.getY();

		//if(map.wouldTouch(pos[0]+(vel[0]-1)/2,pos[1]-(vel[1]+1)/2,Dim[0],Dim[1])!='pos[0]')
		if(map.wouldTouch(x+vel[0],y,Dim[0],Dim[1])!='x'){
			
			this.setLocation(x+vel[0],y);
		}
		if(map.wouldTouch(x,y-vel[1],Dim[0],Dim[1])!='x'){
			this.setLocation(x,y-vel[1]);
			 
		}
		map.updateState(this);
	}
	
	public void updateMot(){
		
	}
	public void unsetMot(axis i){
		switch (i){
			case x: this.setVel(0, this.getVel()[1]); break;
			case y: this.setVel(this.getVel()[0],0); break;
		}
	}
}
