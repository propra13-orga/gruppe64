package com.github.propra13.gruppe64.visible;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;



public abstract class Movable extends Sprite {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8648193148636982438L;
	protected double armor;
	protected int health=100;
	protected int mana=100;
	public enum dir {up ,right,down,left};
	public enum axis {x,y};
	protected enum modes {idle, attack, evade, block, moving};
	public modes movMode=modes.moving;
	protected ArrayList<Item> itemarr;
	protected ArrayList<Item> slotarr;
	protected String nick;
	// Feuer(elementtype=1) oder Eis(elementtype=2 f������r bewegende Gegner und Player
	protected int elementtype=Item.NORMAL; 
	/**
	 * aktuelle Geschwindigkeit
	 */
	protected int[] vel=new int[2];

	


	public Movable(){
		
	}
	

	/**
	 * Setze Groeße des Objekts
	 * @param xDim
	 * @param yDim
	 */
	public Movable(int xDim, int yDim) {
		super(0,0,xDim,yDim);
	}


	/**
	 * Setze Groeße und Position des Objekts
	 * @param x
	 * @param y
	 * @param xDim
	 * @param yDim
	 */
	public Movable(int x, int y, int xDim, int yDim) {
		super(x,y,xDim,yDim);
	}



	public int[] getVel(){
		return vel;
	}
	
	public void setVel(int vx, int vy){
		this.vel[0]=vx;
		this.vel[1]=vy;
	}
	
	public void setVel(int vel[]){
		this.vel=vel;
	}
	
	/**
	 * Versucht Attacke auszufuehren. Alle Player in Reichweite erleiden Schaden
	 */
	public void attemptAttack(){
		
			//System.out.print("schlag ");

			CopyOnWriteArrayList<Movable> movarr=new CopyOnWriteArrayList<Movable>(map.getMovables());
			for(Movable mov:movarr){
				if(!mov.getClass().equals(this.getClass()) && !this.equals(mov) && this.slotarr.get(0).getRange()>sqDistance(this.sprite,mov.sprite,this.Dim,mov.Dim))
				{	//System.out.println("treffer");
					//System.out.println(map.getMovables().size());
					mov.damage(this.slotarr.get(0).getDmg(),this.elementtype);
					//System.out.println(map.getMovables().size());
				}
			}
	
	}
	
	/**
	 * Setze Geschwindigkeit in Richtung i
	 * @param i
	 */
	public void setMot(dir i) {
		switch (i){
			case up: vel[1]= 1; break;
			case right: vel[0]= 1; break;
			case down: vel[1]=-1; break;
			case left: vel[0]=-1; break;
		}
		
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
	//neue damage- Methode,die auch elementtype der Waffe bzw. des Gegners ������bergibgt
	
	/**
	 * Schadenberechnung fuer Moveable. Abhaengig von erlittenen Grundschaden und Elementtyp
	 * @param dmg
	 * @param waffenelement
	 */
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
	public static double sqDistance(JComponent a, JComponent b, int[] aD, int[] bD){
		return Math.pow(a.getX()+aD[0]/2-b.getX()-bD[0]/2,2)+Math.pow(a.getY()+aD[1]/2-b.getY()-bD[1]/2,2);
	}

	abstract public String getNick();
}
