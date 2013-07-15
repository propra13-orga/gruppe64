package com.github.propra13.gruppe64;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.propra13.gruppe64.visible.Map;
import com.github.propra13.gruppe64.visible.MapGenerator;
import com.github.propra13.gruppe64.visible.PlayerSprite;
import com.github.propra13.gruppe64.visible.Shop;
import com.github.propra13.gruppe64.visible.World;



public class Game implements Runnable{
	
	Game(){}
	
	/**
	 * Instanzvariablen
	 */
	protected Main main; 
	protected Map map;
	protected Container cp;
	
	protected Player player;
	protected StatBar statBar;
	protected Chat chatp;
	protected JTextField chatinput;
	protected World world;
	//active  Level
	protected Level aLevel;
	
	//private Timer caretaker;
	
	/** active Level **/
	protected int aLevelNr;
	/**highest acessible Level **/
	protected int levelNr;
	/**last Level**/
	protected int lastLevelNr;
	protected JPanel mapHandler;
	
	public boolean serverOwner=false;
	Lobby lobby;
	
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public Game(Container cp, Main main, Controller controller) {
		this.cp=cp;//new JPanel();this.cp.setLayout(null);cp.add(this.cp,BorderLayout.CENTER);
		this.main =main;
		
		
		cp.setBackground(Color.WHITE);
	
		//which level is reachable
		levelNr =1;
		aLevelNr=1;
	
		
		
	}	

	public void run(){
		MapGenerator mg=new MapGenerator();
		world = (World) mg.generateMap(World.class, "res/Karten/world.txt");
		lastLevelNr=world.getMaxLevel();
		world.drawMap();
		world.game=this;
		
		//player and his statBar
		player = new PlayerSprite(0,50);
		main.controller.setPlayer(player);
		initGamefield();
		
		
		//show initial world
		
		
		//start first level
		showWorld();
		
		
		

	}
	public void initGamefield(){
		
		cp.removeAll();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		
		chatp = new Chat(player);
		chatinput = new JTextField();
		chatinput.setMaximumSize(new Dimension(2000,20));//chatinput.setBounds(0, 600, 700, 20);
		chatinput.setToolTipText("chatinput");
		chatinput.addKeyListener(main.controller);
		
		statBar = new StatBar(player);
		player.addStatBar(statBar);
		player.setChatterBox(chatp,chatinput);
		
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
		cp.add(map.getJPanel(),BorderLayout.CENTER);
		//map.startMotion();
		map.getJPanel().repaint();
		
	}


	public Container getCP() {
		return mapHandler;
	}

	public Player getPlayer() {
		return player;
	}

	public void showMap(Map map) {
		this.map=map;
		mapHandler.add(map.getJPanel());
		map.add(player);map.startMotion();
		map.setLocation(mapHandler.getWidth()/2-player.getX(), mapHandler.getHeight()/2-player.getY());
		mapHandler.repaint();
	}

	public void showWorld() {
		player.setLevel(null);
		showMap(world);
	}
	
	
}

