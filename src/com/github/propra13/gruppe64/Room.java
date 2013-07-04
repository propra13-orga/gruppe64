package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Room extends Map {
	
	private static final long serialVersionUID = 1L;
	
	
	public int raumnr;
	public int levelnr;
	
	public int[] pos_eingang;
	protected ArrayList<Door> doorList;


	Timer caretaker;
	private Level level;


	private boolean active=true;


	private static int wallSize= 50;
	public Room(Level aLevel, char[][] mapArray) {
		super(mapArray);

		this.level = aLevel;
		//this.pos_eingang 	= getPosOf('e');
		
	}
	

	
	public void setWallSize(int wallW, int wallH) {
		spriteheight = wallH;
		spritewidth = wallW;
		
	}
	public void moveMoveable(){
		
		for(Moveable mov : moveables){
			mov.updateMot();
		}
		
	}
	/**
	 *  extends the  add method from Map, to do Room specific tasks for e.g. Movables or Doors
	 */
	@Override
	public Component add(Sprite sp){
		//Do map and Container things
		Component component=super.add(sp);

		Class<? extends Sprite> cClass = sp.getClass();
		
	

		return component;
	}
	
	/*@Override
	public void updateState(Moveable character) {
		char touchedSprite = wouldTouch(character.getRectangle());	
		//System.out.println(player.getVisibleRect().toString());
		if(character.getClass().equals(Player.class)){
		switch(touchedSprite){
		
		case 'a':case 'A':
			// leve.nextRoom();
		break;
		default:
		break;
		}
		}
		
	}*/
	

	public static String charString(char[][] m){
		String tmp="";
		for (int i=0;  i< 7; i++){
			for(int j=0; j< 10;j++){
				tmp+=m[i][j];
			}
			tmp+="\n";
		}
		return tmp;
	}
	public String toString(){
		String tmp="";
		for (int i=0;  i< mapheight; i++){
			for(int j=0; j< mapwidth;j++){
				tmp+=mapArray[i][j];
			}
			tmp+="\n";
		}
		return tmp;
	}
}


