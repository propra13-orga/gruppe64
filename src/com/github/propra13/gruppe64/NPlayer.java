package com.github.propra13.gruppe64;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * represents the sever instance of the player
 */
public class NPlayer extends Player {
	private boolean readyState=false;
	private transient Socket socket;
	private transient OutputStream outStream;
	private transient InputStream inStream;
	private transient ObjectOutputStream outOStream;
	private transient ObjectInputStream intOStream;
	private transient Socket client;
	
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

			socket = new Socket( svr, SGame.PORTNR );
			outStream = socket.getOutputStream();
			inStream = socket.getInputStream();
			outOStream = new ObjectOutputStream(outStream);
			intOStream = new ObjectInputStream(inStream);
			outOStream.writeObject(this);
	}
	public void setClient(Socket client){
		this.client=client;
	}
}
