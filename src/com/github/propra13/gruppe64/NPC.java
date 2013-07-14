package com.github.propra13.gruppe64;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

public class NPC extends Movable implements ActiveArea{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7664870443549816543L;
	private boolean touchTell = true;
	private ArrayList<String> sentence;
	private Iterator<String> iSentence;
	
	

	public NPC(char name) {
		super(50, 50, name);
		sentence = new ArrayList<String>();
		switch(this.name){
		case 'O':
			nick="Meister des Zen";
			sentence.add("Hallo mein Freund!Die Welt wird von Katzen terrorisiert. Benutze space um auf die Katzen einzuschlagen und benutze c um deine Waffen zu wechseln. Wenn du gen��gend Mana hast kannst du mit h dein Leben regenerieren.Mit einem Doppelklick kannst du Lebens- und Manatr��nke verwenden");
			sentence.add("Du bist auf dich alleingestellt");
		
		case '$':
			nick="Verkaeufer";
			sentence.add("Hallo ich bin der Verkaeufer in diesem Laden, ein Zauberer hat mit dazu verdammt auf ewig in einem Wandelnden Laden zu arbeiten. Aber die Geschaefte laufen, ich kann nicht klagen. Schau dir meine Waren an, aber wenn du was anfasst musst du es kaufen.");
			sentence.add("Die letzte Truhe aus sapientpearwood habe an Twoflower verkauft. Ich habe aber viele andere nuetzliche Gegenstaende!!");
		}
	}

	@Override
	public void onTouch(Movable mv) {
		//if kind of player
		if(Player.class.isAssignableFrom(mv.getClass())){
			if(touchTell ){
				((Map)this.getParent()).tellAll(this,sentence.get(0));
				sentence.remove(0);
				touchTell=false;
			}
		}
		
	}

	@Override
	public void onAction(Movable mv) {
		if(Player.class.isAssignableFrom(mv.getClass())){
			//TODO iterator
			((Map)this.getParent()).tellAll(this,sentence.get(0));
		}
		
	}
	@Override
	public void paintComponent(Graphics g){
		switch(this.name){
		case 'O':
			
			Image img9 = Toolkit.getDefaultToolkit().getImage("res/oldman.png");
		    g.drawImage(img9, 0, 0, this);
		    g.finalize();	
		break;
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
}