package hhu.gruppe64.boxedGame;

public class Game {
	//sollte alles private sein!
	Player[] player;
	Map map;
	Game(Player[] player){
		for(int i=0; i<player.length; i++){
			this.player[i] = player[i];
			//gib jedem auserdem einen Kontroller
		}
		
	}
	
}
