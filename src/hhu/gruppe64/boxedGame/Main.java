package hhu.gruppe64.boxedGame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		StdDraw.setXscale(0,100);
		StdDraw.setYscale(0,100);
		StdDraw.setPenColor(java.awt.Color.GREEN);
		StdDraw.filledRectangle(50,70,30,10);
		StdDraw.filledRectangle(50, 40, 30, 10);
		StdDraw.setPenColor(java.awt.Color.BLACK);
		StdDraw.text(50, 70, "Start");
		StdDraw.text(50, 40,"Beenden");
		StdDraw.show();
		// TODO add listener for gamestart Event
		/* Player player1 = new Player();
		 * Player[] playerList = new Player ...
		 * game = new Game(player1);
		 */
		
	}

}
