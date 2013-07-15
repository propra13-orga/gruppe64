package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.github.propra13.gruppe64.visible.Item;
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
	private ArrayList<QuanItem> qiArray;
	
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
		
		qiArray=new ArrayList<QuanItem>();
		
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
  		g.setFont(new Font ("Arial", Font.PLAIN , 11));
  		for(int i=0;i<qiArray.size();i++){
  			String qString;QuanItem qi=qiArray.get(i);
			if(qi.item.name=='Y')qString= player.getGold() +"$"; else qString= "x " + qi.quantity;
			if(qi.quantity>1)
				g.drawString(qString,3+i*60,55+60);
  		}
  		g.setColor(Color.white);g.finalize();

	}
  	public void getStateFrom(){
        this.removeAll();
        repaint();
        ArrayList<Item> slots= player.getSlots();

  		for(int i=0;i<slots.size();i++){
 
  			slots.get(i).setLocation(220+i*60, 0);
  			slots.get(i).setBorder(BorderFactory.createLineBorder(Color.black));
  			this.add(slots.get(i));
  		}

  		//repaint();
  		
  		ArrayList<Item> items= player.getItems();
  		qiArray = new ArrayList<QuanItem>();
   		for(Item aItem:items){
   			Boolean mustAdd=true;
   			if(qiArray.isEmpty()){ qiArray.add(new QuanItem(aItem)); mustAdd=false;}
   			else for(QuanItem qi: qiArray)
   					if(qi.item.name==aItem.name){
   						qi.quantity++;mustAdd=false;}
   			if(mustAdd)qiArray.add(new QuanItem(aItem));
   		}int i=0;
   		for(QuanItem qi: qiArray){
   			add(qi.item);
  			qi.item.setLocation(3+i*60, 55);
  			qi.item.setBorder(BorderFactory.createLineBorder(Color.black));
  			
  			i++;
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
  	
  	private class QuanItem{
  		public Item item;public int quantity=1;
  		public QuanItem(Item item){this.item=item;}
  	}

}
