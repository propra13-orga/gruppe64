package com.github.propra13.gruppe64.visible;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.propra13.gruppe64.ActiveArea;
import com.github.propra13.gruppe64.Game;
import com.github.propra13.gruppe64.Player;


public class World extends Map implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean network=false;
	private JPanel msgBox;
	private JButton okButton;
	private JButton backButton;
	public transient Game game;
	public Point lastPos;
	
	public World(char[][] tArray) {
		super(tArray);
		lastPos=new Point(0,50);
	}
	/**
	 * Gibt die Anzahl der Level aus
	 */
	public int getMaxLevel(){
		return 5;
	}
	/**
	 * Gibt Elterncontainer zurück
	 */
	public Container getCP(){
		return getJPanel().getParent();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Durch eine Tuer gehen. Spielerbewegung wird gestoppt und neues Level wird gestartet.
	 */
	@Override
	public void enterDoor(Door door, Player mv) {
		if(!door.open)return;
		if(door.getSpecial().equals("level")){
			game.startLevel(door.specialNr);this.stopMotion();
			lastPos= new Point(door.getX(),door.getY());
		}
	/**
	 * Alle Tueren bis einschließlich Level lvlUnlocked werden als offen gesetzt
	 * @param lvlUnlocked 	
	 */
	}
	private void setOpenDoors(int lvlUnlocked){
		for(ActiveArea iAA: activeAreas){
			if(iAA.getClass().equals(Door.class)){
				if(((Door)iAA).specialNr>lvlUnlocked){((Door)iAA).open=false;}else{((Door)iAA).open=true;}
			}
		}
	}
	/*@Override
	public Sprite add(Object obj){
		Sprite sp=super.add(obj);
		if(sp instanceof Player){
			sp.setLocation(lastPos);
			setOpenDoors(((Player)sp).getLvlUnlocked());
		}return sp;
	}*/

}
