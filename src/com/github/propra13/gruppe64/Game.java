package com.github.propra13.gruppe64;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class Game implements Runnable{
	

	
	/**
	 * Instanzvariablen
	 */
	private Main main; 
	private Map map;
	private Container cp;
	
	private Player player;
	private StatBar statBar;
	private Chat chatp;
	private JTextField chatinput;
	private World world;
	//active  Level
	private Level aLevel;
	
	private Timer caretaker;
	
	/** active Level **/
	private int aLevelNr;
	/**highest acessible Level **/
	private int levelNr;
	/**last Level**/
	private int lastLevelNr;
	
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public Game(Container cp, Main main, Controller controller) {
		this.cp=cp;
		this.main =main;
		
		//player and his statBar
		player = new Player(0,150);
		statBar = new StatBar(player);
		chatp = new Chat(player);
		chatinput = new JTextField();
		chatinput.setBounds(0, 600, 700, 20);
		chatinput.setToolTipText("chatinput");
		chatinput.addKeyListener(controller);
		
		
		
		cp.setBackground(Color.WHITE);
		cp.setLayout(null);//cp.setLayout(new BorderLayout());
		//which level is reachable
		levelNr =1;
		aLevelNr=1;
	
		
		statBar.setLevel(aLevelNr);
		world = new World(50,50,aLevelNr,this);
		lastLevelNr=world.getMaxLevel();
		
		
	}	

	public void run(){
		player.addStatBar(statBar);
		player.addChatPane(chatp);
		player.addChatInput(chatinput);
		

		initPlayer();
		
		
		//show initial world
		
		
		//start first level
		startLevel();
		
		
		

	}
	public void initPlayer(){
		//make Player ready
		main.controller.setPlayer(player);
		cp.add(statBar);
		statBar.repaint();
		cp.add(chatp,BorderLayout.CENTER);
		chatp.repaint();
		cp.add(chatinput,BorderLayout.CENTER);
		chatinput.repaint();
	}
	public void startLevel(){
		//entferne von Game verwaltete map
		if(map!=null){
			map.stopMotion();
			map.removeAll();
			map.remove(player);
			cp.remove(map);
			
			map=null;
		}
		aLevel = new Level(this, aLevelNr);
		player.setLevel(aLevel);
 
		//aLevel.nextRoom();
		aLevel.initLevel();
		statBar.getStateFrom();
		statBar.repaint(60);

	}
	
	/**
	 * beendet das Game
	 * TODO 
	 */
	public void gameOver() {

		cp.removeAll();
		cp.repaint();
		main.win(false);
		
	}
	
	/**
	 * Wechset ins naechste Level
	 */
	public void nextLevel() {
		//setzte naechstes Level
		aLevelNr++;
		if(aLevelNr>lastLevelNr){
			cp.removeAll();
			main.win(true);
		}else {
			//startLevel();
			showShop();
		}
	}

	public void showShop(){
		map = new Shop(50,50,this);
		map.add(player);
		
		statBar.getStateFrom();
		map.drawMap();
		cp.add(map,BorderLayout.CENTER);
		//map.startMotion();
		map.repaint();
		
	}


	public Container getCP() {
		return cp;
	}

	public Player getPlayer() {
		return player;
	}

	public void showWorld() {
		//this.levelNr = levelNr;
	}
	
}

