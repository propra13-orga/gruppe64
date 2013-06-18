package com.github.propra13.gruppe64;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class Level extends JPanel{
	private static final long serialVersionUID = -4648599488509978586L;
	
	int spriteWidth=50;
	int spriteHeight=50;
	ArrayList<Room> roomList;
	ArrayList<Moveable> moveable;
	
	private Game game;
	private Container cp;
	private Player player;
	
	private int lRoomNr;
	private int aRoomNr=0;
	
	
	private int levelNr;
	
	// mapArray's for all Rooms
	private Iterator<Room> roomIterator;
	//current active Room
	private Room aRoom;

	private Timer caretaker;
	private TimerTask action;
	
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
		
		
		roomList = new ArrayList<Room>();
		readAllRooms(levelnr);
		roomIterator = roomList.iterator();
	}
	public void initLevel(){
		
		
		this.nextRoom();
		
		
		
	}
	public void readAllRooms(int lvl){
		
		lRoomNr=1;
		char[][] tmpArray;
		MapGenerator mg= new MapGenerator("res/Karten/Level%i_Raum%i.txt");
		tmpArray=mg.readRoom(lvl, lRoomNr);
		while(tmpArray!=null){
			System.out.println(Room.charString(tmpArray));
			Room raum=new Room(this,tmpArray);
			roomList.add(raum);
			tmpArray=mg.readRoom(lvl, ++lRoomNr);
		}
		
		System.out.println("Level "+lvl+ " hat "+roomList.size());
	}
	/**
	 * next Room
	 */
	public void nextRoom(){
		Room tmpRoom;
		if(roomIterator.hasNext()){
			tmpRoom = roomIterator.next();
			setMap(tmpRoom);
		} else {
			System.out.print("nextLevel");
			if(aRoom!=null){
				aRoom.stopMotion();
				cp.remove(aRoom);
				
				cp.repaint();
			}
			game.nextLevel();
		}	
	}
	/**
	 * called by Game, iterates about all Movables and move them according to the actual Room
	 */
	public void letThemMove(){
		//for ech movable Object (enemy, player, etc.)
		for(Moveable mov : moveable){
			mov.updateMot();
		}
	}
	/**
	 * gets called by Game, update the Levelchange if some thing happened
	 */
	public Map getMap(){
		return (Map) aRoom;
	}
	/**
	 * Setzte aktuellen Raum
	 * @param map
	 */
	public void setMap(Room map){
		if(aRoom!=null){
			aRoom.stopMotion();
			cp.remove(aRoom);
			
			cp.repaint();
		}
		//next aRoom
		
		aRoom = map;
		
		aRoom.add(player);
		player.setMap();
		//TODO set player position
		player.setLocation(0, 150);
		aRoom.drawMap();
		System.out.print("Set\n"+aRoom.toString());
		cp.add(aRoom);
		
		
		aRoom.startMotion();
		aRoom.repaint();
	}

	public void purge(){
		cp.remove(aRoom);
		//game.showWorld();
	}
	public boolean isLastRoom() {
		if(roomList.indexOf(aRoom)<roomList.size()-1)
			return false;
		return true;
	}
	
}
