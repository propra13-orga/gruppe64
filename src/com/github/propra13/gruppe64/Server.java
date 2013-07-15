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
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Server implements Runnable{
	

	
	/**
	 * Instanzvariablen
	 */
	protected 	ArrayList<NPlayer> playerList;
	private		ArrayList<Thread> threads;
	HashMap<SocketAddress,NPlayer> hashMap;
	
	private 	boolean serverRunning;
	private 	String svrname;
	public ServerSocket serverSocket;

	
	public final static int PORTNR=60001;
	
	
	/**
	 * cp ist content-pane von unserem JFrame
	 */
	public Server(Container cp, Main main, String svrname) {

			this.svrname=svrname;
			threads=new ArrayList<Thread>();
			//addPl(super.getPlayer());
			serverRunning = true;
			playerList=new ArrayList<NPlayer>();
			hashMap= new HashMap<SocketAddress,NPlayer>(); 
	}
	
	
	
	public void addPl(NPlayer pl, ObjectOutputStream outOStream, ObjectInputStream inOStream, SocketAddress socketAddress){
		
		pl.clientAddress=socketAddress;
		pl.outOStream=outOStream;
		pl.inOStream=inOStream;

		if(!playerList.contains(pl)){
				playerList.add(pl);
				correctNicks();
				hashMap.put(socketAddress, pl);
		}

		
	}
	public void correctNicks(){
		
		for(NPlayer pl:playerList){
			Integer i = new Integer(0);
			for(NPlayer pls:playerList){
				if(!pl.equals(pls))
					if(pl.getNick().equals(pls.getNick())){
						for(NPlayer pl2:playerList)
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
		serverSocket=null;
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
		    	client=serverSocket.accept();
		        ClientHandler ch=new ClientHandler(this,client);
		        threads.add(new Thread(ch));
		        threads.get(threads.size()-1).start();
		      }
		      catch ( IOException e ) {
		        e.printStackTrace();
		      }catch ( NullPointerException e){
		    	e.printStackTrace();
		      }
		      
		    }
	}

	private void handleConnection(Socket client) throws IOException, ClassNotFoundException {
		System.out.println(client.toString());
		try {
			OutputStream outStream = client.getOutputStream();
			InputStream inStream = client.getInputStream();
			ObjectOutputStream outOStream = new ObjectOutputStream(outStream);
			ObjectInputStream inOStream = new ObjectInputStream(inStream);
			
			NPlayer serverPlayer = (NPlayer) inOStream.readObject();
			addPl(serverPlayer,outOStream,inOStream,client.getRemoteSocketAddress());
			
			outOStream.writeObject(svrname);
			outOStream.writeObject(playerList);
			
			while(client.isConnected()){
				try {
					Object robj= inOStream.readObject();
					Message msgobj= null;
					if(robj instanceof Message)msgobj=(Message)robj;
					outOStream.reset();
					
					switch(msgobj.head){
					case chatmsg:	forward(msgobj);
						break;
					case chgready:	
						serverPlayer.setReadyState(!serverPlayer.isReady());
						Object[] o={playerList};
						serverPlayer.sendMsg(Message.headers.chgready,o);					
						break;
					case damage:
						break;
					case move:
						break;
					case svrshutdown:
						forward(msgobj);
						client.close();
						break;
					case clshutdown:
						forward(msgobj);
						break;
					default:
						break;
					}
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	        if ( !serverSocket.isClosed() )
		          try { serverSocket.close(); } catch ( IOException e ) { }
		}
		
	}
	private void forward(Message msgobj) {
		for(NPlayer pl: playerList){
			pl.sendMsg(msgobj);
		}
		
	}
	public class ClientHandler implements Runnable{
		private Server sgame;
		private Socket socket;
		
		public ClientHandler(Server sgame,Socket socket){
			this.sgame=sgame;
			this.socket=socket;
		}
		public void run(){
			try {
				handleConnection(socket);
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
		
	}
	
	public int playerNr(Socket client){
		SocketAddress remoteAdress = client.getRemoteSocketAddress();
		int i=0;
		for(NPlayer iPlayer: playerList){
			if(iPlayer.clientAddress.equals(remoteAdress))return i;
			i++;
		}
		return -1;
	}
}