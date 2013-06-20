package com.github.propra13.gruppe64;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



/**
 *  
 *	Diese Klasse liest aus einer Datei und gibt einen Char-Array zurrueck
 *	@author chrimus, nur zusammengefasst von ford-perfect
 */
public class MapGenerator {
	/** contains the path tokens, between integers**/
	String[] pathToken;
	/** 
	 * @param gFilePath 
	 * the generic filepath
	 */
	public MapGenerator(String gFilePath) {
		pathToken = gFilePath.split("%i");
		
	
	}
	//Array
	private String pathString(int i, int j){
		if (pathToken.length>2){
			return pathToken[0]+i+pathToken[1]+j+pathToken[2];
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MapGenerator mg = new MapGenerator("test%stest");
		System.out.print(mg.pathString(1,2));
	}
	

	/* 
	 * Ruft res/Karten/Level[lvl]_Raum[room].txt auf und bestimmt seine Groe��e
	 * 
	 * AUSGABE:
	 * - int[] {Zeilenanzahl des Files, max. Zeilenlaenge im File}
	 * 	ODER
	 * - null wenn res/Karten/Level[lvl]_Raum[room].txt nicht existiert
	 */
	public int[] getMapSize(int lvl, int room){
		
		int linelength = 0;
		int linenumber = 0;
		String current_line="";
		
		FileReader f;
		//System.out.print(this.pathString(lvl,room));
		try {
			
			f = new FileReader(this.pathString(lvl,room));

			BufferedReader buffer = new BufferedReader(f);
		
			while( (current_line= buffer.readLine())!= null){
				
				linenumber++;
				
				int currentlength = current_line.length();
				if (linelength < currentlength) linelength = currentlength;
			}
			buffer.close();
		}
		catch (IOException e) {
			return null;
		}
		
		int[] size= {0,0};
		size[0] = linenumber; 
		size[1] = linelength; 
		
		return size;
	}
	
	/*
	 * Ruft res/Karten/Level[lvl]_Raum[room].txt auf und kopiert Inhalt in char[][]
	 * 
	 * AUSGABE:
	 * - char[mapheight][mapheight]
	 * 	ODER
	 * - null, wenn res/Karten/Level[lvl]_Raum[room].txt nicht existiert
	 */
	public char[][] readFile(int mapwidth, int mapheight, int lvl, int room){
		
		char[][] map = new char[mapheight][mapwidth];
		
		FileReader f;
		String currentline;
		try {
			//"res/Karten/Level"+lvl+"_Raum"+room+".txt"
			f = new FileReader(this.pathString(lvl,room));
			BufferedReader buffer = new BufferedReader(f);
			
			for( int j=0; j<mapheight; j++){
				currentline = buffer.readLine();
			
				for( int i=0; i< currentline.length(); i++){
					map[j][i]= currentline.charAt(i);
				}
			}
			buffer.close();
		}
		catch (IOException e) {
			return null;
		}
		catch (NullPointerException e){
			/* 
			 * Durch diese catch-Abfrage wird der Fall abgefanden, dass im txt-File
			 * weniger Zeilen sind als angegeben. Somit terminiert die NullPointerException
			 * nicht den kompletten Prozess, sondern bricht nur das beschreiben des Arrays map
			 * ab und dieses ist ja dann schon fertig...
			 */
		}
		return map;
	}
	
	/*
	 * Kopiert res/Karten/Level[lvl]_Raum[room].txt in char[][]
	 * 
	 * AUSGABE:
	 * - char[][] = Map[y][x], wobei x,y die Koordinaten des
	 * 		JPanel-Koordinatensystems sind
	 * 	ODER
	 * - null, wenn res/Karten/Level[lvl]_Raum[room].txt nicht existiert
	 */
	public char[][] readRoom(int lvl, int room){
		
		int mapwidth, mapheight;
		int[] mapsize = getMapSize(lvl, room);
		
		if(mapsize==null){return null;}
		mapheight= mapsize[0];
		mapwidth = mapsize[1];
		
		char[][] map = readFile( mapwidth, mapheight, lvl, room);
		return map;
	}
}