package com.github.propra13.gruppe64;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Moveable extends Sprite {
	

	protected double armor;
	protected int health=100;
	private Map map;
	public enum dir {up ,right,down,left};
	public enum axis {x,y};
	private enum modes {idle, attack, evade, block, moving};
	private int mode=0;
	private Timer timer;
	protected ArrayList<Item> itemarr;
	protected ArrayList<Item> slotarr;
	


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
			System.out.print("schlag ");
			int x=this.getX();
			int y=this.getY();
			
			this.mode=1;
			TimerTask action = new TimerTask() {
				public void run() {
					mode=0;
				}
			};

			timer = new Timer();
			timer.schedule(action, 1000, 5000);
			timer.purge();
			CopyOnWriteArrayList<Moveable> movarr=new CopyOnWriteArrayList<Moveable>(map.getMovables());
			for(Moveable mov:movarr){
				if(!this.equals(mov) && this.slotarr.get(0).getRange()>Math.pow(x+Dim[0]/2-mov.getX()+mov.Dim[0]/2,2)+Math.pow(y+Dim[1]/2-mov.getY()+mov.Dim[1]/2,2))
				{	System.out.println("treffer");
					System.out.println(map.getMovables().size());
					mov.damage(this.slotarr.get(0).getDmg());
					System.out.println(map.getMovables().size());
				}
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
		if(map.wouldTouch(x+vel[0],y,Dim[0],Dim[1])!='x'){
			
			this.setLocation(x+vel[0],y);
		}
		x=this.getX();
		y=this.getY();
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
	public void damage(int dmg){
		this.health -= dmg;
		if(this.health<=0)		map.remove(this);
	}
	public ArrayList<Item> getSlots(){
		return slotarr;
	}
}
