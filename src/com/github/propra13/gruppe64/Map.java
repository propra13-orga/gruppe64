package com.github.propra13.gruppe64;

import java.awt.Graphics;

import javax.swing.JPanel;

//Dies k�nte auch Level heisen, aber Ihr m�sst das selbst entscheiden
public class Map extends JPanel{
	private static final long serialVersionUID = 1L;
	//Sollte Sinnvolle Argumente von Game bekommen
	
	static int mapwidth=10;
	static int mapheight=7;
	
	/*  Beispiel: x_max = mapwidth = 4 und y_max = mapheight = 3:
	 * 
	 *  [0][0]   [0][1]   [0][2]  [0][3]
	 *  [1][0]   [1][1]   [1][2]  [1][3]
	 *  [2][0]   [2][1]   [2][2]  [2][3]
	 */
	//char map[][]=new char [mapwidth][mapheight];
	
	static char map[][]= {	{'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
					{'E', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'A'},
					{'x', 'x', ' ', ' ', ' ', ' ', 'X', 'X', 'X', 'X'},
					{'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
					{'x', 'x', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};
	
	//gibt Name des Feldes bei (x,y) zurück
	public static String getField(int x, int y){
		
		
		if (x>=mapwidth || x<0 || y>=mapheight || y<0) return "Auserhalb Spielfeld";
		
		switch (map[y][x]){
			case ' ': return "frei";
			case 'x': 
			case 'X': return "versperrt";
			case 'e':
			case 'E': return "eingang";
			case 'a':
			case 'A': return "ausgang";
			case 'g':
			case 'G': return "gegener";
			default: return "undefiniertes Terrain";
		}
		
	}
	
	public static void main(String[] args) {
		
		
		String ausgabe = getField(9,3);
		
		System.out.println(ausgabe);
		
		
	}
}


