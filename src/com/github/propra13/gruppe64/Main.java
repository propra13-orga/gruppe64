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

	
	private GameGraphic myGame;

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
		myJButton(String label,ActionListener al){
			super(label);
			addActionListener(al);
		}
	}
	private JButton bNGame, bIGame, bRandom,bRead,bClose;
	/**
	 * Gibt Titel an JFrame
	 */
	public Main(){
		super("Spiel †berschrift");
		
	}
	private void initMain(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cp = this.getContentPane();
		/*
		 * Neues Spiel Button
		 */
		bNGame = new JButton("Neues Spiel");
		bNGame.setDefaultCapable(true);
		bNGame.addActionListener(this);
		/*
		 * Neues Netzwerkgame 
		 */
		 bIGame = new JButton("Neues Netzwerk Spiel");
		 bIGame.addActionListener(this);
		//Neues Randomlevel
		 bRandom = new JButton("Speichere Random-Level");
		//Lade Level-Game
		bRead = new JButton("Lese Level");
		 //Beenden Button
		bClose = new JButton("Beenden");
		bClose.addActionListener(this);
		
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
		myGame=new GameGraphic(this,this.cp);
		//javax.swing.SwingUtilities.invokeLater(myGame);
		Thread gameThread = new Thread(myGame);
		gameThread.start();
		
		Controller controller1 = new Controller(new Player());
		this.cp.addKeyListener(controller1);
		
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

