package com.github.propra13.gruppe64;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;



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
	public transient Lobby lobby;
	public transient NGame nGame;

	
	public boolean isReady(){
		return readyState;
	}
	
	public void setReadyState(boolean b) {
		readyState=b;
	}
	public NPlayer(String nick,NGame nGame) {
		super(0, 0);
		setNick(nick);
		this.nGame=nGame;
		//TODO setup client
	}

	public void connect(InetAddress svr) throws UnknownHostException, IOException{
			while(dataSocket==null){
				try{
					dataSocket = new Socket( svr, Server.PORTNR );
				} catch(IOException e){
					
					if(e instanceof ConnectException){
						
					} else{
						e.printStackTrace();
					}
				}
			}
			outStream = dataSocket.getOutputStream();
			inStream = dataSocket.getInputStream();
			outOStream = new ObjectOutputStream(outStream);
			intOStream = new ObjectInputStream(inStream);
			String serverName=null;
			ArrayList<NPlayer> nplayers=null;
			Nmessage msg;
		
				try{
					outOStream.writeObject(this);
					serverName = (String) intOStream.readObject();
					nplayers=(ArrayList<NPlayer>) intOStream.readObject();	// FALSCH?
				}catch(IOException e){
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			nGame.playerList=nplayers;
			nGame.initLobby(serverName);
			lobby.addlocalPl(this);
	} 
	
	public void handleNW(){
		NPlayer npl;
		try {
			while(dataSocket.isConnected()){
				Object msgobj=intOStream.readObject();
				switch(((Nmessage)msgobj).head){
				case chatmsg:	this.getChatPane().append((Moveable)((Nmessage)msgobj).object.get(0), (String)((Nmessage)msgobj).object.get(1));
					break;
				case chgready:	nGame.playerList=(ArrayList<NPlayer>) ((Nmessage)msgobj).object.get(0); // FALSCH!
								lobby.updateTable();
					break;
				case damage:
					break;
				case move:
					break;
				default:
					break;
				}
			}
			dataSocket.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setClient(Socket client){
		this.dataSocket=client;
	}
	public void tell(NPlayer npl,String msg){
		try {
			ArrayList<Object> obj=new ArrayList<Object>();
			obj.add(npl);
			obj.add(msg);
			outOStream.writeObject(new Nmessage(Nmessage.headers.chatmsg,obj));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void chgready(){
		try {
			ArrayList<Object> obj=new ArrayList<Object>();
			obj.add(this);
			outOStream.writeObject(new Nmessage(Nmessage.headers.chgready,obj));
			setReadyState(!isReady());
			if(isReady())	lobby.ready.setText("unready");
			else			lobby.ready.setText("ready"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
