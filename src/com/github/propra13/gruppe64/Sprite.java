package com.github.propra13.gruppe64;

import java.awt.Color;
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
	


	//Geschwindigkeit bei Bewegung
	double vx, vy;

	int xDim, yDim ;
	char name;
	public Sprite(char name) {
		xDim=50;
		yDim=50;
		this.name=name;
		velocity = new boolean[4];
		this.stopAll();
		this.setLocation( 100, 100);
	
	}
	
	public void paintComponent(Graphics g){
		
		switch (this.name){
			case 'x': g.setColor(Color.black); g.fillRect(0, 0, xDim, yDim); break;
			case 'e': g.setColor(Color.gray);  g.fillRect(x, y, xDim, yDim); break;
			case 'a': g.setColor(Color.green); g.fillRect(x, y, xDim, yDim); break;
			case 'g': g.setColor(Color.red);   g.fillRect(x, y, xDim, yDim); break;	
		}
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
