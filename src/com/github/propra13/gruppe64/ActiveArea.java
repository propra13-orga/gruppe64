package com.github.propra13.gruppe64;

import java.awt.Rectangle;

public interface ActiveArea {
	public boolean onTouchAction();
	public boolean onActionAction();
	public void onTouch(Moveable mv);
	public void onAction(Moveable mv);
	public Rectangle getRectangle();
}
