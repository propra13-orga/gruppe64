package com.github.propra13.gruppe64;								// # 0001

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;


@SuppressWarnings({ "serial" })
public class Player extends Moveable {
	//der bei der Bewegung bachtenswerter Offset zum (x,y)
	//int x_off, y_off;

	private StatBar statBar;
	
	private Map map;
	private int w;						//waffen Nr im Waffenslot
	private Timer timer;
	private int mode=0;
	//Test Konstruktor
	public Player(int x, int y){
		//Groesse des Spielers 
		super(x,y,30,30);
		timer = new Timer();
		itemarr = new ArrayList<Item>();
		slotarr = new ArrayList<Item>();
		itemarr.add(new Item('s'));
		slotarr.add(new Item('s'));
		
	}
	
	public boolean putOnMap(int x, int y, Map map){
		//TODO ist die Stelle ueberhaupt sinnvoll -> map.isCrossable
		return true;
	}
	

	public void paintComponent(Graphics g){
		//Zeichnet jenach Typ
	//	g.setColor(Color.ORANGE);
	//	g.fillRect(0, 0, xDim, yDim);
		
		Image img2 = Toolkit.getDefaultToolkit().getImage("res/banana.gif");
	    g.drawImage(img2, 0, 0, this);
	    g.finalize();	
		//System.out.print("playerDraw");
	}
	public void updateMot(){
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
		x=this.getX();
		y=this.getY();
		//Items aufnehmen
		CopyOnWriteArrayList<Item> items=new CopyOnWriteArrayList<Item>(map.getItems());
		for(Item it:items){
			if(it.isLootable() && Math.pow(it.getHeight()+it.getWidth(), 2)/16>Math.pow(x+Dim[0]/2-it.getX()-it.getWidth()/2,2)+Math.pow(y+Dim[1]/2-it.getY()-it.getHeight()/2,2))	
				pickup(it);
		}
		map.updateState(this);
	}
	public void addStatBar(StatBar statBar) {
		this.statBar = statBar;
		
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
			timer.schedule(action, 1000);

			CopyOnWriteArrayList<Moveable> movarr=new CopyOnWriteArrayList<Moveable>(map.getMovables());
			for(Moveable mov:movarr){
				if(!this.equals(mov) && this.slotarr.get(0).getRange()>Math.pow(x+Dim[0]/2-mov.getX()-mov.Dim[0]/2,2)+Math.pow(y+Dim[1]/2-mov.getY()-mov.Dim[1]/2,2))
				{	System.out.println("treffer");
					System.out.println(map.getMovables().size());
					mov.damage(this.slotarr.get(0).getDmg());
					System.out.println(map.getMovables().size());
				}
			}
	
		}
	}
	
	public void damage(int dmg){
		this.health -= dmg;
		statBar.updateHealth(this.health);
		if(this.health<=0)		map.remove(this);
	}
	public void switchweapon(){
		
		do{
			if(++w>=itemarr.size())	w=0;
		}while(!itemarr.get(w).isWeapon());
		slotarr.set(0, itemarr.get(w));
		statBar.getStateFrom(this);
	}
	
	public void pickup(Item item){
		itemarr.add(item);
		map.remove(item);
		statBar.getStateFrom(this);
	}
	public void setMap(){
		map = (Map)this.getParent();
	}
	
}
