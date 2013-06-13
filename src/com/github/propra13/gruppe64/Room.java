package com.github.propra13.gruppe64;

import java.util.ArrayList;

public class Room extends Map {
	//default WallSize
	
	private static int wallSize= 50;
	public Room(char[][] mapArray) {
		super (wallSize, wallSize);
		this.mapArray = mapArray;
		this.moveables = new ArrayList<Moveable>();
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
}
