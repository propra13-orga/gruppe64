package com.github.propra13.gruppe64;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Main extends JFrame implements ActionListener{
	//serialiseable
	private static final long serialVersionUID = -7278907361953613792L;

	
	private Game myGame;

	private Container cp;
	
	private class myGBC extends GridBagConstraints{

		private static final long serialVersionUID = 4632170617578570378L;
		//private static final long serialVersionUID = -964164546900389807L;
		public myGBC(int gridx, int gridy){
			super();
			this.gridx = gridx;
			this.gridy = gridy;
			this.fill = GridBagConstraints.HORIZONTAL;
		}
		public myGBC(int gridx, int gridy, int gridwidth, double weightx){
			this(gridx,gridy);
			this.gridwidth = gridwidth;
			this.weightx = weightx;
		}
	}
	
	private class myJButton extends JButton{
		myJButton(String label){
			super(label);
			addActionListener(Main.this);
		}
	}
	
	/**
	 * Gibt Titel an JFrame
	 */
	public Main(){
		super("Spiel �berschrift");
		
	}
	
	/**
	 * Erzeugt alle Buttons f�r das Menu
	 */
	private JButton bNGame, bIGame, bRandom,bRead,bClose;
	private void initMain(){
		//was passiert, wenn man das Fenster schliesst TODO Schliessen-Dialog
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// das ContetnPane auf das gezeichnet werden soll
		cp = this.getContentPane();
		
		/* 
		 * First Menu-Buttons
		 */
		//Neues Spiel Button
		bNGame = new myJButton("Neues Spiel");
		bNGame.setDefaultCapable(true);
		// Neues Netzwerkgame 
		bIGame = new myJButton("Neues Netzwerk Spiel");
		//Neues Randomlevel
		bRandom = new myJButton("Speichere Random-Level");
		//Lade Level-Game
		bRead = new myJButton("Lese Level");
		//Beenden Button
		bClose = new myJButton("Beenden");

		
		cp.setLayout(new GridBagLayout());

		
		cp.add(bNGame, 	new myGBC(0,0,2,1));
		cp.add(bIGame, 	new myGBC(0,1,2,1));
		cp.add(bRandom, new myGBC(0,2,1,0.5));
		cp.add(bRead, 	new myGBC(1,2,1,0.5));
		cp.add(bClose, 	new myGBC(0,3,2,1));
		this.pack();
		this.setSize(600, 600);
		
		//this.setResizable(false);
		
		this.setVisible(true);
	}
	private void startGame(){
		//Erzeugt neues Spiel und startet es
		myGame=new Game(this.cp);
		Thread gameThread = new Thread(myGame);
		gameThread.start();
		
		
		
	}
	public static void main(String[] args) {
	
		Main mainFrame = new Main();
		mainFrame.initMain();
	
	
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==this.bNGame){
			this.startGame();
		}
		if(ae.getSource()==this.bClose){
			System.exit(0);
		}
		//netGame=new NetworkGame(myself);
		//javax.swing.SwingUtilities.invokeLater(myGame);
		
	}
}

