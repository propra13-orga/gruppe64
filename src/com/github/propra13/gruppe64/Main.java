package com.github.propra13.gruppe64;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
//import java.util.Timer;
//import java.util.TimerTask;

import javax.imageio.ImageIO;
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
	private NWBrowser browser;
	Lobby lobby;
	public  Controller controller;
	private JPanel pWeiter;
	private Clip clip;
	private Container cp;
	public static boolean debug=true;
	
	
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
		super("Toast mit Katzengsicht - Game Of The Year Edition");
		
		java.net.URL url = this.getClass().getClassLoader().getResource("../res/nyan2.png");
				//ClassLoader.getSystemResource("res/nyan2.png");
//		Toolkit kit = Toolkit.getDefaultToolkit();
//		Image img = kit.createImage(url);
//		this.setIconImage(img);
	}
	
	/**
	 * Allelle Buttons fuer das Haupt-Menu
	 */
	private JButton bNGame;
	private JButton bIGame;
	private JButton bRandom;
	private JButton bRead;
	private JButton bCreate;
	private JButton bClose;


	private int xFrame=300;
	private int yFrame=500;
	
	
	public void initMain(){//TODO in Menu-Klasse auslagern
		//was passiert, wenn man das Fenster schliesst TODO Schliessen-Dialog
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// das ContetnPane auf das gezeichnet werden soll
		cp = this.getContentPane();
		cp.removeAll();
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
		
		//this.setResizable(false);
		
		this.setVisible(true);
	}
	
	/**
	 * Started das Spiel
	 */
	private AudioInputStream stream;
	
	private void startGame(){
		//TODO mit music Thread ersetzten, der nicht aus dem RAM abspielt
		if(stream == null)
		try {
		    File wavFile=new File("res/nerv.wav");
		    AudioFormat format;
		    DataLine.Info info;
		    

		    stream = AudioSystem.getAudioInputStream(wavFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.stop();
		    clip.open(stream);
		   // clip.start();   
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
		//cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		//cp.setLayout(new GridBagLayout());
		
		cp.removeAll();
		//Erzeugt neues Spiel und startet es
		myGame=new Game(this.cp, this, controller);
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
		if(ae.getSource()==this.bCreate){
			this.remove(pWeiter);
			this.initMain();
		}
		if(ae.getSource()==this.bCreate){
			MapEditor me = new MapEditor(cp);
			me.init();
		}
		if(ae.getSource()==this.bIGame){
			this.showNW();
		}
		//netGame=new NetworkGame(myself);
		//javax.swing.SwingUtilities.invokeLater(myGame);
		
	}
	
	private void showNW() {
		//Controller
		controller = new Controller();
		this.setFocusable(true);
		this.addKeyListener(controller);

		cp.removeAll();
		//Erzeugt neuen networkbrowser und zeigt ihn an
		browser=new NWBrowser(this.cp, this);	
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
		pWeiter.setBounds(0, 0, 300, 300);
		pWeiter.setLayout(new GridBagLayout());
		JLabel msg = new JLabel("",SwingConstants.CENTER);
		if(b){
			msg.setText("Herzlichen Glueckwunsch");
		} else{
			msg.setText("Loser!");
		}
		bCreate = new myJButton("Weiter zum Haupmenue");
		bCreate.setDefaultCapable(true);
		pWeiter.add(msg, new myGBC(0,0,1,1));
		pWeiter.add(bCreate,new myGBC(0,1,1,1));
		
		pWeiter.setVisible(true);
		
		
		cp.add(pWeiter);
		
		
		this.pack();
		this.setSize(xFrame, yFrame);
		
		
		
	}
	
}

