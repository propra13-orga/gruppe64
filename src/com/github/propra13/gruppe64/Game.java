package com.github.propra13.gruppe64;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;


//Soll in einem extra Thread laufen
public class Game extends JPanel implements Runnable{
	

	
	/**
	 * @uml.property  name="map"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Map map;
	
	private Player player;
	
	Timer caretaker;
	/**
	 * @uml.property  name="cp"
	 */
	private Container cp;
	private Main main;

	//Aktuelles Level
	private int aLevel=1;
	//Maximales Level
	private int mLevel=3;
	
	/**
	 * @param cp ist content-pane von unserem JFrame
	 */
	public Game(Container cp, Main main) {
	
		this.cp=cp;
		this.main =main;
		
	}
	
	public boolean isReady(){
		return true;
	}
	/**
	 * @return
	 * @uml.property  name="map"
	 */
	public Map getMap(){
		return map;
	}
	// Ist das Spielfeld fertig? Darf es angezeigt werden?
	private void setReady(){
		
	}
	public void run(){

		startLevel();

		TimerTask action = new TimerTask() {
			public void run() {
				player.updMot();
			}
		};

		caretaker = new Timer();
		caretaker.schedule(action, 0, 5);
		
		

	}
	private void startLevel(){
		//Ein Layout ohne 
				cp.setLayout(null);
				cp.setBackground(Color.WHITE);
				cp.removeAll();
				//load maparray
				
				map = new Map(50,50, aLevel, this);
				player = new Player(0,150,map);
				//player.setVisible(true);
				map.add(player);
				
				//fuege die Map in das Grund-Panel
				cp.add(map);
				//zeichne die Map

				main.controller.setPlayer(player);
				map.drawMap();

				map.repaint(100);
				
	}
	public void gameOver() {
		caretaker.cancel();
		caretaker.purge();
		main.win(false);
		
	}

	public void nextLevel() {
		//setzte naechstes Level
		aLevel++;
		if(aLevel>mLevel){
			caretaker.cancel();
			caretaker.purge();
			main.win(true);
		}
		map.removeAll();
		cp.remove(map);
		startLevel();
	}


	
}

