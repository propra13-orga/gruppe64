package com.github.propra13.gruppe64;

import java.awt.Color;

import javax.swing.JPanel;
/**
 * Under all Maps (Room, Shop) the StatBar shows the current State of the Player
 * 	-Health
 * 	-Armor
 * 	-Items
 * 	-Story/Text
 * @author vad
 *
 */


public class StatBar extends JPanel{
	static private  int panelH =200;
	static private int panelW =500;

	public StatBar() {
		super();
		//Ueberschneidung mit map
		this.setBounds(0, 400, panelW, panelH);
		this.setBackground(Color.RED);
		this.setLayout(null);
		this.setVisible(true);
	}

}
