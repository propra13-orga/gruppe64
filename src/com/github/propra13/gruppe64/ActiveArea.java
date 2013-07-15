package com.github.propra13.gruppe64;

import java.awt.Rectangle;

import com.github.propra13.gruppe64.visible.Movable;

public interface ActiveArea {
	public boolean onTouchAction();
	public boolean onActionAction();
	public void onTouch(Movable mv);
	public void onAction(Movable mv);
	public Rectangle getRectangle();
	public String getNick();
	void setNick(String string);
	
}
