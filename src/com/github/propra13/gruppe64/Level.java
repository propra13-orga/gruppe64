package com.github.propra13.gruppe64;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

public class Level extends JPanel{
	int spriteWidth=50;
	int spriteHeight=50;
	ArrayList<Room> roomList;
	ArrayList<Moveable> moveable;
	
	char map[][]={	{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
			{'x', 'x', ' ', ' ', ' ', ' ', 'x', ' ', 'x', 'x'},
			{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
			{'e', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'a'},
			{'x', 'x', ' ', ' ', ' ', ' ', 'x', ' ', 'x', 'x'},
			{'x', 'x', ' ', 'g', ' ', ' ', ' ', ' ', ' ', 'x'},
			{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}};

char map2[][]={	{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
			{'x', ' ', ' ', 'g', ' ', ' ', ' ', ' ', ' ', 'x'},
			{'x', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', 'x'},
			{'e', ' ', ' ', 'g', 'x', ' ', 'x', 'g', ' ', 'a'},
			{'x', 'x', ' ', 'x', 'x', ' ', 'x', 'x', 'x', 'x'},
			{'x', 'x', ' ', ' ', 'x', ' ', ' ', ' ', ' ', 'x'},
			{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}};

char map3[][]={	{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
			{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
			{'x', 'x', ' ', ' ', 'g', ' ', 'x', 'x', 'x', 'x'},
			{'e', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'a'},
			{'x', 'x', ' ', ' ', ' ', ' ', 'x', 'x', 'x', 'x'},
			{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
			{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}};


private Map aMap;
private Container cp;
private Player player;
// mapArray's for all Rooms

/**private ArrayList<char[][]> roomArrayL;*/
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
	public Level(Player player, Container cp, int levelnr) {
		this.cp = cp;
		this.player = player;
		
		roomList = new ArrayList<Room>();
		readAllRooms(levelnr);
		roomIterator = roomList.iterator();
	}
	
	public void readAllRooms(int lvl){
		
		int i=1;
		Room raum;
		while( (raum=new Room(lvl,i)) != null){
			roomList.add(raum);
		}
	}
	
	public void nextRoom(){
		//create first Level
		if (roomIterator.hasNext()){
			aRoom = roomIterator.next();
			setMap(aRoom);
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
	public void updateState(){
		
	}
	public void setMap(Map map){
		if(aMap!=null){
			aMap.remove(player);
			cp.remove(aMap);
		}
		cp.add(map, new myGBC(0, 0));
		map.add(player);
		aMap=map;
	}

	public void purge(){
		cp.remove(aMap);
		//game.showWorld();
	}
	
}
