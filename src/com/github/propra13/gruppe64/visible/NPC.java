package com.github.propra13.gruppe64.visible;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;

import com.github.propra13.gruppe64.ActiveArea;
import com.github.propra13.gruppe64.Player;

public class NPC extends Movable implements ActiveArea{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7664870443549816543L;
	private boolean touchTell = true;
	private ArrayList<String> sentence;
	private Iterator<String> iSentence;
	private String nick;
	private transient JComponent sprite;
	
	/**
	 * Initalisiert Sprite (50x50) und Textausgabe
	 * @param name Meister des Zen oder Verkaeufer
	 */
	public NPC(char name) {
		super(50, 50);
		this.name=name;
		sentence = new ArrayList<String>();
		switch(this.name){
		case 'O':
			setNick("Meister des Zen");
			sentence.add("Hallo mein Freund!Die Welt wird von Katzen terrorisiert. Benutze space um auf die Katzen einzuschlagen und benutze c um deine Waffen zu wechseln. Wenn du gen������gend Mana hast kannst du mit h dein Leben regenerieren.Mit einem Doppelklick kannst du Lebens- und Manatr������nke verwenden");
			sentence.add("Du bist auf dich alleingestellt");
		
		case '$':
			nick="Verkaeufer";
			sentence.add("Hallo ich bin der Verkaeufer in diesem Laden, ein Zauberer hat mit dazu verdammt auf ewig in einem Wandelnden Laden zu arbeiten. Aber die Geschaefte laufen, ich kann nicht klagen. Schau dir meine Waren an, aber wenn du was anfasst musst du es kaufen.");
			sentence.add("Die letzte Truhe aus sapientpearwood habe an Twoflower verkauft. Ich habe aber viele andere nuetzliche Gegenstaende!!");
		}
	}
	
	public NPC(){
		sprite = new SpriteComponent(this);}
			
			@Override
			public void paint(Graphics g){
				switch(NPC.this.name){
				case 'O':
					
					Image img9 = Toolkit.getDefaultToolkit().getImage("res/oldman.png");
				    g.drawImage(img9, 0, 0, sprite);
				    g.finalize();	
				break;
			}
			}
		
	
	/**
	 * Befindet sich Charakter auf diesen NPC
	 */
	@Override
	public void onTouch(Movable mv) {
		//if kind of player
		if(Player.class.isAssignableFrom(mv.getClass())){
			if(touchTell ){
				map.tellAll(this,sentence.get(0));
				sentence.remove(0);
				touchTell=false;
			}
		}
		
	}

	@Override
	public void onAction(Movable mv) {
		if(Player.class.isAssignableFrom(mv.getClass())){
			//TODO iterator
			map.tellAll(this,sentence.get(0));
		}
		
	}
	


	@Override
	public String getNick() {
		// TODO Auto-generated method stub
		return nick;
	}

	@Override
	public boolean onTouchAction() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onActionAction() {
		// TODO Auto-generated method stub
		return true;
	}
	protected void die(){
		map.remove(this);
	}

	@Override
	public void updateMot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNick(String string) {
		nick=string;
		
	}
}