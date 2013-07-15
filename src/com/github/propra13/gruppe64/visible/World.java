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
	public int getMaxLevel(){
		return 5;
	}
	public Container getCP(){
		return this.getParent();
	}
	public void showMsg(){
		msgBox = new JPanel();
		msgBox.setLocation(this.getWidth()/2-msgBox.getWidth()/2, this.getHeight()/2-msgBox.getHeight()/2);
		this.add(msgBox);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enterDoor(Door door, Player mv) {
		if(!door.open)return;
		if(door.getSpecial().equals("level")){
			game.startLevel(door.specialNr);this.stopMotion();
			lastPos= new Point(door.getX(),door.getY());
		}
		
	}
	private void setOpenDoors(int lvlUnlocked){
		for(ActiveArea iAA: activeAreas){
			if(iAA.getClass().equals(Door.class)){
				if(((Door)iAA).specialNr>lvlUnlocked){((Door)iAA).open=false;}else{((Door)iAA).open=true;}
			}
		}
	}
	@Override
	public Component add(Component sp){
		Component c=super.add(sp);
		if(Player.class.isAssignableFrom(sp.getClass())){
			sp.setLocation(lastPos);
			setOpenDoors(((Player)sp).getLvlUnlocked());
		}return c;
	}

}
