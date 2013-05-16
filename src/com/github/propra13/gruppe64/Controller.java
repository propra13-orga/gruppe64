package com.github.propra13.gruppe64;										// # 0002

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class Controller extends KeyAdapter{									//brauche playerobject mit public x,y
	private Player player;
	public int hoch,runter,rechts,links,keyp,keyr;							//keyp: key pressed		keyr: key released
	
	
	
	Controller(Player player){
		this.player = player;
		hoch	=	KeyEvent.VK_UP;
		runter	=	KeyEvent.VK_DOWN;	
		rechts  =	KeyEvent.VK_RIGHT;
		links	=	KeyEvent.VK_LEFT;
		
	}
	
	public void keyPressed(KeyEvent e){										//	if(...&&___) -> Wenn der spieler in entgegengesetzte Richtungen drückt,
			keyp=e.getKeyCode();											//			0			1	         wird die letzte Eingabe ignoriert.
			if(keyp== hoch && player.vy != -1)player.setMot(0);				//			|			|
			if(keyp== rechts && player.vx != -1)player.setMot(1);			//		3---+---1		0		-1	---0---   1
			if(keyp== runter && player.vy !=  1)player.setMot(2);			//			|			|
			if(keyp== links && player.vx !=  1)player.setMot(3);			//			2		   -1
			
	}
	public void keyReleased(KeyEvent e){
		keyr=e.getKeyCode();
		if(keyr==hoch || keyr==runter)	player.unsetMot(0);					//			0
		if(keyr==rechts || keyr==links)	player.unsetMot(1);					//			|
																			//			+---1	
		
	}
	
	
	
}
