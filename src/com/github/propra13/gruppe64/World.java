package com.github.propra13.gruppe64;

import java.awt.Container;

public class World extends Map{
	private boolean network=false;

	public World(int spritewidth, int spriteheight) {
		super(spritewidth, spriteheight);
		// TODO Auto-generated constructor stub
	}

	public World(int spritewidth, int spriteheight, int level, Game game) {
		//super(spritewidth, spriteheight, level, game);
		// TODO Auto-generated constructor stub
	}
	public World(int spritewidth, int spriteheight, int level, SGame game) {
		//super(spritewidth, spriteheight, level, game);
		// TODO Auto-generated constructor stub
	}
	public int getMaxLevel(){
		return 5;
	}
	public Container getCP(){
		return this.getParent();
	}
}
