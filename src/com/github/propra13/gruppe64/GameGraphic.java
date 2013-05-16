package com.github.propra13.gruppe64;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

//Soll in einem extra Thread laufen
public class GameGraphic implements Runnable{
	
	private Map map;
	private Container cp;
	private Main myMain;
	// das content-pane von unserem JFrame
	public GameGraphic(Main myMain, Container cp) {
		// TODO Auto-generated constructor stub
		// cp ist das JPanel auf das gezeichnet wird
		this.cp=cp;
		map = new Map();
		this.myMain=myMain;
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
		//Ein Layout ohne 
		cp.setLayout(null);
		cp.setBackground(Color.WHITE);
		cp.removeAll();
		//load maparray
		
		
		int zeile=7, spalte=10,x,y;
		
		//Test paint sprite
		for(int i=0; i<spalte;i++){
			x=i*51+10;
			for(int j=0; j< zeile; j++){
				y=j*51+10;
				Sprite sp1 = map.getSprite(j, i);
				
				if(sp1!=null){
					System.out.print("("+x+","+y+")- {"+i+","+j+"}");
					sp1.setLocation(x,y);
					cp.add(sp1);
					
				}
			System.out.print("\n");
			}
		}
		cp.repaint();
		
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