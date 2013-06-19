package com.github.propra13.gruppe64;

import java.awt.Color;
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
	static private  int panelH =300;
	static private int panelW =800;
	private int health=200;
	private int manahealth=200;
	private JButton Inventar;
	private String str;
	private String[] strarr;
	private StringBuilder sb;
	private int textWidth;
	private Player player;
	private int gposx, gposy;
	
	public StatBar(Player pl) {
		super();
		player=pl;
		//this.setSize(panelW,panelH);
		
		this.setLayout(null);
		//Ueberschneidung mit map

		this.setBounds(0, 350, panelW, panelH);
		//this.setSize(panelW,panelH);

		this.setBackground(Color.RED);
		this.setVisible(true);
		
		textWidth=70;
		str="Hallo, frag den Alten.";
		sb= new StringBuilder(str);
		int i = 0;
		while (i + textWidth < sb.length() && (i = sb.lastIndexOf(" ", i + textWidth)) != -1) {
		    sb.replace(i, i + 1, "\n");
		}
		strarr=sb.toString().split("\n");
		

	/*	Inventar=new JButton("Inventar");
		Inventar.setBounds(10,10,150,20);
		this.add(Inventar);
		Inventar.setVisible(true);
	*/
		
	/*	JTextArea jt = new JTextArea("Hallo");
		jt.setBackground(Color.YELLOW);
		jt.setBounds(0, 100, 50, 50);
		this.add(jt);
		jt.setVisible(true);*/
		
		
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
  		
  		
  		g.setColor(Color.black);
  		if(strarr.length >0)
  			for(int i=0;i<strarr.length;i++)
  				g.drawString(strarr[i], 10, 130+i*20);
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
 
  			slots.get(i).setLocation(220+i*60, 10);
  			slots.get(i).setBorder(BorderFactory.createLineBorder(Color.black));
  			this.add(slots.get(i));
  		}

  		//repaint();

  		ArrayList<Item> items= player.getItems();
  		for(int i=0;i<items.size();i++){
  			
  			items.get(i).setLocation(220+i*60, 70);
  			items.get(i).setBorder(BorderFactory.createLineBorder(Color.black));
  			
  			if(items.get(i).name=='Y'){
  				if(player.getGold()>0) this.add(items.get(i));
  				gposx=items.get(i).getX()+5;
  				gposy=items.get(i).getY()-10;
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
  	
  	public void printSaga(){
  		
  		str="Hallo mein Freund!Die Welt wird von Katzen terrorisiert. Benutze space um auf die Katzen einzuschlagen und benutze c um deine Waffen zu wechseln. Wenn du genügend Mana hast kannst du mit h dein Leben regenerieren.Mit einem Doppelklick kannst du Lebens- und Manatränke verwenden ";
		sb= new StringBuilder(str);
		int i = 0;
		while (i + textWidth < sb.length() && (i = sb.lastIndexOf(" ", i + textWidth)) != -1) {
		    sb.replace(i, i + 1, "\n");
		}
		strarr=sb.toString().split("\n");
		repaint();
  		
  	}

}
