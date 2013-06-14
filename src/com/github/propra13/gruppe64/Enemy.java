package com.github.propra13.gruppe64;

public class Enemy extends Moveable {

	public Enemy(int x, int y, int xDim, int yDim) {
		super(new int[]{x,y}, new int[]{xDim,yDim});
		// TODO Auto-generated constructor stub
	}

	public Enemy(int xDim, int yDim, char name) {
		super(new int[]{xDim,yDim}, name);
		// TODO Auto-generated constructor stub
	}

}
