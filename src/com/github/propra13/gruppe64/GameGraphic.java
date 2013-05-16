package com.github.propra13.gruppe64;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

//Soll in einem extra Thread laufen
public class GameGraphic implements Runnable{
	
	private Map map;
	private Container cp;
	// das content-pane von unserem JFrame
	public GameGraphic(Main myMain, Container cp) {
		// TODO Auto-generated constructor stub
		// cp ist das JPanel auf das gezeichnet wird
		this.cp=cp;
		map = new Map();
	}
	
	public boolean isReady(){
		return true;
	}
	public Map getMap(){
		return map;
	}
	// Ist das Spielfeld fertig? Darf es angezeigt werden?
	private void setReady(){
		
	}
	public void run(){
		//Ein Layout, bei dem es kein ¬??
		cp.setLayout(null);
		cp.setBackground(Color.WHITE);
		cp.removeAll();
		//load maparray
		Sprite s1= map.getSprite(0, 0);
		
		cp.add(s1);
		
		//System.exit(0);
	}
}




//Anregungen:


	//private Player[] player;
	
	/*Game(Player[] player){
		for(int i=0; i<player.length; i++){
			this.player[i] = player[i];
			//gib jedem auserdem einen Kontroller
		}
		
	}*/