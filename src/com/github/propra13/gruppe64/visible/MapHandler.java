package com.github.propra13.gruppe64.visible;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

public class MapHandler extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6394094756079999905L;
	Map map= null;
	JPanel mapPanel =null;
	public MapHandler() {
		this.addComponentListener(new HandlerListener());
		
	}

	public void add(Map map){
		//map=
	}
	
	class HandlerListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            MapHandler.this.updateMapPos();
        }
}

	public void updateMapPos() {
		if(map==null)return;
		if(mapPanel==null)return;
		Point playerPos=map.getPlayerPos();
		
		
	}
}
