package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	static private  int panelH =120;
	static private int panelW =800;
	private int health=200;
	private int manahealth=200;
	private Player player;
	private int gposx, gposy;
	
	public StatBar(Player pl) {
		super();
		player=pl;
		//this.setSize(panelW,panelH);
		
		this.setLayout(null);
		//Ueberschneidung mit map

		this.setPreferredSize(new Dimension(1200,panelH));this.setMaximumSize(new Dimension(2000,panelH));
		//this.setSize(panelW,panelH);

		this.setBackground(Color.GRAY);
		this.setVisible(true);
		
		
		
	}
	
	
	
	
	void setLevel(int levelNr){

	}
  	@Override
  	public void paintComponent(java.awt.Graphics g){
  		super.paintComponent(g);
		g.setColor(Color.green);
  		g.fillRect(10, 10,health, 7);
	//	g.drawRect(10,50,90,30);
  		
  		
  		g.setColor(Color.blue);
  		g.fillRect(10,20,manahealth,7);
  		
  		g.setColor(Color.white);
  		if(player.getGold()>0){
  			g.drawString("$ "+player.getGold(),gposx,gposy);
  			
  		}
	}
  	public void getStateFrom(){
        this.removeAll();
        repaint();
        ArrayList<Item> slots= player.getSlots();

  		for(int i=0;i<slots.size();i++){
 
  			slots.get(i).setLocation(220+i*60, 3);
  			slots.get(i).setBorder(BorderFactory.createLineBorder(Color.black));
  			this.add(slots.get(i));
  		}

  		//repaint();

  		ArrayList<Item> items= player.getItems();
  		for(int i=0;i<items.size();i++){
  			
  			items.get(i).setLocation(3+i*60, 60);
  			items.get(i).setBorder(BorderFactory.createLineBorder(Color.black));
  			
  			if(items.get(i).name=='Y'){
  				if(player.getGold()>0) this.add(items.get(i));
  				gposx=items.get(i).getX()+5;
  				gposy=items.get(i).getY()+65;
  			}else{
  				this.add(items.get(i));
  			}				
  		}
  		repaint();

  	}
  	public void updateHealth(int hp){
  		this.health=2*hp;
  		repaint();
  	}

  	public void updateMana(int mn){
  		this.manahealth=2*mn;
  		repaint();
  	}
  	


}
