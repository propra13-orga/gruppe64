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
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
	protected 	ArrayList<NPlayer> playerList;
	private		ArrayList<Thread> threads;
	private 	boolean serverRunning;
	private 	String svrname;
	private 	Socket client;
	
	public final static int PORTNR=60001;
	
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public SGame(Container cp, Main main, String svrname) {
		super(cp,main,main.controller);

			this.svrname=svrname;
			threads=new ArrayList<Thread>();
			//addPl(super.getPlayer());
			serverRunning = true;
		
	}
	public SGame(Socket client, Container cp, Main main) {
		super(cp,main,main.controller);
		this.client=client;
		serverRunning=false;
	} 
	
	public SGame(Container cp, Main main, String text, ArrayList<NPlayer> playerList) {
		this(cp,main,text);
		this.playerList=playerList;
	}
	public void addPl(NPlayer pl){
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
		if(serverRunning){	startServer();	}
		
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
		    	handleConnection(client=serverSocket.accept());
		        
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

	private void handleConnection(Socket client) throws IOException {
		System.out.println(client.toString());
		//new SocketHandler(this,client);
		OutputStream outStream;
		try {
			outStream = client.getOutputStream();
			InputStream inStream = client.getInputStream();
			ObjectOutputStream outOStream = new ObjectOutputStream(outStream);
			ObjectInputStream inOStream = new ObjectInputStream(inStream);
			outOStream.writeObject(svrname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}