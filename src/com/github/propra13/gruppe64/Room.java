package com.github.propra13.gruppe64;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Room extends Map {
	
	private static final long serialVersionUID = 1L;
	
	
	public int raumnr;
	public int levelnr;
	
	public int[] pos_eingang;
	
	private Level level;
	
	private static int wallSize= 50;
	public Room(Level aLevel, char[][] mapArray) {
		super (wallSize, wallSize);
		this.mapArray = mapArray;
		this.level = aLevel;
		//this.pos_eingang 	= getPosOf('e');
		this.moveables = new ArrayList<Moveable>();
	}
	
	public Room(int lvl, int room){
		this.levelnr 		= lvl;
		this.raumnr 		= room;
		super.mapArray 		= readRoom(levelnr, raumnr);
		this.pos_eingang 	= getPosOf('e');
		super.moveables 	= new ArrayList<Moveable>();
		
	}
	@Override 
	void influence(Moveable character){
		
	}
	/**
	 * Updates the state of the Room, eg. the containing Characters
	 */
	@Override
	void updateState(){
	
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
	@Override
	public void updateState(Moveable character) {
		char touchedSprite = wouldTouch(character.getRectangle());	
		//System.out.println(player.getVisibleRect().toString());
		
		switch(touchedSprite){

		case 'a':case 'A':
			level.nextRoom();
		break;
		default:
		break;
		}
		
		
	}
}


