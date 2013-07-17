package com.github.propra13.gruppe64;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.propra13.gruppe64.visible.*;



public class NGame extends Game implements Runnable,MapProcessor{
	

	
	/**
	 * Instanzvariablen
	 */
	
	public transient NPlayer player;
	public ArrayList<NPlayer> playerList;
	private ArrayList<Map> mapList;
	private Level level;
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public NGame(Container cp, Main main, Controller controller, String nick) {
		super(cp,main,controller);
		player = new NPlayer(nick,this);
		super.player=player;											
	}	
	public NGame(){	}
	public void initLobby(String svrname){
		cp.removeAll();
		lobby=new Lobby(cp, main, this, serverOwner);
		lobby.chat.append("Wilkommen auf Server "+svrname);
		player.lobby=lobby;
	}
	public void run(){
		player.handleNW();				
	}
	@Override
	public void showWorld(){
		Object[] o={"Serverbesizter waelt Level aus"};
		player.sendMsg(Message.headers.svrmsg, o);
		MapGenerator mg=new MapGenerator();
		world = (World) mg.generateMap(World.class, "res/Karten/world.txt");
		lastLevelNr=world.getMaxLevel();
		world.drawMap();
		world.game=this;
		super.showWorld();
	}
	@Override
	public void startLevel(int lvl){
		ArrayList<Room> roomList=null;
		Object[] o={roomList};
		player.sendMsg(Message.headers.svrmsg, o);
	}
	public void removePl(SocketAddress socketAddress){
		NPlayer removedPlayer= null;
		for(NPlayer iPl: playerList){
			if(iPl.clientAddress.equals(socketAddress))
				removedPlayer=iPl;
		}
		playerList.remove(removedPlayer);
	}
	@Override
	public void initGamefield() {
		super.initGamefield();
		main.controller.setPlayer(player);
	}
	public void startLevel(){
		//TODO
	}
	public void startMapProcessor(Object object) {
		map=(Map)object;
		//this ist wichtig, da Game.player
		new ClientMapProcessor(this,map,this.player);
		
	}
}