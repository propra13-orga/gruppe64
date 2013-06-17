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
public class Game extends JPanel implements Runnable, Action{
	

	
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
	private int alevelNr;
	/**highest open Level **/
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
		statBar = new StatBar();
		player.addStatBar(statBar);
		
		cp.setBackground(Color.WHITE);
		cp.setLayout(new BorderLayout());
		//which level is reachable
		levelNr =1;
		alevelNr=1;
		
		statBar.setLevel(alevelNr);
		world = new World(50,50,alevelNr,this);
		lastLevelNr=world.getMaxLevel();
		
		
	}	

	public void run(){
		

		cp.setBackground(Color.RED);
		cp.setLayout(new BorderLayout());
		
		//show initial world
		
		cp.add(statBar);

		//cp.validate();
		startLevel();
		//main.pack();
		// setzt den Timer der den Spieler aktualisiert
		TimerTask action = new TimerTask() {
			public void run() {
				
				for (Moveable mov: map.getMovables()){
					mov.updateMot();
					if(map.getMovables().size()>1)
						if(mov.equals(map.getMovables().get(1))){
							mov.attemptAttack();
							cp.repaint();

						}	
				}
				//aLevel.getaRoom().updateMotion();
			}
		};

		caretaker = new Timer();
		caretaker.schedule(action, 0, 5);
		
		
		

	}
	private void startLevel(){
		//kein Layoutmanager
		
		
		
		
		//load maparray
		map = new Map(50,50, levelNr, this);
		//TODO set Player at Entrance
		player.setLocation(0, 150);
		//aLevel = new Level(player, cp, levelNr);
		
		//add player to map
		map.add(player);
		statBar.getStateFrom(player);
		
		//Reihenfolge ist wichtig, das das repaint die Child auf einem Stack sieht
		main.controller.setPlayer(player);
		map.drawMap();
		//fuege die Map in das Grund-Panel
		cp.add(map);
		map.repaint();


		
	}
	
	/**
	 * beendet das Game
	 * TODO 
	 */
	public void gameOver() {
		caretaker.cancel();
		caretaker.purge();
		cp.removeAll();
		map=null;
		main.win(false);
		
	}
	
	/**
	 * Wechset ins naechste Level
	 */
	public void nextLevel() {
		//setzte naechstes Level
		levelNr++;
		//aLevel.purge();
		
		cp.remove(map);
		map=null;
		if(levelNr>lastLevelNr){
			caretaker.cancel();
			caretaker.purge();
			main.win(true);
		}else {
			startLevel();
		}
	}

	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putValue(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
	
}

