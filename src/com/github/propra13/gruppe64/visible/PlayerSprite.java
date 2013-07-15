package com.github.propra13.gruppe64.visible;								// # 0001


import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.propra13.gruppe64.ActiveArea;
import com.github.propra13.gruppe64.Chat;
import com.github.propra13.gruppe64.Game;
import com.github.propra13.gruppe64.Level;
import com.github.propra13.gruppe64.Player;
import com.github.propra13.gruppe64.StatBar;



public class PlayerSprite extends Movable implements Player, SpriteContent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4414756618308751730L;
	//der bei der Bewegung bachtenswerter Offset zum (x,y)
	//int x_off, y_off;

	private StatBar statBar;
	private int goldmoney;
	public static final int[] prefPos={600, 400};


	private int w,a;						//waffen Nr im Waffenslot

	private transient Timer timer_pl;
	private int mode=0;
	TimerTask action;

	// leben ������������������brig
	private int life;

	public transient Game game;
	int level;
	/**
	 * 	freigeschaltenes Level des jeweiligen Spielers
	 * 
	 * @author sepp
	 */
	private int lvlUnl;
	private int gold=0;
	public  Level aLevel;
	//private boolean hasArmor=false;
	//private boolean hasArmorFire=false;
	
	public transient ChatterBox chatterBox;
	

	//Test Konstruktor
	public PlayerSprite(int x, int y){
		this();

		timer_pl = new Timer();
		nick="player";
		this.life=3;

		itemarr = new ArrayList<Item>();
		slotarr = new ArrayList<Item>();
		itemarr.add(new Item('s'));

		slotarr.add(new Item('s'));

		lvlUnl=1;

		sprite.setBounds(x,y,30,30);
		 
	}
	public PlayerSprite(){
		//Groesse des Spielers 
		super(0,0,30,30);
		sprite = new SpriteComponent(this);
	}

	public void paint(Graphics g){
		Image img2 = Toolkit.getDefaultToolkit().getImage("res/banana.gif");
	    g.drawImage(img2, 0, 0, sprite);
	    g.finalize();	
	    g.setFont(new Font ("Arial", Font.PLAIN , 11));
		g.drawString(nick, 10, 10);
	}
	public void updateMot(){
		if(movMode!=modes.moving)return;
		int x=this.getX();
		int y=this.getY();
		boolean movPossible=true;
		//if(vel[0]>0)movPossible=map.isCrossable(new Point(x+Dim[0]+vel[0],y), new Point(x+Dim[0]+vel[0],y+Dim[1]));
		//if(vel[0]<0)movPossible=map.isCrossable(new Point(x+vel[0],y), new Point(x+vel[0],y+Dim[1]));
		if(true){
			
			this.setLocation(x+vel[0],y);
			map.getJPanel().setLocation(map.getJPanel().getX()-vel[0], map.getJPanel().getY());
		}
		x=this.getX();
		y=this.getY();
		if(true){
			
			this.setLocation(x,y-vel[1]);
			map.getJPanel().setLocation(map.getJPanel().getX(), map.getJPanel().getY()+vel[1]);
		}
	//	map.updateState(this);
		x=this.getX();
		y=this.getY();
		//Items aufnehmen
		CopyOnWriteArrayList<Item> items=new CopyOnWriteArrayList<Item>(map.getItems());
		for(Item it:items){
			if(it.isLootable() && Math.pow(it.sprite.getHeight()+it.sprite.getWidth(), 2)/16>Math.pow(x+Dim[0]/2-it.sprite.getX()-it.sprite.getWidth()/2,2)+Math.pow(y+Dim[1]/2-it.sprite.getY()-it.sprite.getHeight()/2,2))	
				pickup(it);
		}
		ActiveArea aActiveA=map.isOnActiveArea(this);
		if(aActiveA!= null){
			aActiveA.onTouch(this);
		}
		
	}
	public void attemptAttack(){
		
		if(this.mode==0){
			System.out.print("schlag ");
			int x=this.getX();
			int y=this.getY();
			
			this.mode=1;
			action= new TimerTask() {
				public void run() {
					mode=0;
				}
			};
			timer_pl.schedule(action, 1000);

			CopyOnWriteArrayList<Movable> movarr=new CopyOnWriteArrayList<Movable>(map.getMovables());
			for(Movable mov:movarr){
				if(!this.equals(mov) && this.slotarr.get(0).getRange()>sqDistance(this.sprite,mov.sprite,this.Dim,mov.Dim))
				{	System.out.println("Spieler trifft");
					System.out.println(map.getMovables().size());
					mov.damage(this.slotarr.get(0).getDmg(),this.slotarr.get(0).elementtype);
					System.out.println(map.getMovables().size());
				}
			}
	
		}
	}

	
	public void damage(int dmg){
		this.health -= dmg;
		
		statBar.updateHealth(this.health);
		if(this.health<=0)
		{
			die();
		}
	}
	
	//neue damage- Methode,die auch elementtype der Waffe bzw. des Gegners beruecksichtigt
	
	public void damage(int dmg, int elementwaffe){
		
		super.damage(dmg, elementwaffe);
		
		statBar.updateHealth(this.health);

	}
	
	/**
	 * man kann nur in einem Level sterben, also 
	 */
	@Override
	protected void die(){
		life--;
		if(life>0){
			aLevel.reset(this);
			health=100;
			statBar.updateHealth(health);
			aLevel.setOnDoor(aLevel.entrance);
		} else {
			
			aLevel.gameLost();
		}

	}
	public void switchweapon(){
		
		do{
			if(++w>=itemarr.size())	w=0;
		}while(!itemarr.get(w).isWeapon());
		slotarr.set(0, new Item(itemarr.get(w)));
		statBar.getStateFrom();
	}
	

	public void switcharmor(){
		boolean b=false;
		for(Item item:itemarr)		if(item.isArmor()) b=true;
		if(b){
			do{
				if(++a>=itemarr.size())	a=0;
			}while(!itemarr.get(a).isArmor());
			if(slotarr.size()<2)	slotarr.add(new Item(itemarr.get(a)));	
			else					slotarr.set(1, new Item(itemarr.get(a)));
			if (itemarr.get(a).displayedName.equals("FireArmor"))		this.elementtype=Item.FIRE;
			else if (itemarr.get(a).displayedName.equals("IceArmor"))	this.elementtype=Item.ICE;
			statBar.getStateFrom();
		}
	}
	
	public void pickup(Item item){
		
		//TODO gold soll geadded werden, nicht angezeigt
		boolean notRedundant = true;
		if(item.isWeapon()||item.isArmor()){
			for(Item itemIt: itemarr){
				if(itemIt.getSpriteName()==item.getSpriteName()){
					notRedundant=false;
				}	
			}
		}
	
		
		
		if(notRedundant){

			if(item.getSpriteName()=='Y')	gold+= 50;
			if(map instanceof Shop && gold>=item.getPrice()){
				
				
				gold-= item.getPrice();
				itemarr.add(item);
				item.setOwner(this);
				map.remove(item);
				statBar.getStateFrom();
			}else if(!(map instanceof Shop)){
				
				itemarr.add(item);
				item.setOwner(this);
				map.remove(item);
				statBar.getStateFrom();
			}
		}else{
			if(!(map instanceof Shop)){
				map.remove(item);
				statBar.getStateFrom();
			}
		}
		
	}
	public void use(char c){
		Item item = null;
		for(Item it:itemarr){
			if(it.name==c)item=it;
		}
		if(item!=null)	use(item);		
	}
	public void use(Item item){
	
		
		if(item.plushealth!=0) {
			this.healthCast();
			itemarr.remove(item);
		}
		if(item.plusmana !=0){
			this.mana=100;	
			statBar.updateMana(this.mana);
			itemarr.remove(item);
		}	
		statBar.getStateFrom();
	}
	
		
	public void abortTimer(){
		timer_pl.cancel();
		timer_pl.purge();
	}

	@Override
	public Game getGame() {
		return game;
	}
	/**
	 * Setze vom Spieler erreichbares Level
	 * @param i
	 */
	public void setLevel(int i) {
		if(i>level){
			level= i;
		}
	}
	/**
	 * setzte aktives Level
	 * @param aLevel
	 */
	public void setLevel(Level aLevel){
		this.aLevel= aLevel;
		if(chatterBox!=null)
		chatterBox.clearInputText();
	}
	public Level getLevel(){
		return aLevel;
	}

	public void healthCast() {
		// TODO Auto-generated method stub
		int mtemp=this.mana;
		mtemp=mtemp-50;
		if(mtemp>=0){
			mana = mana-50;
			statBar.updateMana(this.mana);
			if(health<50){
				health=health+50;
			}
			else health=100;
			statBar.updateHealth(this.health);
		}


		
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getLvlUnlocked() {
		return lvlUnl;
	}

	public void setLvlUnlocked(int lvlUnl) {
		if(this.lvlUnl < lvlUnl)this.lvlUnl = lvlUnl;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void addStatBar(StatBar statBar) {
		this.statBar = statBar;
		
	}
	public void setChatterBox(Chat chatPane, JTextField chatInput){
		chatterBox=new ChatterBox(chatPane,chatInput);
	}
	public ChatterBox getChatterBox(){
		return chatterBox;
	}
	
	public void performAction() {
		if(movMode!=Movable.modes.moving)return;
		ActiveArea activeSprite = map.isOnActiveArea(this);
		if(activeSprite!=null){
			activeSprite.onAction(this);
		}
				
	}


	@Override
	public void chgready() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouchAction() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onActionAction() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onTouch(Movable mv) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAction(Movable mv) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void tell(ActiveArea player, String chatInputText) {
		getChatterBox().writeChat(player, chatInputText);
		
	}
	
	


}
