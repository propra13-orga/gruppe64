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
public class SGame extends Game implements Runnable{
	

	
	/**
	 * Instanzvariablen
	 */
	protected ArrayList<Player> playerList;

	
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public SGame(Container cp, Main main, Controller controller) {
		super(cp,main,controller);
		addPl(super.getPlayer());
	}	
	
	public void addPl(Player pl){
		playerList.add(pl);
		
	}
	public void correctNicks(){
		for(Player pl:playerList){
			for(Player pls:playerList){
				int i=0;
				if(!pl.equals(pls))
					if(pl.getNick()==pls.getNick()) pls.setNick(pls.getNick()+"("+(i++)+")");
			}
		}
	}
	public void run(){
		//super.run();
	}


	
}