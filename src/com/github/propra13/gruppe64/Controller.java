package com.github.propra13.gruppe64;										// # 0002

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.github.propra13.gruppe64.visible.Movable.axis;
import com.github.propra13.gruppe64.visible.Movable.dir;



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
	
	public int switchW;
	
	public int switchA;
	
	public int swichT;
	
	public int hCast;
	private int enterRoom;
	
	boolean hochp=true,runterp=true,rechtsp=true,linksp=true,attackp=true,enterp=true;
	
	
	
	public Controller(Player player){
		this();
		this.player = player;
		
		
	}
	public Controller(){
		hoch	=	KeyEvent.VK_UP;
		runter	=	KeyEvent.VK_DOWN;	
		rechts  =	KeyEvent.VK_RIGHT;
		links	=	KeyEvent.VK_LEFT;
		attack	=	KeyEvent.VK_SPACE;
		switchW =	KeyEvent.VK_C;
		swichT =	KeyEvent.VK_X;
		switchA=	KeyEvent.VK_V;
		hCast = KeyEvent.VK_H;
		enterRoom = KeyEvent.VK_E;
		
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
	
	public void keyPressed(KeyEvent e){										
			keyp=e.getKeyCode();
			if(player!=null){																	
				if(!player.getChatterBox().ownsFocus()){
					if(keyp== hoch &&hochp){player.setMot(dir.up);hochp=false;}			
					if(keyp== rechts &&rechtsp){player.setMot(dir.right);rechtsp=false;}		
					if(keyp== runter &&runterp){player.setMot(dir.down);runterp=false;}		
					if(keyp== links && linksp){player.setMot(dir.left);linksp=false;}		
					if(keyp== attack&&attackp){player.attemptAttack();attackp=false;}
					if(keyp== enterRoom&&enterp){player.performAction();enterp=false;}
				}
			}
	}
	public void keyReleased(KeyEvent e){
		keyr=e.getKeyCode();
		if(player!=null){
			if((keyr==hoch&&player.getVel()[1] != -1) || (keyr==runter&&player.getVel()[1] !=  1)){	
				player.unsetMot(axis.y); 
				if(keyr==hoch)hochp=true;else runterp=true;}

			if((keyr==rechts&&player.getVel()[0] != -1) || (keyr==links&& player.getVel()[0] !=  1)){
				player.unsetMot(axis.x); 
				if(keyr==rechts)rechtsp=true;else linksp=true;}	
			if(keyr== attack){attackp=true;}
			if(keyr== enterRoom){enterp=true;}
			if(keyr==switchW && !player.getChatterBox().ownsFocus()) player.switchweapon();
			if(keyr==switchA && !player.getChatterBox().ownsFocus()) player.switcharmor();
			if(keyr==hCast && !player.getChatterBox().ownsFocus()) player.use('H');			
			if(keyr==KeyEvent.VK_ENTER && player.getChatterBox().ownsFocus()){
				player.tell((ActiveArea)player, player.getChatterBox().getInput());
				player.getChatterBox().clearInputText();
			}
		}
	}
	
	
	
	
}
