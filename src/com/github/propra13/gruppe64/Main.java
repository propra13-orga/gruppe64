package com.github.propra13.gruppe64;


import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
//import java.util.Timer;
//import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener{


	private Thread gameThread;
	//private Thread musicThread;

	private Game myGame;
	public  Controller controller;
	private JPanel pWeiter;
	
	private Container cp;
	
	
	private class myGBC extends GridBagConstraints{

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
		super("-=PBJT=-");
		
	}
	
	/**
	 * Allelle Buttons fuer das Haupt-Menu
	 */
	private JButton bNGame;
	private JButton bIGame;
	private JButton bRandom;
	private JButton bRead;
	private JButton bRestart;
	private JButton bClose;


	private int xFrame=500;
	private int yFrame=400;
	
	
	private void initMain(){//TODO in Menu-Klasse auslagern
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
		
		this.setSize(xFrame, yFrame);
		
		this.setResizable(false);
		
		this.setVisible(true);
	}
	/**
	 * Started das Spiel
	 */
	private void startGame(){
		//TODO mit music Thread ersetzten, der nicht aus dem RAM abspielt
		try {
		    File wavFile=new File("res/nerv.wav");
		    AudioInputStream stream;
		    AudioFormat format;
		    DataLine.Info info;
		    Clip clip;

		    stream = AudioSystem.getAudioInputStream(wavFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();   
		}
		catch (Exception e) {
		    System.err.print("Sound-Fehler:\n");
		    System.err.print(e.toString());
		    System.err.print("\nOSX-specific\n");
		}
		
		//Controller
		controller = new Controller();
		this.setFocusable(true);
		this.addKeyListener(controller);
				
		//Neues Layout, Map (ueber Game-stat)
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		
		//Erzeugt neues Spiel und startet es
		myGame=new Game(this.cp, this);
		gameThread = new Thread(myGame);
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
		if(ae.getSource()==this.bRestart){
			this.remove(pWeiter);
			this.initMain();
		}
		//netGame=new NetworkGame(myself);
		//javax.swing.SwingUtilities.invokeLater(myGame);
		
	}
	/**
	 * hat man Gewonnen oder Verloren? TODO
	 */
	public void win(boolean b) {
		// Destroy myGame
		//cp.remove(myGame);
		myGame=null;
		
		// Weiter-Menu
		pWeiter = new JPanel();
		pWeiter.setBounds(0, 0, 600, 600);
		pWeiter.setLayout(new GridBagLayout());
		JLabel msg = new JLabel("",SwingConstants.CENTER);
		if(b){
			msg.setText("Herzlichen Glueckwunsch");
		} else{
			msg.setText("Loser!");
		}
		bRestart = new myJButton("Weiter zum Haupmenue");
		bRestart.setDefaultCapable(true);
		pWeiter.add(msg, new myGBC(0,0,1,1));
		pWeiter.add(bRestart,new myGBC(0,1,1,1));
		
		pWeiter.setVisible(true);
		
		
		cp.add(pWeiter);
		
		
		this.pack();
		this.setSize(xFrame, yFrame);
		
		
		
	}
}

