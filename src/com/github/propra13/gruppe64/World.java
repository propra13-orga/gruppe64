package com.github.propra13.gruppe64;

public class World extends Map{
	private static char EXIT=1;

	public World(int spritewidth, int spriteheight) {
		super(spritewidth, spriteheight);
		// TODO Auto-generated constructor stub
	}

	public World(int spritewidth, int spriteheight, int level, Game game) {
		super(spritewidth, spriteheight, level, game);
		// TODO Auto-generated constructor stub
	}
	public int getMaxLevel(){
		return 5;
	}
}
