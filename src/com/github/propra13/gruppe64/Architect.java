package com.github.propra13.gruppe64;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.github.propra13.gruppe64.Moveable.axis;
import com.github.propra13.gruppe64.Moveable.dir;
import com.github.propra13.gruppe64.Moveable.modes;

public class Architect extends Player {
	
	
	public class ArchitectController extends KeyAdapter {
		
		
		private Architect player;
	
		public int hoch;							//keyp: key pressed		keyr: key released
	
		public int runter;
	
		public int rechts;
		
		public int links;
	
		public int keyp;
	
		public int keyr;
		
		
		
		
		
		public ArchitectController(){
			hoch	=	KeyEvent.VK_UP;
			runter	=	KeyEvent.VK_DOWN;	
			rechts  =	KeyEvent.VK_RIGHT;
			links	=	KeyEvent.VK_LEFT;
			this.player = Architect.this;
			
			
		}

		//welcher Spieler soll kontrolliert werden
		public void setPlayer(Architect player){
			this.player=player;
		}
		
		public void keyPressed(KeyEvent e){										//	if(...&&___) -> Wenn der spieler in entgegengesetzte Richtungen dr��ckt,
				keyp=e.getKeyCode();		System.out.println(keyp);									
				if(player!=null){																	//			0			1	         wird die letzte Eingabe ignoriert.
					
						if(keyp== hoch && player.getVel()[1] != -1)player.setMot(dir.up);			//			|			|
						if(keyp== rechts && player.getVel()[0] != -1)player.setMot(dir.right);		//		3---+---1		0		-1	---0---   1
						if(keyp== runter && player.getVel()[1] !=  1)player.setMot(dir.down);		//			|			|
						if(keyp== links && player.getVel()[0] !=  1)player.setMot(dir.left);		//			2		   -1
						
					
				}
		}
		public void keyReleased(KeyEvent e){
			keyr=e.getKeyCode();
			if(player!=null){
				if(keyr==hoch || keyr==runter)	player.unsetMot(axis.y);				//			1
				if(keyr==rechts || keyr==links)	player.unsetMot(axis.x);				//			|
				
				}
			}
		}
	
		@Override
		public void updateMot(){
			
			int x=this.getX();
			int y=this.getY();
			if(!(vel[0]<0&&x+vel[0]<0)){
				
				this.setLocation(x+vel[0],y);
				if(map.getClass().equals(Room.class))map.setLocation(map.getX()-vel[0], map.getY());
				if(x>map.getWidth()){
					map.setSize(map.getWidth()+Map.spritewidth,map.getHeight());
				}
				
			}
			x=this.getX();
			y=this.getY();
			if(!(vel[1]>0&&y+vel[1]<0)){
				
				this.setLocation(x,y-vel[1]);
				if(map.getClass().equals(Room.class))map.setLocation(map.getX(), map.getY()+vel[1]);
				if(y>map.getHeight()){
					map.setSize(map.getWidth(),map.getHeight()+Map.spriteheight);
				}
			}
		//	map.updateState(this);
			x=this.getX();
			y=this.getY();
		}
		
		
		
	
	public Architect(){
	super(50,50);nick="arch";
	
	}

	@Override
	protected void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getNick() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
