package com.github.propra13.gruppe64.visible;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import com.github.propra13.gruppe64.Player;
import com.github.propra13.gruppe64.visible.SpriteContent.SpriteComponent;

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
		if(mapPanel!=null)this.remove(mapPanel);
		this.map=map;
		mapPanel=map.getJPanel();
		add(mapPanel);
		mapPanel.repaint();
		this.repaint();
	}
	public void add(Map map, Player player){
		map.setFocusPlayer(player);
		((PlayerSprite)player).getSprite().addComponentListener(new HandlerListener());
		add(map);
	}
	class HandlerListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            if(e.getSource() instanceof MapHandler)MapHandler.this.updateMapPos();
            super.componentMoved(e);
        }
        @Override
        public void componentMoved(ComponentEvent e) {
        	if(e.getSource() instanceof SpriteComponent){ updateMapPos();System.out.println("move");}
        	super.componentMoved(e);
        }

	}
	public void updateMapPos() {
		if(map==null){System.out.println("keineMap");return;}
		if(mapPanel==null)return;
		Point playerPos=map.getPlayerPos();
		map.setLocation(getWidth()/2-playerPos.x, getHeight()/2-playerPos.y);
		
	}

	public void showOverlay() {
		// TODO Auto-generated method stub
		
	}
}
