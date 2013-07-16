package com.github.propra13.gruppe64;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Server extends NGame implements Runnable{
	

	
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
				if(!pl.clientAddress.equals(socketAddress))System.out.println("inconsitente IP");
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
	@SuppressWarnings("unused")
	public void startServer(){
		serverSocket=null;
		try {
			serverSocket = new ServerSocket( PORTNR );
			//serverSocket.setReuseAddress(false);
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
		        Thread aThread = new Thread(ch);
		        ch.setThread(aThread);
		        threads.add(aThread);
		        aThread.start();
		      }
		      catch ( IOException e ) {
		    	  if(e instanceof SocketException){
		    		  if(client!=null){
		    			  try {
		    				  	client.close();
		    			  } catch (IOException e1) {
		    				  e1.printStackTrace();
		    			  } finally{
		    				  client=null;
		    			  }
		    		  }
		    	  }
		    	  else{
		    		  e.printStackTrace();
		    	  }
		      }catch ( NullPointerException e){
		    	e.printStackTrace();
		      } 
		      
		    }
	}

	private void handleConnection(Socket client) throws IOException, ClassNotFoundException {
		SocketAddress socketaddr=client.getRemoteSocketAddress();
		System.out.println(client.toString());
		
		NPlayer serverPlayer=null;
		
		OutputStream outStream = null;
		InputStream inStream = null;
		ObjectOutputStream outOStream = null;
		ObjectInputStream inOStream = null;
		try {
			outStream = client.getOutputStream();
			inStream = client.getInputStream();
			outOStream = new ObjectOutputStream(outStream);
			inOStream = new ObjectInputStream(inStream);
			
			serverPlayer = (NPlayer) inOStream.readObject();
			addPl(serverPlayer,outOStream,inOStream,client.getRemoteSocketAddress());
			
			outOStream.writeObject(svrname);
			outOStream.writeObject(playerList);
			
			while(client.isConnected()){

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
						sendAll(Message.headers.chgready,o);					
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
						removePl(socketaddr);						
						client.close();
						break;
					default:
						break;
					}
					
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EOFException e){
			removePl(socketaddr);
			sendAll(Message.headers.clshutdown,new Object[]{socketaddr});
			client.close();
		} catch (IOException e) {
			if(e instanceof SocketException)
				
			System.out.println("Playerverbindung abgebrochen:"+serverPlayer.getNick());
			e.printStackTrace();
		}finally {
		     //serverSocket.close();
		     if(outStream!=null)   outStream.close();
		     if(inStream!=null)    inStream.close();
		     if( outOStream!=null) outOStream.close();
		     if(inOStream!=null)   inOStream.close();
	       
	        playerList.remove(serverPlayer);
	        
		}
		
	}
	private void forward(Message msgobj) {
		for(NPlayer pl: playerList){
			pl.sendMsg(msgobj);
		}
		
	}
	private void sendAll(Message.headers header, Object[] array){
		for(NPlayer pl: playerList){
			pl.sendMsg(header, array);
		}
	}
	public class ClientHandler implements Runnable{
		private Server sgame;
		private Socket socket;
		private Thread myThread;
		
		public ClientHandler(Server sgame,Socket socket){
			this.sgame=sgame;
			this.socket=socket;
		}
		public void setThread(Thread aThread) {
			myThread=aThread;
			
		}
		public void run(){
			try {
				handleConnection(socket);
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			threads.remove(myThread);
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
	/***
	 * hashmap test
	 * 
	 */
	@Override
	public void removePl(SocketAddress sa){
		playerList.remove(hashMap.get(sa));
	}
}