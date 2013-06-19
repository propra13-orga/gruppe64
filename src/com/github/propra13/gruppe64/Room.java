package com.github.propra13.gruppe64;

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
	Timer caretaker;
	private Level level;


	private boolean active=true;
	
	private static int wallSize= 50;
	public Room(Level aLevel, char[][] mapArray) {
		super (wallSize, wallSize);
		this.mapArray = mapArray;
		this.level = aLevel;
		//this.pos_eingang 	= getPosOf('e');
		//this.moveables = new ArrayList<Moveable>();
	}
	
//	public Room(int lvl, int room){
//		this.levelnr 		= lvl;
//		this.raumnr 		= room;
//		super.mapArray 		= readRoom(levelnr, raumnr);
//		this.pos_eingang 	= getPosOf('e');
//		//super.moveables 	= new ArrayList<Moveable>();
//		
//	}
	
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
		if(character.getClass().equals(Player.class)){
		switch(touchedSprite){
		
		case 'a':case 'A':
			if(level.isLastRoom()){
				//if(moveables.size()==1){
					level.nextRoom();
					active=false;
				//}
			} else {
				level.nextRoom();
				active=false;
			}
		break;
		default:
		break;
		}
		}
		
	}
	

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


