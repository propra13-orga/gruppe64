package com.github.propra13.gruppe64;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

public class NPC extends Moveable implements ActiveArea{

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
			sentence.add("Hallo mein Freund!Die Welt wird von Katzen terrorisiert. Benutze space um auf die Katzen einzuschlagen und benutze c um deine Waffen zu wechseln. Wenn du genügend Mana hast kannst du mit h dein Leben regenerieren.Mit einem Doppelklick kannst du Lebens- und Manatränke verwenden");
			sentence.add("Du bist auf dich alleingestellt");
		}
	}

	@Override
	public void onTouch(Moveable mv) {
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
	public void onAction(Moveable mv) {
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
}