package com.github.propra13.gruppe64;

import java.awt.Rectangle;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JTextField;

import com.github.propra13.gruppe64.visible.Item;
import com.github.propra13.gruppe64.visible.Movable;
import com.github.propra13.gruppe64.visible.PlayerSprite;
import com.github.propra13.gruppe64.visible.Movable.axis;
import com.github.propra13.gruppe64.visible.Movable.dir;



/*
 * represents the sever instance of the player
 */
public class NPlayer  extends PlayerSprite implements Player,ActiveArea{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean readyState;
	public transient Socket dataSocket;
	private transient OutputStream outStream;
	private transient InputStream inStream;
	public transient ObjectOutputStream outOStream;
	public transient ObjectInputStream inOStream;
	public transient Lobby lobby;
	public transient NGame nGame;
	public SocketAddress clientAddress;


	public transient boolean serverInstance=true;
	

	
	public boolean isReady(){
		return readyState;
	}
	
	public void setReadyState(boolean b) {
		readyState=b;
	}
	public NPlayer(String nick,NGame nGame) {
		super();
		this.nick=nick;
		
		this.nGame=nGame;
		readyState=false;
		serverInstance=false;
	}
	/**
	 * Verbindet sich mit dem Server, bekommt erste Playerlist
	 * @param svr die Serveraddresse
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect(InetAddress svr) throws UnknownHostException, IOException{
			dataSocket=new Socket();
			try{
				dataSocket.connect(new InetSocketAddress( svr, Server.PORTNR ),5000);
				
				outStream = dataSocket.getOutputStream();
				inStream = dataSocket.getInputStream();
				outOStream = new ObjectOutputStream(outStream);
				inOStream = new ObjectInputStream(inStream);
				String serverName=null;
				ArrayList<NPlayer> playerList=null;
				Message msg;
			
					try{
						outOStream.writeObject(this);outOStream.reset();
						serverName = (String) inOStream.readObject();
						playerList=(ArrayList<NPlayer>) inOStream.readObject();	
						clientAddress=(SocketAddress) inOStream.readObject();;
					}catch(IOException e){
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				nGame.playerList=playerList;
				setNick();
				nGame.initLobby(serverName);
				lobby.addlocalPl(this);
			} catch(IOException e){
				e.printStackTrace();
		}
	} 
	
	public void handleNW(){
		NPlayer npl;
		try {
			while(dataSocket.isConnected()&&!dataSocket.isClosed()){
				Object robj= inOStream.readObject();
				Message msgobj = null;
				if(robj instanceof Message)msgobj=(Message)robj;
				switch(msgobj.head){
				case chatmsg:	this.getChatterBox().writeChat((ActiveArea)msgobj.object.get(0), (String)msgobj.object.get(1));
					break;
				case chgready:	nGame.playerList=(ArrayList<NPlayer>) msgobj.array[0]; // FALSCH!
								lobby.updateTable();
					break;
				case start:		nGame.playerList=(ArrayList<NPlayer>) msgobj.array[0];
								nGame.initGamefield();
								nGame.showWorld();								
					break;
				case damage:
					break;
				case move:
					break;
				case svrshutdown:	dataSocket.close(); lobby.initmain();
					break;
				case clshutdown:	if(((SocketAddress)msgobj.array[0]).equals(clientAddress)){
										dataSocket.close();
										lobby.initmain();}
									else{nGame.removePl((SocketAddress)msgobj.array[0]);lobby.updateTable();}
					break;
				default:
					break;
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EOFException e){
			try {
				dataSocket.close();
				lobby.initmain();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {	outOStream.close();	} catch (IOException e) {System.out.println("kann oOS nicht closen obwohl noch nicht geclosed wurde.");}
			try {	outStream.close();	} catch (IOException e) {System.out.println("kann oS nicht closen obwohl noch nicht geclosed wurde.");}
			try {	inOStream.close();	} catch (IOException e) {System.out.println("kann iOS nicht closen obwohl noch nicht geclosed wurde.");}
			try {	inStream.close();	} catch (IOException e) {System.out.println("kann iS nicht closen obwohl noch nicht geclosed wurde.");}	
			if(!dataSocket.isClosed())try {	dataSocket.close();	} catch (IOException e) {System.out.println("kann socket nicht closen obwohl noch nicht geclosed wurde.");}	
		}
	}
	
	public void setClient(Socket client){
		this.dataSocket=client;
	}
	public void tell(ActiveArea npl,String msg){
		try {
			ArrayList<Object> obj=new ArrayList<Object>();
			obj.add(npl);
			obj.add(msg);
			outOStream.writeObject(new Message(Message.headers.chatmsg,obj));outOStream.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void chgready(){
	
			Object[] o={this};
			sendMsg(Message.headers.chgready,o);
			setReadyState(!isReady());
			if(isReady())	lobby.ready.setText("unready");
			else			lobby.ready.setText("ready"); 
		
	}

	public void sendMsg(Message.headers header, Object[] o){
		try{
			outOStream.writeObject(new Message(header,o));outOStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
	}

	public void sendMsg(Message msgObj) {
		try{
			outOStream.writeObject(msgObj);outOStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
	}

	public String getNick() {
		return nick;
	}


	public void setNick() {
		// TODO Auto-generated method stub
		for(NPlayer npl:nGame.playerList)
			if(npl.clientAddress.equals(this.clientAddress))	nick=npl.nick;

		super.setNick(nick);

	}

	@Override
	public Level getLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game getGame() {
		return nGame;
	}


	@Override
	public void setLevel(Level aLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getRectangle() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	/**
	 * 
	 */
	public void setLocation(int x, int y) {
		Object[] o={x,y};
		if(serverInstance)sendMsg(Message.headers.setLocation,o);
		sprite.setLocation(x, y);
	}

	

	@Override
	public int[] getVel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMot(dir i) {
		int[] vel=new int[2];
		switch (i){
			case up: vel[1]= 1; break;
			case right: vel[0]= 1; break;
			case down: vel[1]=-1; break;
			case left: vel[0]=-1; break;
		}
		sendMsg(new Message(Message.headers.move,new Object[]{clientAddress,vel}));
	}

	@Override
	public void unsetMot(axis y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attemptAttack() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void switchweapon() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switcharmor() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void performAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void use(char c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLvlUnlocked(int i) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<Item> getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGold() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Item> getSlots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLvlUnlocked() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNick(String string) {
		nick=string;
		
	}


	@Override
	public boolean onTouchAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onActionAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onTouch(Movable mv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAction(Movable mv) {
		// TODO Auto-generated method stub
		
	}
	public void callbackFkt(Message.Performer perf){
		perf.performOn(this);
	}
	public void disconnect(){
		if(nGame.serverOwner){	sendMsg(new Message(Message.headers.svrshutdown,new Object[]{clientAddress}));}
		else{					sendMsg(new Message(Message.headers.clshutdown,new Object[]{clientAddress}));}
	}

	public void startNWGame() {
		// TODO Auto-generated method stub
		sendMsg(new Message(Message.headers.start,new Object[]{null}));
	}
}
