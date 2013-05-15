package com.github.propra13.gruppe64;

import java.awt.Container;

import javax.swing.JFrame;

//Soll in einem extra Thread laufen
public class Game implements Runnable{
	
	private Map map;
	private Container cp;
	// das content-pane von unserem JFrame
	public Game(Main myMain, Container cp) {
		// TODO Auto-generated constructor stub
		// Create Map, stelle sie auch dem cp dar (1.ändere sein Layoutmanager, 2.add)
		
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
		// TODO
		// Mein Main erzeugt alles wie gewünscht und euer Game.run() schliest
		// Hoffe Ihr macht mehr daraus. Dies diente nur meinem "Proof of concept"
		// 
		this.cp.setLayout(null);
		System.exit(0);
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