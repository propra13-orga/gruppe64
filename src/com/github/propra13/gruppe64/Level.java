package com.github.propra13.gruppe64;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


public class Level implements java.io.Serializable{
	private static final long serialVersionUID = -4648599488509978586L;
	
	int spriteWidth=50;
	int spriteHeight=50;
	ArrayList<Room> roomList;
	ArrayList<Moveable> moveable;
	
	private Game game;
	private Container cp;
	private Player player; //networkf if NPlayer.class


	
	private int levelNr;
	
	// mapArray's for all Rooms
	private Iterator<Room> roomIterator;
	//current active Room
	private Map aMap;


	
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

	}
	public void initLevel(){
		readAllRooms(levelNr);
		roomIterator = roomList.iterator();
		for(Room iRoom: roomList){
			//iRoom.removeAll();
			iRoom.drawMap();
			iRoom.startMotion();
		}
		this.nextRoom();
	
	}
	public void readAllRooms(int lvl){
		

		char[][] tmpArray;
		MapGenerator mg= new MapGenerator("res/Karten/Level%i_Raum%i.txt");
		roomList=mg.generateRoomList(this);

		System.out.println("Level "+lvl+ " hat "+roomList.size());
	}
	/**
	 * next Room
	 */
	public void nextRoom(){
		Room tmpRoom;
		if(roomIterator.hasNext()){
			tmpRoom = roomIterator.next();
			//tmpRoom.add(player);
			setMap(tmpRoom);
		} else {
			System.out.print("nextLevel");
			removeOldMap();
			player.setLevel(null);
			game.nextLevel();
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
		cp.add(aMap);
		//put player on top
		aMap.setComponentZOrder(player, 0);
		//aMap.startMotion();
		aMap.repaint();
	}

	public void removeOldMap(){
		if(aMap!=null){
			if(aMap.playerList.size()<2){
				//aMap.stopMotion();
			}
			aMap.remove(player);//
			cp.remove(aMap);
			cp.revalidate();
			cp.repaint();
		}
	}
	public boolean isLastRoom() {
		if(roomList.indexOf(aMap)<roomList.size()-1)
			return false;
		return true;
	}
	
	public void gameOver() {
		game.gameOver();
		
	}
	//TODO denke an network
	public void reset(Player pl) {
		
		this.initLevel();
	}
	public int getLevelNr() {
		return levelNr;
	}
	public void enterDoor(Door door) {
		// TODO if open Door
		//target Door
		if(door.getSpecial()!=null){
			if(player.getClass().equals(NPlayer.class)){
				
			}
			
		}else{ //other Room from this
			Door tDoor=door.getTarget();
			Map tMap = (Map)tDoor.getParent();
			setMap(tMap);
			player.setLocation(tDoor.getX(),tDoor.getY());
		}
		
	}
	
	
}
