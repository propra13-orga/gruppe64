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
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JTextField;

import com.github.propra13.gruppe64.Movable.axis;
import com.github.propra13.gruppe64.Movable.dir;



/*
 * represents the sever instance of the player
 */
public class NPlayer  implements Player,ActiveArea{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean readyState;
	private transient Socket dataSocket;
	private transient OutputStream outStream;
	private transient InputStream inStream;
	private transient ObjectOutputStream outOStream;
	private transient ObjectInputStream intOStream;
	public transient Lobby lobby;
	public transient NGame nGame;
	public SocketAddress clientAddress;
	public String nick;
	private transient PlayerSprite playerSprite;
	private transient JTextField chatInput;
	//private boolean hasArmor=false;
	//private boolean hasArmorFire=false;
	
	private Chat chatPane;

	
	public boolean isReady(){
		return readyState;
	}
	
	public void setReadyState(boolean b) {
		readyState=b;
	}
	public NPlayer(String nick,NGame nGame) {
		playerSprite=new PlayerSprite(0, 0);
		this.nick=nick;
		
		this.nGame=nGame;readyState=false;
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
				Object robj= intOStream.readObject();
				Nmessage msgobj = null;
				if(robj instanceof Nmessage)msgobj=(Nmessage)robj;
				switch(msgobj.head){
				case chatmsg:	this.writeChat((ActiveArea)msgobj.object.get(0), (String)msgobj.object.get(1));
					break;
				case chgready:	nGame.playerList=(ArrayList<NPlayer>) msgobj.object.get(0); // FALSCH!
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
	public void tell(ActiveArea npl,String msg){
		try {
			ArrayList<Object> obj=new ArrayList<Object>();
			obj.add(this);
			obj.add(msg);
			outOStream.writeObject(new Nmessage(Nmessage.headers.chatmsg,obj));outOStream.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void chgready(){
	
			ArrayList<Object> obj=new ArrayList<Object>();
			obj.add(this);
			sendMsg(Nmessage.headers.chgready,obj);
			setReadyState(!isReady());
			if(isReady())	lobby.ready.setText("unready");
			else			lobby.ready.setText("ready"); 
		
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

	public String getNick() {
		return nick;
	}


	public void setNick() {
		// TODO Auto-generated method stub
		for(NPlayer npl:nGame.playerList)
			if(npl.clientAddress.equals(this.clientAddress))	nick=npl.nick;

		playerSprite.setNick(nick);

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
	public void addStatBar(StatBar statBar) {
		// TODO Auto-generated method stub
		
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
	public void writeChat(ActiveArea mv, String msg) {
		getChatPane().append(mv,msg);
		
	}

	@Override
	public void setLocation(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getVel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMot(dir up) {
		// TODO Auto-generated method stub
		
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
	public boolean chatIsFocusOwner() {
		
		return getChatInput().isFocusOwner();
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
	public void setChatInputText(String string) {
		getChatInput().setText(string);
		
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
	public void writeChat(String string) {
		getChatPane().append(string);
		
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
		// TODO Auto-generated method stub
		
	}

	public void addChatInput(JTextField chatinput) {
		// TODO Auto-generated method stub
		chatInput = chatinput;
	}

	public void addChatPane(Chat chatp) {
		this.chatPane = chatp;
		
	}

	public JTextField getChatInput() {
		return chatInput;
	}

	@Override
	public String getChatInputText() {
		
		return getChatInput().getText();
	}

	public Chat getChatPane(){
		return chatPane;
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


}
