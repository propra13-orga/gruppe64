package com.github.propra13.gruppe64;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.github.propra13.gruppe64.StatBar.QuanItem;

/*
 * represents the sever instance of the player
 */
public class NPlayer extends Player {
	private boolean readyState=false;
	private transient Socket dataSocket;
	private transient OutputStream outStream;
	private transient InputStream inStream;
	private transient ObjectOutputStream outOStream;
	private transient ObjectInputStream intOStream;

	
	public boolean isReady(){
		return readyState;
	}
	
	public void setReadyState(boolean b) {
		readyState=b;
	}
	public NPlayer(String nick) {
		super(0, 0);
		setNick(nick);
		//TODO setup client
	}

	public void connect(InetAddress svr) throws UnknownHostException, IOException{
			dataSocket = new Socket( svr, SGame.PORTNR );
			outStream = dataSocket.getOutputStream();
			inStream = dataSocket.getInputStream();
			outOStream = new ObjectOutputStream(outStream);
			intOStream = new ObjectInputStream(inStream);
			System.out.println("Willkommen auf dem Server "+ intOStream.readChar() );
			outOStream.writeObject(this);
	} 
	public void setClient(Socket client){
		this.dataSocket=client;
	}
}
