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
	/**
	 * @uml.property  name="cp"
	 */
	private Container cp;
	
	/**
	 * @param cp ist content-pane von unserem JFrame
	 */
	public Game(Container cp) {
	
		this.cp=cp;
		
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
		//Ein Layout ohne 
		cp.setLayout(null);
		cp.setBackground(Color.WHITE);
		cp.removeAll();
		//load maparray
		
		map = new Map(50,50);
		player = new Player(300,300,map);
		//player.setVisible(true);
		map.add(player);
		
		//fuege die Map in das Grund-Panel
		cp.add(map);
		//zeichne die Map
		map.drawMap();
		map.repaint(100);
		TimerTask action = new TimerTask() {
			public void run() {
				player.updMot();
			}
		};

		Timer caretaker = new Timer();
		caretaker.schedule(action, 0, 100);
		
		Controller controller1 = new Controller(player);
		this.cp.addKeyListener(controller1);
		

	}


	
}

