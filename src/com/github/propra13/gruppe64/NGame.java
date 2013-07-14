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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class NGame extends Game implements Runnable{
	

	
	/**
	 * Instanzvariablen
	 */

	public transient NPlayer nplayer;
	
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public NGame(Container cp, Main main, Controller controller, String nick) {
		super(cp,main,controller);
		nplayer = new NPlayer(nick,this);
		 													
	}	
	
	public void initLobby(String svrname, ArrayList<NPlayer> playerList){
		cp.removeAll();
		lobby=new Lobby(cp, main, playerList, serverOwner);
		lobby.chat.append("Wilkommen auf Server "+svrname);
		nplayer.lobby=lobby;
	}
	public void run(){
		nplayer.handleNW();				
	}
	
}