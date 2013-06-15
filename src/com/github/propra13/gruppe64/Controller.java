package com.github.propra13.gruppe64;										// # 0002

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.github.propra13.gruppe64.Moveable.axis;
import com.github.propra13.gruppe64.Moveable.dir;



public class Controller extends KeyAdapter{									//brauche playerobject mit public x,y
	/**
	 * @uml.property  name="player"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Player player;
	/**
	 * @uml.property  name="hoch"
	 */
	public int hoch;							//keyp: key pressed		keyr: key released
	/**
	 * @uml.property  name="runter"
	 */
	public int runter;
	/**
	 * @uml.property  name="rechts"
	 */
	public int rechts;
	/**
	 * @uml.property  name="links"
	 */
	public int links;
	/**
	 * @uml.property  name="keyp"
	 */
	public int keyp;
	/**
	 * @uml.property  name="keyr"
	 */
	public int keyr;
	
	public int attack;
	
	
	public Controller(Player player){
		this.player = player;
		hoch	=	KeyEvent.VK_UP;
		runter	=	KeyEvent.VK_DOWN;	
		rechts  =	KeyEvent.VK_RIGHT;
		links	=	KeyEvent.VK_LEFT;
		attack	=	KeyEvent.VK_SPACE;
		
		/* WASD
		hoch	=	KeyEvent.VK_W;
		runter	=	KeyEvent.VK_S;	
		rechts  =	KeyEvent.VK_D;
		links	=	KeyEvent.VK_A;*/
		
	}
	public Controller(){
		hoch	=	KeyEvent.VK_UP;
		runter	=	KeyEvent.VK_DOWN;	
		rechts  =	KeyEvent.VK_RIGHT;
		links	=	KeyEvent.VK_LEFT;
		attack	=	KeyEvent.VK_SPACE;
		
		/* WASD
		hoch	=	KeyEvent.VK_W;
		runter	=	KeyEvent.VK_S;	
		rechts  =	KeyEvent.VK_D;
		links	=	KeyEvent.VK_A;*/
		
	}
	//welcher Spieler soll kontrolliert werden
	public void setPlayer(Player player){
		this.player=player;
	}
	
	public void keyPressed(KeyEvent e){										//	if(...&&___) -> Wenn der spieler in entgegengesetzte Richtungen drückt,
			//System.out.print("kp");
			keyp=e.getKeyCode();											
			if(player!=null){																//			0			1	         wird die letzte Eingabe ignoriert.
				if(keyp== hoch && player.getVel()[1] != -1)player.setMot(dir.up);			//			|			|
				if(keyp== rechts && player.getVel()[0] != -1)player.setMot(dir.right);		//		3---+---1		0		-1	---0---   1
				if(keyp== runter && player.getVel()[1] !=  1)player.setMot(dir.down);		//			|			|
				if(keyp== links && player.getVel()[0] !=  1)player.setMot(dir.left);		//			2		   -1
				if(keyp== attack)player.attemptAttack();
			}
	}
	public void keyReleased(KeyEvent e){
		keyr=e.getKeyCode();
		if(player!=null){
			if(keyr==hoch || keyr==runter)	player.unsetMot(axis.y);				//			1
			if(keyr==rechts || keyr==links)	player.unsetMot(axis.x);				//			|
		}																			//			+---0
		
	}
	
	
	
}
