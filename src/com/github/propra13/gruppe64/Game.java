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
	/**
	 * @uml.property  name="cp"
	 */
	private Container cp;
	
	/**
	 * Der Trick ist hier, das der Thread vom Main, dann f�r die Map benutzt wird
	 * @param cp ist content-pane von unserem JFrame
	 */
	public Game(Container cp) {
	
		//default bzw. erste Map wird geladen
		map = new Map();
		//fuege die Map in das Grund-Panel
		cp.add(map);
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
		
		
		int zeile=7, spalte=10,x,y;
		//TODO Wo soll das eigentlich hin (vgl. WIKI)
		//Test paint sprite
		for(int i=0; i<spalte;i++){
			x=i*50+10;
			for(int j=0; j< zeile; j++){
				y=j*50+10;
				Sprite sp1 = map.getSprite(j, i);
				
				if(sp1!=null){
					//System.out.print("("+x+","+y+")- {"+i+","+j+"}");
					sp1.setLocation(x,y);
					cp.add(sp1);
					
				}
			//System.out.print("\n");
			}
		}
		
		
		cp.repaint();
		
		Player player = new Player();
		cp.add(player);
		Controller controller1 = new Controller(player);
		this.cp.addKeyListener(controller1);
		//System.exit(0);
		cp.repaint();

	}


	
}

