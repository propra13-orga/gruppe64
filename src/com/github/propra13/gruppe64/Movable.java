package com.github.propra13.gruppe64;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;


public abstract class Movable extends Sprite {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8648193148636982438L;
	protected double armor;
	protected int health=100;
	protected int mana=100;
	protected Map map;
	public enum dir {up ,right,down,left};
	public enum axis {x,y};
	protected enum modes {idle, attack, evade, block, moving};
	public modes movMode=modes.moving;
	protected ArrayList<Item> itemarr;
	protected ArrayList<Item> slotarr;
	protected String nick;
	
	// Feuer(elementtype=1) oder Eis(elementtype=2 f��r bewegende Gegner und Player
	protected int elementtype=Item.NORMAL; 
	/**
	 * aktuelle Geschwindigkeit
	 */
	protected int[] vel=new int[2];

	


	public Movable(int posx, int posy,int Dimx, int Dimy) {
		super(posx, posy, Dimx, Dimy);
		// TODO Auto-generated constructor stub
	}


	public Movable(int Dimx, int Dimy, char name) {
		super(Dimx, Dimy, name);
		// TODO Auto-generated constructor stub
	}
	
	public int[] getVel(){
		return vel;
	}
	
	public void setVel(int vx, int vy){
		this.vel[0]=vx;
		this.vel[1]=vy;
	}
	
	public void attemptAttack(){
		
			//System.out.print("schlag ");

			CopyOnWriteArrayList<Movable> movarr=new CopyOnWriteArrayList<Movable>(map.getMovables());
			for(Movable mov:movarr){
				if(!mov.getClass().equals(this.getClass()) && !this.equals(mov) && this.slotarr.get(0).getRange()>sqDistance(this,mov))
				{	//System.out.println("treffer");
					//System.out.println(map.getMovables().size());
					mov.damage(this.slotarr.get(0).getDmg(),this.elementtype);
					//System.out.println(map.getMovables().size());
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
	/**
	 * Gets called at add(Moveable) from map
	 */
	public void setMap(){
		Map preCast = (Map) getParent();
		if(preCast==null){
			map=preCast;return;
		}
			
		Class<? extends Map> cClass =   preCast.getClass();
		this.map = cClass.cast(this.getParent());
	}
	
	/**
	 * Ist die beabsichtigte Bewegung moeglich
	 */
	abstract public void updateMot();/*{
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
		//map.updateState(this);
	}*/
	@Override
	public void setLocation(int x, int y){
		super.setLocation(x,y);
		
	}
	public void unsetMot(axis i){
		switch (i){
			case x: this.setVel(0, this.getVel()[1]); break;
			case y: this.setVel(this.getVel()[0],0); break;
		}
	}
	
	
/*	public void damage(int dmg){
		this.health -= dmg;
		if(this.health<=0)		map.remove(this);
	}
*/	
	//neue damage- Methode,die auch elementtype der Waffe bzw. des Gegners ��bergibgt
	
	public void damage(int dmg, int waffenelement){
		
		if (waffenelement==0){} //normaler Schaden, da Waffe neutral
		else if (elementtype==Item.NORMAL){}//normaler Schaden, da Charakter neutral ist
		else if(waffenelement== elementtype) {dmg = dmg/2;} //halber Schaden, da gleiche Elemente von Charakter und Waffe
		else {dmg = dmg*2;} // doppelter Schaden, da verschiedene Elemente von Charakter und Waffe
		
		this.health -=dmg;
		if(this.health<=0)	{ this.die();}
		
	}
	protected abstract void die();
	
	public ArrayList<Item> getSlots(){
		return slotarr;
	}
	public ArrayList<Item> getItems(){
		return itemarr;
	}
	public static double sqDistance(Movable a, Movable b){
		return Math.pow(a.getX()+a.Dim[0]/2-b.getX()-b.Dim[0]/2,2)+Math.pow(a.getY()+a.Dim[1]/2-b.getY()-b.Dim[1]/2,2);
	}

	abstract public String getNick();
}
