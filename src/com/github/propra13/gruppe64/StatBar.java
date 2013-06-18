package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
	private int health=200;
	private int manahealth=200;
	private JButton Inventar;
	public StatBar() {
		super();
		
		//this.setSize(panelW,panelH);
		
		this.setLayout(null);
		//Ueberschneidung mit map

		this.setBounds(0, 360, panelW, panelH);
		//this.setSize(panelW,panelH);

		this.setBackground(Color.RED);
		this.setVisible(true);

	/*	Inventar=new JButton("Inventar");
		Inventar.setBounds(10,10,150,20);
		this.add(Inventar);
		Inventar.setVisible(true);
	*/
		

		
		
	}
	
	void setLevel(int levelNr){

	}
  	@Override
  	public void paintComponent(java.awt.Graphics g){
  		super.paintComponent(g);
		g.setColor(Color.green);
  		g.fillRect(10, 10,health, 30);
	//	g.drawRect(10,50,90,30);
  		
  		
  		g.setColor(Color.blue);
  		g.fillRect(10,50,manahealth,30);
  			
	}
  	public void getStateFrom(Player p){
  		this.removeAll();
  		ArrayList<Item> slots= p.getSlots();
  		System.out.print(slots.get(0).displayedName);
  		for(int i=0;i<slots.size();i++){
 
  			slots.get(i).setLocation(220+i*60, 10);
  			slots.get(i).setBorder(BorderFactory.createLineBorder(Color.black));
  			this.add(slots.get(i));
  		}

  		repaint();

  		ArrayList<Item> items= p.getItems();
  		for(int i=0;i<items.size();i++){
  			
  			items.get(i).setLocation(220+i*60, 70);
  			items.get(i).setBorder(BorderFactory.createLineBorder(Color.black));
  			this.add(items.get(i));
  		}
  		repaint();
  	}
  	public void updateHealth(int hp){
  		this.health=2*hp;
  		repaint();
  	}

}
