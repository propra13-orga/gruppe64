package com.github.propra13.gruppe64;									// # 0001

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class Controller extends KeyAdapter{								//brauche playerobject mit public x,y
	private Player player;
	public int hoch,runter,rechts,links;
	
	
	
	Controller(Player player){
		this.player = player;
		hoch	=	KeyEvent.VK_UP;
		runter	=	KeyEvent.VK_DOWN;	
		rechts  =	KeyEvent.VK_RIGHT;
		links	=	KeyEvent.VK_LEFT;
		
	}
	
	public void keyPressed(KeyEvent e){									//	if(....) -> Wenn der spieler in entgegengesetzte Richtungen drückt,
		switch (e.getExtendedKeyCode()){								//			0			1	         wird die letzte Eingabe ignoriert.
			case hoch:		if(player.vy != -1)player.setMot(0) break;	//			|			|
			case rechts: 	if(player.vx != -1)player.setMot(1) break;	//		3---+---1		0		-1	---0---   1
			case runter: 	if(player.vy !=  1)player.setMot(2) break;	//			|			|
			case links: 	if(player.vx !=  1)player.setMot(3) break;	//			2		   -1
		}	
	}
	public void keyReleased(KeyEvent e){
		switch (e.getExtendedKeyCode()){								//			0
			case hoch:													//			|
			case runter: 	player.unsetMot(0);break;					//			+---1
			case rechts:				
			case links: 	player.unsetMot(1);break;	
		}
	}
	
	
	
}
