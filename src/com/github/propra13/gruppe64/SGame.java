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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class SGame extends Game implements Runnable{
	

	
	/**
	 * Instanzvariablen
	 */
	protected ArrayList<Player> playerList;
	public final static int PORTNR=1234;
	
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public SGame(Container cp, Main main) {
		super(cp,main,main.controller);
		addPl(super.getPlayer());
	}	
	
	public void addPl(Player pl){
		playerList.add(pl);
		
	}
	public void correctNicks(){
		
		for(Player pl:playerList){
			Integer i = new Integer(0);
			for(Player pls:playerList){
				if(!pl.equals(pls))
					if(pl.getNick().equals(pls.getNick())){
						for(Player pl2:playerList)
							if(!pl.equals(pl2) && pl2.getNick().equals(pls.getNick()+"("+i.toString()+")"))i++;
						pls.setNick(pls.getNick()+"("+(i++).toString()+")");
					}	
			}
		}
	}
	public void run(){
		
	}
	public void startServer(){
		ServerSocket serverSocket=null;
		try {
			serverSocket = new ServerSocket( PORTNR );

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (serverRunning)
		    {
		      Socket client = null;
	
		      try
		      {
		        client = serverSocket.accept();
		        handleConnection(client);
		      }
		      catch ( IOException e ) {
		        e.printStackTrace();
		      }catch ( NullPointerException e){
		    	  e.printStackTrace();
		      }
		      finally {
		        if ( client != null )
		          try { client.close(); } catch ( IOException e ) { }
		      }
		    }
		}

	private void handleConnection(Socket client) {
		
		
	}

	
}