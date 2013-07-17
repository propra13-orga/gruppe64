package com.github.propra13.gruppe64;

import java.util.ArrayList;

import com.github.propra13.gruppe64.visible.Map;
import com.github.propra13.gruppe64.visible.MapHandler;
import com.github.propra13.gruppe64.visible.Room;

public class NLevel extends Level {

	public NLevel() {
		// TODO Auto-generated constructor stub
	}

	public NLevel(NPlayer player, NGame game, int levelnr, ArrayList<Map> roomList) {
		this.game =game;
		this.player = player;
		this.levelNr=levelnr;
		
		this.roomList = roomList;
		//storeAllRooms(levelNr);
	}
	public void setMapHandler(MapHandler mapHandler){
		this.cp = mapHandler;
	}
	//public 
}
