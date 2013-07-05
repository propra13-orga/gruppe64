package com.github.propra13.gruppe64;								// # 0001

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings({ "serial" })
public class Player extends Moveable {
	//der bei der Bewegung bachtenswerter Offset zum (x,y)
	//int x_off, y_off;

	private StatBar statBar;
	private int goldmoney;



	private int w;						//waffen Nr im Waffenslot

	private Timer timer_pl;
	private int mode=0;
	TimerTask action;

	// leben übrig
	private int life;

	private Game game;
	private int level;
	/**
	 * 	freigeschaltenes Level des jeweiligen Spielers
	 * 
	 * @author sepp
	 */
	private int lvlUnl;
	private int gold=0;
	private Level aLevel;
	//private boolean hasArmor=false;
	//private boolean hasArmorFire=false;
	
	private Chat chatPane;
	private boolean saga=true;
	private String nick;
	private JTextField chatInput;
	
	

	//Test Konstruktor
	public Player(int x, int y){
		//Groesse des Spielers 
		super(x,y,30,30);
		timer_pl = new Timer();
		nick="player";
		this.life=3;

		itemarr = new ArrayList<Item>();
		slotarr = new ArrayList<Item>();
		itemarr.add(new Item('s'));

		slotarr.add(new Item('s'));

		lvlUnl=1;

		
		 
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
	//	map.updateState(this);
		x=this.getX();
		y=this.getY();
		//Items aufnehmen
		CopyOnWriteArrayList<Item> items=new CopyOnWriteArrayList<Item>(map.getItems());
		for(Item it:items){
			if(it.isLootable() && Math.pow(it.getHeight()+it.getWidth(), 2)/16>Math.pow(x+Dim[0]/2-it.getX()-it.getWidth()/2,2)+Math.pow(y+Dim[1]/2-it.getY()-it.getHeight()/2,2))	
				pickup(it);
		}
		//map.updateState(this);
		if(map.wouldTouch(x,y,Dim[0],Dim[1])=='O'&&saga){
			String text="Hallo mein Freund!Die Welt wird von Katzen terrorisiert. Benutze space um auf die Katzen einzuschlagen und benutze c um deine Waffen zu wechseln. Wenn du genügend Mana hast kannst du mit h dein Leben regenerieren.Mit einem Doppelklick kannst du Lebens- und Manatränke verwenden AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
			chatPane.append("Zenmeister Ikkyu Sojun", text);
			saga=false;			
		}
	}
	public void addStatBar(StatBar statBar) {
		this.statBar = statBar;
		
	}
	public void addChatPane(Chat chatp) {
		this.chatPane = chatp;
		
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

			CopyOnWriteArrayList<Moveable> movarr=new CopyOnWriteArrayList<Moveable>(map.getMovables());
			for(Moveable mov:movarr){
				if(!this.equals(mov) && this.slotarr.get(0).getRange()>Math.pow(x+Dim[0]/2-mov.getX()-mov.Dim[0]/2,2)+Math.pow(y+Dim[1]/2-mov.getY()-mov.Dim[1]/2,2))
				{	System.out.println("Spieler trifft");
					System.out.println(map.getMovables().size());
					mov.damage(this.slotarr.get(0).getDmg(),this.slotarr.get(0).elementtype);
					System.out.println(map.getMovables().size());
				}
			}
	
		}
	}
	
/*	public void damage(int dmg){
		if(hasArmor)
			this.health -= Math.ceil(dmg/2);
		else
			this.health -=dmg;
		statBar.updateHealth(this.health);
		if(this.health<=0){
			die();
		}
	}
	*/
	
	public void damage(int dmg){
		this.health -= dmg;
		
		statBar.updateHealth(this.health);
		if(this.health<=0)
		{
			die();
		}
	}
	
	//neue damage- Methode,die auch elementtype der Waffe bzw. des Gegners übergibgt
	
	public void damage(int dmg, int elementwaffe){
		if(elementwaffe==elementtype){ dmg=dmg/2;}
		else if(elementtype==0){dmg=dmg;}
		else dmg=dmg*2;
		
		this.health -=dmg;
		statBar.updateHealth(this.health);
		if(this.health<=0){
			die();
	}
	}
	
	/**
	 * man kann nur in einem Level sterben, also 
	 */
	private void die(){
		life--;
		if(life>0){
			aLevel.reset();
			health=100;
			statBar.updateHealth(health);
		} else {

			aLevel.gameOver();
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
		
		do{
			if(++w>=itemarr.size())	w=0;
		}while(!itemarr.get(w).isArmor());
		if(slotarr.size()<2)
			slotarr.add(new Item(itemarr.get(w)));	
		else
			slotarr.set(1, new Item(itemarr.get(w)));
		statBar.getStateFrom();
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
	
		
		Class<? extends JPanel> cClass = map.getClass();
		if(notRedundant){

			if(item.getSpriteName()=='Y')	setGold(getGold() + 50);
			if(cClass.equals(Shop.class) && gold>=item.getPrice()){
				
				
				setGold(getGold() - item.getPrice());
				itemarr.add(item);
				item.setOwner(this);
				map.remove(item);
				statBar.getStateFrom();
			}else if(!cClass.equals(Shop.class)){
				
				itemarr.add(item);
				item.setOwner(this);
				map.remove(item);
				statBar.getStateFrom();
			}
		}else{
			if(!cClass.equals(Shop.class)){
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

	public int getLvlUnl() {
		return lvlUnl;
	}

	public void setLvlUnl(int lvlUnl) {
		this.lvlUnl = lvlUnl;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void addChatInput(JTextField chatinput) {
		// TODO Auto-generated method stub
		chatInput = chatinput;
	}

	public JTextField getChatInput() {
		return chatInput;
	}
	
	public Chat getChatPane(){
		return chatPane;
	}

	public void enterRoom() {
		System.out.print("trYopenDoor");
		//if(map.getClass().equals(Room.class)){
			Room targetRoom = (Room) map.isOnOpenDoor(this);
			if(targetRoom!=null){
				System.out.print("openDoor");
				aLevel.nextRoom();
			}
		//}
			//map.leaveMap(this);
		
	}

}
