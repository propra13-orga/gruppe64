package com.github.propra13.gruppe64;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable{
	

	
	/**
	 * Instanzvariablen
	 */
	private Main main; //Ganze main?
	private Container cp;
	private Player player;
	private Map map;
	
	private Timer caretaker;

	//Aktuelles Level
	private int levelNr;
	//Maximales Level
	private int lastLevelNr=3;
	
	private JPanel mapArea;
	private Level aLevel;
	private StatBar statBar;
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public Game(Container cp, Main main) {
	
		this.cp=cp;
		this.main =main;
		
		player = new Player(0,150);
		
		player.addStatBar(statBar);
		
		statBar = new StatBar();
		levelNr =1;
		statBar.setLevel(levelNr);
		
	}	

	public void run(){
		
		cp.setBackground(Color.WHITE);
		cp.setLayout(new BorderLayout());
		
		
		cp.add(statBar);
		startLevel();
		//main.pack();
		// setzt den Timer der den Spieler aktualisiert
		TimerTask action = new TimerTask() {
			public void run() {
				player.updMot();
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
		
		player.setLocation(0, 150);
		//aLevel = new Level(player, cp, levelNr);
		
		
		map.add(player);
		statBar.getStateFrom(player);
		//Reihenfolge ist wichtig, das das repaint die Child auf einem Stack sieht
		main.controller.setPlayer(player);
		map.drawMap();
		//fuege die Map in das Grund-Panel
		cp.add(map);
		cp.repaint(100);

		
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
	
}

