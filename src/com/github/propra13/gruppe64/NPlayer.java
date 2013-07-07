package com.github.propra13.gruppe64;
/*
 * represents the sever instance of the player
 */
public class NPlayer extends Player {
	private boolean readyState=false;
	public boolean isReady(){
		return readyState;
	}
	
	public void setReadyState(boolean b) {
		readyState=b;
	}
	public NPlayer(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

}
