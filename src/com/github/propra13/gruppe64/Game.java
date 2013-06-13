package com.github.propra13.gruppe64;


import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.util.ArrayList;
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
	private int aLevel=1;
	//Maximales Level
	private int mLevel=3;
	private JPanel mapArea;
	private ArrayList<String> levelPaths;
	private StatBar statBar;
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public Game(Container cp, Main main) {
	
		this.cp=cp;
		this.main =main;
		levelPaths = new ArrayList<String>();
		levelPaths.add("somePath");
		statBar = new StatBar();
	}	

	public void run(){
		
		cp.setBackground(Color.WHITE);
		cp.removeAll();
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
		map = new Map(50,50, aLevel, this);
		
		player = new Player(0,150);
		map.add(player);
		
		//Reihenfolge ist wichtig, das das repaint die Child auf einem Stack sieht
		main.controller.setPlayer(player);
		map.drawMap();
		//fuege die Map in das Grund-Panel
		cp.add(map);
		//main.pack();
		//zeichne die Map alle 100 millisec
		map.repaint(100);
		
	}
	
	/**
	 * beendet das Game
	 * TODO 
	 */
	public void gameOver() {
		caretaker.cancel();
		caretaker.purge();
		cp.remove(map);
		map=null;
		main.win(false);
		
	}
	
	/**
	 * Wechset ins naechste Level
	 */
	public void nextLevel() {
		//setzte naechstes Level
		aLevel++;
		//map.removeAll();
		cp.remove(map);
		map=null;
		if(aLevel>mLevel){
			caretaker.cancel();
			caretaker.purge();
			main.win(true);
		}else {
			startLevel();
		}
	}
	
}

