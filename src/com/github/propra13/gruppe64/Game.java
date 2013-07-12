package com.github.propra13.gruppe64;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Game implements Runnable{
	

	
	/**
	 * Instanzvariablen
	 */
	private Main main; 
	private Map map;
	private Container cp;
	
	private Player player;
	private StatBar statBar;
	private Chat chatp;
	private JTextField chatinput;
	private World world;
	//active  Level
	private Level aLevel;
	
	//private Timer caretaker;
	
	/** active Level **/
	private int aLevelNr;
	/**highest acessible Level **/
	private int levelNr;
	/**last Level**/
	private int lastLevelNr;
	private JPanel mapHandler;
	
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public Game(Container cp, Main main, Controller controller) {
		this.cp=cp;//new JPanel();this.cp.setLayout(null);cp.add(this.cp,BorderLayout.CENTER);
		this.main =main;
		
		//player and his statBar
		player = new Player(0,50);
		
		
		
		
		
		cp.setBackground(Color.WHITE);
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));//cp.setLayout(new BorderLayout());
		//which level is reachable
		levelNr =1;
		aLevelNr=1;
	
		
		MapGenerator mg=new MapGenerator();
		world = (World) mg.generateMap(World.class, "res/Karten/world.txt");
		lastLevelNr=world.getMaxLevel();
		world.drawMap();
		world.game=this;
	}	

	public void run(){
		
		

		initGamefield();
		
		
		//show initial world
		
		
		//start first level
		showWorld();
		
		
		

	}
	public void initGamefield(){
		main.controller.setPlayer(player);
		
		chatp = new Chat(player);
		chatinput = new JTextField();
		chatinput.setMaximumSize(new Dimension(2000,20));//chatinput.setBounds(0, 600, 700, 20);
		chatinput.setToolTipText("chatinput");
		chatinput.addKeyListener(main.controller);
		
		statBar = new StatBar(player);
		player.addStatBar(statBar);
		player.addChatPane(chatp);
		player.addChatInput(chatinput);
		
		
		cp.add(statBar,cp);
		statBar.repaint();
		statBar.setLevel(aLevelNr);
		statBar.repaint(30);
		
		cp.add(mapHandler=new JPanel(),cp);
		mapHandler.setPreferredSize(new Dimension(1200,500));
		mapHandler.setMaximumSize(new Dimension(3000,500));mapHandler.setLayout(null);
		mapHandler.setBackground(Color.DARK_GRAY);mapHandler.setVisible(true);mapHandler.repaint(16);
		
		cp.add(chatp,cp);
		chatp.repaint();
		cp.add(chatinput,cp);
		chatinput.repaint();
		main.pack();
	}
	public void startLevel(int aLevelNr){
		//entferne von Game verwaltete map
		if(map!=null){
			map.stopMotion();
			map.remove(player);
			mapHandler.removeAll();
			map=null;
		}
		aLevel = new Level(this, aLevelNr);
		player.setLevel(aLevel);
 
		//aLevel.nextRoom();
		aLevel.initLevel();
		statBar.getStateFrom();
		aLevel.setOnDoor(aLevel.entrance);

	}
	
	/**
	 * beendet das Game
	 * TODO 
	 */
	public void return2Main() {

		cp.removeAll();
		cp.repaint();
		main.initMain();
		
	}


	public void showShop(){
		map = new Shop(50,50,this);
		map.add(player);
		
		statBar.getStateFrom();
		map.drawMap();
		cp.add(map,BorderLayout.CENTER);
		//map.startMotion();
		map.repaint();
		
	}


	public Container getCP() {
		return mapHandler;
	}

	public Player getPlayer() {
		return player;
	}

	public void showMap(Map map) {
		this.map=map;
		mapHandler.add(map);
		map.add(player);map.startMotion();
		mapHandler.repaint();
	}

	public void showWorld() {
		player.setLevel(null);
		showMap(world);
	}

	
}

