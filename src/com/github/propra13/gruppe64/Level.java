package com.github.propra13.gruppe64;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;

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
	
	private Map aMap;
	private int levelNr;
	
	// mapArray's for all Rooms
	private Iterator<Room> roomIterator;
	//current active Room
	private Room aRoom;
	
	
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
	
	public void readAllRooms(int lvl){
		
		int i=1;
		Room raum;
		char[][] tmpArray;
		MapGenerator mg= new MapGenerator("res/Karten/Level%i_Raum%i.txt");
		tmpArray=mg.readRoom(lvl, i);
		while(tmpArray!=null){
			raum=new Room(this,tmpArray);
			roomList.add(raum);
			tmpArray=mg.readRoom(lvl, ++i);
		}
		System.out.println("Level "+lvl+ " hat "+roomList.size());
	}
	
	public void nextRoom(){
		//create first Level
		if (roomIterator.hasNext()){
			aRoom = roomIterator.next();
			setMap(aRoom);
		} else {
			//exit last Room, next Level accessible
			cp.remove(aMap);
			player.setLevel(levelNr+1);
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
		return aMap;
	}
	/**
	 * Setzte aktuellen Raum
	 * @param map
	 */
	public void setMap(Room map){
		if(aMap!=null){
			aMap.remove(player);
			cp.remove(aMap);
		}
		cp.add(map);
		map.repaint();
		map.add(player);
		aMap=map;
	}

	public void purge(){
		cp.remove(aMap);
		//game.showWorld();
	}
	
}
