package com.github.propra13.gruppe64;

import java.awt.Graphics;

import javax.swing.JComponent;

public class Sprite extends JComponent {

	private static final long serialVersionUID = 1419758802002837841L;
	
	//Aktuelle Position in Pixel !!!
	int x,y;
	
	//stetige 
		
	//Maximale Geschwindigkeit bei Bewegung
	double VelMax=0.1;
	// Geschwindigkeits vektor 
	public boolean[] velocity;
	
	//Ausmasse
	double dimx, dimy;
	//Bezeichnung z.b. frei, Gegner
	String name;
	
	public Sprite(String name) {
		this.name=name;
		velocity = new boolean[4];
		this.stopAll();
		
	
	}
	public void paintComponent(Graphics g){
		
		//switch
			//g.drawRect
		//unterschiedliche Sachen malen
	}
	public void startMot(int direction){
		velocity[direction]=true;
	}
	public void stopMovt(int direction){
		velocity[direction]=false;
	}
	
	public void stopAll(){
		for(int i=0; i<4; i++){
			velocity[i]=false;
		}
	}
	/**
	 * Mit einem Timer wird diese Funktion aufgerufen, diese setzt die aktuelle Position
	 */
	public void updateMotion(){
		this.setLocation( x, y);
	}
}
