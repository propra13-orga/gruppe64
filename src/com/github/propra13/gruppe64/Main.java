package com.github.propra13.gruppe64;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Main extends JFrame{
	/*
	 * TODO Kruder Java-Hack, aber inner-class sind auch schlecht implementiert
	 */
	private Main myself;
	/*
	 * TODO Was ist das?
	 */
	private static final long serialVersionUID = 1L;
	private Game myGame;

	private Container cp;
	/**
	 * Gibt Titel an JFrame
	 */
	public Main(){
		super("Spiel †berschrift");
		myself=this;
		
	}
	private void initMain(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cp = this.getContentPane();
		/*
		 * Neues Spiel Button
		 */
		JButton bNGame = new JButton("Neues Spiel");

		bNGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myself.startGame();
				
			}
		});

		GridBagConstraints bNGameC = new GridBagConstraints();
		bNGameC.gridx = 1;
		bNGameC.gridy = 1;
		bNGameC.fill = GridBagConstraints.HORIZONTAL;
		bNGameC.weightx = 1.0;
		
		/*
		 * Neues Netzwerkgame 
		 */
		JButton bIGame = new JButton("Neues Netzwerk Spiel");

		bIGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//netGame=new NetworkGame(myself);
				//javax.swing.SwingUtilities.invokeLater(myGame);
			}
		});

		GridBagConstraints bIGameC = new GridBagConstraints();
		bIGameC.gridx = 1;
		bIGameC.gridy = 2;
		bIGameC.fill = GridBagConstraints.HORIZONTAL;
		bIGameC.weightx = 1.0;
		
		JButton bClose = new JButton("Beenden");

		bClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		GridBagConstraints bCloseC = new GridBagConstraints();
		bCloseC.gridx = 1;
		bCloseC.gridy = 3;
		bCloseC.fill = GridBagConstraints.HORIZONTAL;
		bCloseC.weightx = 1.0;

		cp.setLayout(new GridBagLayout());

		cp.add(bClose, bCloseC);
		cp.add(bNGame, bNGameC);
		cp.add(bIGame, bIGameC);
		this.pack();
		this.setSize(600, 600);
		//this.setResizable(false);
		
		this.setVisible(true);
	}
	private void startGame(){
		myGame=new Game(this.cp);
		// TODO vergleiche mit myGame.start()
		javax.swing.SwingUtilities.invokeLater(myGame);
		
	}
	public static void main(String[] args) {
	
		Main mainFrame = new Main();
		mainFrame.initMain();
	
	}

}

