package com.github.propra13.gruppe64.visible;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.github.propra13.gruppe64.ActiveArea;
import com.github.propra13.gruppe64.Game;
import com.github.propra13.gruppe64.Player;

public class Shop extends Map{

	static char[][] shop = readFile();
	
	/**
	 * Initalisiere Shop-Raum. Lese shop.txt aus und lege die Groeße der jeweiligen Sprites fest.
	 * Ordne Shop einer Game-Instanz zu.
	 * @param spritewidth
	 * @param spriteheight
	 * @param game
	 */
	public Shop(int spritewidth, int spriteheight, Game game){
		super(shop);
		this.spritewidth= spritewidth;
		this.spriteheight= spriteheight;

		
		this.game=game;

	}
	public Shop(char[][] tArray) {
		// TODO Auto-generated constructor stub
	}
	/*//@Override
	public void updateState(Moveable character) {

		char touchedSprite = wouldTouch(player.getRectangle());	
		//System.out.println(player.getVisibleRect().toString());
		
		switch(touchedSprite){

		case 'a':case 'A':
			game.startLevel();
		break;
		default:
		break;
		}
		
		
	}*/
	// TODO ersetzten durch mapgen
	static public char[][] readFile(){
		
		char[][] map = new char[7][10];
		
		FileReader f;
		String currentline;
		try {
			f = new FileReader("res/Karten/shop.txt");
			BufferedReader buffer = new BufferedReader(f);
			
			for( int j=0; j<7; j++){
				currentline = buffer.readLine();
			
				for( int i=0; i< currentline.length(); i++){
					map[j][i]= currentline.charAt(i);
				}
			}
			buffer.close();
		}
		catch (IOException e) {
			return null;
		}
		catch (NullPointerException e){
			/* 
			 * Durch diese catch-Abfrage wird der Fall abgefanden, dass im txt-File
			 * weniger Zeilen sind als angegeben. Somit terminiert die NullPointerException
			 * nicht den kompletten Prozess, sondern bricht nur das beschreiben des Arrays map
			 * ab und dieses ist ja dann schon fertig...
			 */
		}
		return map;

	}
	
	/**
	 * Fuege den Player in Shop ein
	 * @param pl Objekt der Klasse Player
	 * @return null, wenn erfolgreich terminiert
	 */
	public Component add(Player pl){
		//Map is Super
		super.add(pl);
		for(ActiveArea aActive: activeAreas){
			if(aActive.getClass().equals(Door.class)){
				Door tDoor=(Door)aActive;
				if(tDoor.getSpecial().equals("exit")){
					//TODO
				}
			}
			
		}
		return null;
	}
	
	@Override
	public void enterDoor(Door door, Player mv) {
		if(door.getSpecial()==null)return;
		switch(door.getSpecial()){
		case "exit": mv.getLevel().popRoom();break;
		}
		
	}
}
