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
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class Server implements Runnable{
	

	
	/**
	 * Instanzvariablen
	 */
	protected 	ArrayList<NPlayer> playerList;
	private		ArrayList<Thread> threads;
	private 	boolean serverRunning;
	private 	String svrname;
	private 	Socket client;
	OutputStream outStream;
	InputStream inStream;
	ObjectOutputStream outOStream;
	ObjectInputStream inOStream;
	
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
			outStream = client.getOutputStream();
			inStream = client.getInputStream();
			outOStream = new ObjectOutputStream(outStream);
			inOStream = new ObjectInputStream(inStream);
			NPlayer npl = (NPlayer) inOStream.readObject();
			npl.clientAddress=client.getRemoteSocketAddress();
			playerList.add(npl);	
			outOStream.writeObject(svrname);
			outOStream.writeObject(playerList);
			while(client.isConnected()){
				try {
					Object robj= inOStream.readObject();
					Nmessage msgobj = null;
					if(robj instanceof Nmessage)msgobj=(Nmessage)robj;
					outOStream.reset();
					
					switch(msgobj.head){
					case chatmsg:	outOStream.writeObject(msgobj);
						break;
					case chgready:	ArrayList<Object> obj=new ArrayList<Object>();
						npl=(NPlayer)msgobj.object.get(0);
						playerList.get(playerNr(client)).setReadyState(!npl.isReady());
						obj.add(playerList);
						sendMsg(Nmessage.headers.chgready,obj);					
						break;
					case damage:
						break;
					case move:
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
	        if ( client != null )
		          try { client.close(); } catch ( IOException e ) { }
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
	public void sendMsg(Nmessage.headers header, ArrayList<Object> arrayList){
		try{
			outOStream.writeObject(new Nmessage(header,arrayList));outOStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
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