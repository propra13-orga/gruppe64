package com.github.propra13.gruppe64.visible;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.github.propra13.gruppe64.*;


public class Room extends Map {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Raumnummer
	 */
	public int roomNr;
	/**
	 * Levelnummer
	 */
	public int levelnr;
	
	/**
	 * Enthaelt alle Door-Objekte der Instanz von Map
	 */
	protected ArrayList<Door> doorList;


	Timer caretaker;


	/**
	 * Wird auf diesen Raum gerade gespielt?
	 */
	private boolean active=true;


	private static int wallSize= 50;
	
	/**
	 * Erstelle Raum aus mapArray und initalisiere roomNr
	 * @param mapArray
	 * @param roomNr
	 */
	public Room(char[][] mapArray, int roomNr) {
		super(mapArray);

		this.roomNr=roomNr;
		
	}
	

	/**
	 * Setze die Groeße der Wand-Sprites
	 * @param wallW
	 * @param wallH
	 */
	public void setWallSize(int wallW, int wallH) {
		spriteheight = wallH;
		spritewidth = wallW;
		
	}
	/**
	 * Bewege alle Moveable-Objekte
	 */
	public void moveMoveable(){
		
		for(Movable mov : movables){
			mov.updateMot();
		}
		
	}
 static String charString(char[][] m){
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

	
	public void enterDoor(Door door, Player pl) {
		if(!door.open){
			pl.tell(door,"Door locked");return;
		}
		//finde das Level dieses Raumes
		if(this.getFocusPlayer()==null)return;
		Level level= this.getFocusPlayer().getLevel();
		
		if(door.getSpecial()!=null){
			
			if(pl.getClass().equals(NPlayer.class)){
				
			}
			switch(door.getSpecial()){
				case "shop": level.fallBackDoorpush(door); game.showShop();break;
				case "goal": pl.setLvlUnlocked(level.getLevelNr()+1);level.showExtern("world");break;
				case "exit": level.showExtern("world");break;
				case "entrance": level.showExtern("world");
				default:
			}
		}else{ //other Room from this level
			Door tDoor=door.getTarget();
			level.setOnDoor(tDoor);

			
		}	
		
	}
	
}


