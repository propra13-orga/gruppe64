package com.github.propra13.gruppe64;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * Under all Maps (Room, Shop) the StatBar shows the current State of the Player
 * 	-Health
 * 	-Armor
 * 	-Items
 * 	-Story/Text
 * @author vad
 *
 */


public class StatBar extends JPanel{
	static private  int panelH =200;
	static private int panelW =500;
	static private int health=100;
	private JButton Inventar;
	public StatBar() {
		super();
		//Ueberschneidung mit map
		this.setBounds(0, 400, panelW, panelH);
		//this.setSize(panelW,panelH);
		this.setBackground(Color.RED);
		this.setLayout(null);
		this.setVisible(true);
	/*	Inventar=new JButton("Inventar");
		Inventar.setBounds(10,10,150,20);
		this.add(Inventar);
		Inventar.setVisible(true);
	*/
		
		
	}
  	@Override public void paintComponent(java.awt.Graphics g){

		g.drawRect(10, 10,100, 30);
	//	g.drawRect(10,50,90,30);
	}
  	
  	public void getStateFrom(Player p){
  		
  	}
  	public void updateHealth(int health){
  		this.health=health;
  		
  	}
}
