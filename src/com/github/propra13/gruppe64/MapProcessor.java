package com.github.propra13.gruppe64;

import java.util.ArrayList;

import com.github.propra13.gruppe64.visible.*;

interface MapProcessor{
public class ServerMapProcessor extends Thread{
	private ArrayList<Map> mapArray;
	private ArrayList<NPlayer> playerList;
	/**
	 * 
	 * @param level
	 * @param mapArray
	 * @param playerList
	 */
	public ServerMapProcessor(Level level, ArrayList<Map> mapArray, ArrayList<NPlayer> playerList){
		this.mapArray=mapArray;
		this.playerList=playerList;
		this.mapArray=mapArray;
	}
	@Override
	public void run() {
		
		super.run();
	}
	
}
public class ClientMapProcessor extends Thread{

	public ClientMapProcessor(NGame game, Map map, NPlayer localPlayer) {
		//world version

			//TODO der Spieler in der Map soll durch den localen ersetzt werden
			game.getCP().add(map);
			map.startMotion();
	}
	
}
	public MapHandler getCP();
}