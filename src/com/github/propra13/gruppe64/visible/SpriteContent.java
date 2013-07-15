package com.github.propra13.gruppe64.visible;

import java.awt.Graphics;

import javax.swing.JComponent;

public interface SpriteContent {
	public class SpriteComponent extends JComponent{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5946932655928763031L;
		Sprite ref=null;
		public SpriteComponent(Sprite ref){
			
		}
		public void paintComponent(Graphics g){
			ref.paint(g);
		}
	}
	public void paint(Graphics g);
}
