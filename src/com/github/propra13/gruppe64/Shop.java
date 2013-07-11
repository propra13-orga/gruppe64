package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Shop extends Map{

	static char[][] shop = readFile();
	
	public Shop(int spritewidth, int spriteheight, Game game){
		super(shop);
		this.spritewidth= spritewidth;
		this.spriteheight= spriteheight;

		
		this.game=game;

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
}
