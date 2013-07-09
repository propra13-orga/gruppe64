package com.github.propra13.gruppe64;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import com.github.propra13.gruppe64.Door.cd;


public class Level implements java.io.Serializable{
	private static final long serialVersionUID = -4648599488509978586L;
	
	int spriteWidth=50;
	int spriteHeight=50;
	ArrayList<Room> roomList;
	ArrayList<Moveable> moveable;
	
	private Game game;
	private Container cp;
	private Player player; //networkf if NPlayer.class
	public Stack<Door> fallBackDoor;

	
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
	public void swiftTo(final Map a, final Map b, final Door.cd carDir){
		player.movMode=Moveable.modes.idle;
		a.remove(player);b.add(player);
		final int moveX,moveY;
		if(carDir==cd.EAST){
			moveX=-3;moveY=0;
			b.setLocation(aMap.getWidth(),0);
			cp.add(b);
		}else{
			moveX=0;moveY=0;return;
		}
		final Timer moveTimer=new Timer();
		TimerTask move = new TimerTask() {
			public void run() {
				aMap.setLocation(aMap.getX()+moveX, aMap.getY()+moveY);
				b.setLocation(b.getX()+moveX, b.getY()+moveY);
				if(aMap.getX()+aMap.getWidth()<0||aMap.getX()>cp.getWidth()){
					b.setLocation(0, 0);
					cp.remove(aMap);
					player.movMode=Moveable.modes.moving;
					aMap=b;
					moveTimer.cancel();
				}	
			}
		};
		
		moveTimer.schedule(move, 0, 10);
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
			if(door.carDir==Door.cd.EAST){
				swiftTo(aMap, tMap, door.carDir);
				player.setLocation(tDoor.getX(),tDoor.getY());
			}else{
				setMap(tMap);
				player.setLocation(tDoor.getX(),tDoor.getY());
			}
		}	
		
	}
	
	
}
