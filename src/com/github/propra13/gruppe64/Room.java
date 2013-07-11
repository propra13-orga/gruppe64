package com.github.propra13.gruppe64;

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

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Image img9 = Toolkit.getDefaultToolkit().getImage("res/oldman.png");
		for(int i=0;i<this.mapheight;i++){
			for(int j=0;j<this.mapwidth;j++){
			    g.drawImage(img9, j*wallSize, i*wallSize, this);
			    
			}
				
		}
		g.finalize();
	}

	@Override
	public void showMsg() {
		// TODO Auto-generated method stub
		
	}
}


