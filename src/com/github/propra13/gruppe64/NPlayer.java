package com.github.propra13.gruppe64;

import java.io.EOFException;
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
			String serverName=null;
			Nmessage msg;
		
				try{
					serverName = (String) intOStream.readObject();
				}catch(IOException e){
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			System.out.println("Willkommen auf dem Server "+  serverName);
			//outOStream.writeObject(this);
			dataSocket.close();
	} 
	public void setClient(Socket client){
		this.dataSocket=client;
	}
}
