package com.github.propra13.gruppe64;

import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import com.github.propra13.gruppe64.visible.Door;
import com.github.propra13.gruppe64.visible.Map;
import com.github.propra13.gruppe64.visible.MapGenerator;
import com.github.propra13.gruppe64.visible.Movable;
import com.github.propra13.gruppe64.visible.Room;
import com.github.propra13.gruppe64.visible.World;
import com.github.propra13.gruppe64.visible.Door.cd;


public class Level implements java.io.Serializable{
	private static final long serialVersionUID = -4648599488509978586L;
	
	int spriteWidth=50;
	int spriteHeight=50;
	protected ArrayList<Room> roomList;
	ArrayList<Movable> moveable;
	
	private transient Game game;
	protected Container cp;
	protected Player player; //networkf if NPlayer.class
	Stack<Door> fallBackDoor;
	public Door entrance;
	
	protected int levelNr;
	
	// mapArray's for all Rooms
	private Iterator<Room> roomIterator;
	//current active Room
	protected Map aMap;

	private World world;

	protected MapGenerator mg;


	public Level(){
		
	}
	/**
	 * 
	 * @param player
	 * @param cp
	 * Content pane, where everything is drawn on
	 * @param levelPath
	 * the Path to the Level-Description File
	 */
	public Level(Game game, int levelnr) {
		this.game =game;
		this.cp = game.getCP();
		this.player = game.getPlayer();
		this.levelNr=levelnr;
		
		roomList = new ArrayList<Room>();
		storeAllRooms(levelNr);
	}
	public void initLevel(){
		roomList=getAllRooms();
		//boolean entranceFound=false;
		if(roomList.get(0)==null){
			setMap(world);
		}
		roomIterator = roomList.iterator();
		for(Room iMap: roomList){
			iMap.level=this;
			iMap.drawMap();
			iMap.startMotion();
		}
	}
	
	protected ArrayList<Room> getAllRooms() {
		try
	      {
	         FileInputStream fileIn = new FileInputStream("sav/roomList.tmp");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         roomList = (ArrayList<Room>) in.readObject();
	         in.close();
	         fileIn.close();
	         entrance=roomList.get(0).getEntrance();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("class not found");
	         c.printStackTrace();
	         return null;
	      }
		return roomList;
	}
	public void storeAllRooms(int lvl){
		

		char[][] tmpArray;
		mg= new MapGenerator("res/Karten/Level%i_Raum%i.txt");
		ArrayList<Room> roomList2store=mg.generateRoomList(this);

		System.out.println("Level "+lvl+ " hat "+roomList2store.size());
		 try
	      {	
			 File dirFile=new File("sav/");
			 if(!dirFile.exists()){
				 dirFile.mkdir();
			 }
	         FileOutputStream fileOut =new FileOutputStream("sav/roomList.tmp");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(roomList2store);
	         out.close();
	         fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}

	/**
	 * gets called by Game, update the Levelchange if some thing happened
	 */
	public Map getMap(){
		return (Map) aMap;
	}
	/**
	 * Setzte aktuellen Raum
	 * @param map
	 */
	public void setMap(Map map){
		removeOldMap();
		aMap = map;

		aMap.add(player);
		System.out.print("\nw"+map.getWidth()+"h"+map.getHeight()+"\n"+aMap.toString());
		cp.add(aMap.getJPanel());
		
		aMap.getJPanel().repaint();
	}

	public void removeOldMap(){
		if(aMap!=null){
			/*if(aMap.playerList.size()<2){
				//aMap.stopMotion();
			}*/
			aMap.remove(player);//
			cp.remove(aMap.getJPanel());
			cp.revalidate();
			cp.repaint();
		}
	}
	

	//TODO denke an network
	public void reset(Player pl) {
		aMap.remove(pl);
		//showMsg();
		roomList=getAllRooms();
		roomIterator=roomList.iterator();
		//TODO notification
		initLevel();
	}
	public int getLevelNr() {
		return levelNr;
	}
	
	public void popRoom(){
		Door tDoor = fallBackDoor.pop();
		tDoor.getRoom().enterDoor(tDoor,player);
	}
	
	public void gameLost() {
		// TODO Auto-generated method stub
		
	}
	public void setOnDoor(Door tDoor){
		if(tDoor==null){System.out.println("Level has no entrance");return;}
		Map tMap = tDoor.map;
		setMap(tMap);
		player.setLocation(tDoor.getX(),tDoor.getY());
		aMap.setLocation(aMap.getParent().getWidth()/2-player.getX(),aMap.getParent().getHeight()/2-player.getY());

	}
	public void showExtern(String string) {
		cp.removeAll();
		switch(string){
		case "world": game.showWorld();break;
		case "shop" : game.showShop();
		}
		
	}
	public void fallBackDoorpush(Door door) {
		fallBackDoor.push(door);
		
	}
	public Door fallBackDoorpop() {
		return fallBackDoor.pop();
		
	}
	
}
