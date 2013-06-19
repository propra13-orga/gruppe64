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


@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable{
	

	
	/**
	 * Instanzvariablen
	 */
	private Main main; 
	private Map map;
	private Container cp;
	
	private Player player;
	private StatBar statBar;
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
	public Game(Container cp, Main main) {
		this.cp=cp;
		this.main =main;
		
		//player and his statBar
		player = new Player(0,150);
		statBar = new StatBar(player);
		player.addStatBar(statBar);
		
		cp.setBackground(Color.WHITE);
		cp.setLayout(new BorderLayout());
		//which level is reachable
		levelNr =1;
		aLevelNr=1;
	
		
		statBar.setLevel(aLevelNr);
		world = new World(50,50,aLevelNr,this);
		lastLevelNr=world.getMaxLevel();
		
		
	}	

	public void run(){
		
		cp.setBackground(Color.RED);
		cp.setLayout(new BorderLayout());
		
		//make Player ready
		main.controller.setPlayer(player);
		cp.add(statBar);
		statBar.repaint();
		
		//show initial world
		
		
		//start first level
		startLevel();
		
		
		

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
//<<<<<<< HEAD
		aLevel.nextRoom();
//=======

//		//load maparray
//		if(levelNr!=2 && levelNr != 4)
//			map = new Map(50,50, levelNr, this);
//		else
//			map=new Shop(50,50,this);
//		//TODO set Player at Entrance
//		player.setLocation(0, 150);
//		//aLevel = new Level(player, cp, levelNr);
//		
//		//add player to map
//		map.add(player);
//		statBar.getStateFrom();
//
//		//Reihenfolge ist wichtig, das das repaint die Child auf einem Stack sieht
//		main.controller.setPlayer(player);
//		map.drawMap();
//		//fuege die Map in das Grund-Panel
//		cp.add(map);
//>>>>>>> 5be603d894af9ce92abca4ee5042a17c8966a7d1
		statBar.getStateFrom();
		statBar.repaint(60);

	}
	
	/**
	 * beendet das Game
	 * TODO 
	 */
	public void gameOver() {
		caretaker.cancel();
		caretaker.purge();

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
		cp.add(map);
		map.startMotion();
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

