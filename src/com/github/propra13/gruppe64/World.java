package com.github.propra13.gruppe64;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class World extends Map implements ActionListener{
	private boolean network=false;
	private JPanel msgBox;
	private JButton okButton;
	private JButton backButton;


	public World(int spritewidth, int spriteheight, int level, Game game) {
		//super(spritewidth, spriteheight, level, game);
		// TODO Auto-generated constructor stub
	}
	public World(int spritewidth, int spriteheight, int level, SGame game) {
		//super(spritewidth, spriteheight, level, game);
		// TODO Auto-generated constructor stub
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


}
