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
	
	private static int wallSize= 50;
	public Room(char[][] mapArray) {
		super (wallSize, wallSize);
		this.mapArray = mapArray;
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
			mov.updMot();
		}
		
	}
	

	/*
	 * Kopiert res/Karten/Level[lvl]_Raum[room].txt in char[][]
	 * 
	 * AUSGABE:
	 * - char[][] = Map[y][x], wobei x,y die Koordinaten des
	 * 		JPanel-Koordinatensystems sind
	 * 	ODER
	 * - null, wenn res/Karten/Level[lvl]_Raum[room].txt nicht existiert
	 */
	public char[][] readRoom(int lvl, int room){
		
		int mapwidth, mapheight;
		int[] mapsize = getMapSize(lvl, room);
		
		if(mapsize==null){return null;}
		mapheight= mapsize[0];
		mapwidth = mapsize[1];
		
		char[][] map = readFile( mapwidth, mapheight, lvl, room);
		return map;
	}
}


