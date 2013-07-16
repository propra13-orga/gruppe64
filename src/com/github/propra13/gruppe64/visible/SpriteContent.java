package com.github.propra13.gruppe64.visible;

import java.awt.Graphics;

import javax.swing.JComponent;

public interface SpriteContent {
	/**
	 * Eine innere Klasse die die JComponent paintComponent methode für einen Sprite
	 * bereitstellt, ohne das diese davon erben muss!
	 * Das JComponent in sehr verschiedenen Versionen unterwegts ist und Netzwerk zur
	 * Qual macht. Eine effiziente Alternative zu JSON
	 */
	public class SpriteComponent extends JComponent{
		/**
		 *  Version 1.1
		 */
		private static final long serialVersionUID = -3363070901678961360L;
		/**
		 * Die Referenz auf das eigentliche Sprite 
		 */
		Sprite ref=null;
		/**
		 * Konstruktor fuer die innnere Klasse
		 * @param ref das Aeussere Sprite
		 */
		public SpriteComponent(Sprite ref){
			this.ref=ref;
		}
		/**
		 * Hier wird die Methode, die in von Sprite aufgerufen
		 */
		public void paintComponent(Graphics g){
			ref.paint(g);
		}
	}
	/**
	 * Die funktion in eigentlich gezeichnet wird
	 * @param g Graphics von JContent
	 */
	 void paint(Graphics g);
	 
	 /***
	  * Statische Klasse die Informationen des Sprites ausgibt
	  *
	  */
	/* public static class SpriteDebuger extends Sprite{
		 *//**
		  * 
		  *//*
		 public static void printInfo(){
			 System.out.println("name"+ self.name);
		 }
	 }*/
}
