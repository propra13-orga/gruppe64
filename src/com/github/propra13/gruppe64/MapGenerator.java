package com.github.propra13.gruppe64;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;





/**
 *  
 *	Diese Klasse liest aus einer Datei und gibt einen Char-Array zurrueck
 *	@author chrimus, nur zusammengefasst von ford-perfect
 */
public class MapGenerator {
	/** contains the path tokens, between integers**/
	private String[] pathToken;
	private CopyOnWriteArrayList<String[][]> lineDesc;
	private Iterator<String[][]> iLineDesc;
	public static String[] processAbleSprites = {
				"D:Door:D,[0-9]*,[0-9]*:D,[0-9]*,[a-z]*,[0-9]*",
				"$:Switch"
			};
	private String[][] pasToken;
	private String[][] tLineDesc;
	private ArrayList<Door> doorList;
	/** 
	 * @param gFilePath 
	 * the generic filepath
	 */
	public MapGenerator(String gFilePath) {
		pathToken = gFilePath.split("%i");
		pasToken = new String[processAbleSprites.length][];
		for(int i=0; i<processAbleSprites.length;i++){
			//zerteilt die processAbleSprite Def
			pasToken[i]=processAbleSprites[i].split("[:]");
		}
		//lineDesc 
		lineDesc = new CopyOnWriteArrayList<String[][]>();
		iLineDesc = lineDesc.iterator();
		doorList = new ArrayList<Door>();
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
	private static void main(String[] args) {
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
				current_line = current_line.split(";")[0]; if (current_line.length()<1) break; else
				linenumber++;
				
				int currentlength = current_line.length();
				if (linelength < currentlength) linelength = currentlength;
			}
			tLineDesc = new String[linenumber][];
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
				String[] sCurrentline = currentline.split(";");
				tLineDesc[j]=(sCurrentline);currentline=sCurrentline[0];
				
				for( int i=0; i< currentline.length(); i++){
					map[j][i]= currentline.charAt(i);
				}
			}
			lineDesc.add(tLineDesc);
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
	public  ArrayList<Room> generateRoomList(Level level){
		ArrayList<Room> roomList = new ArrayList<Room>();
		char[][] tmpArray;
		int lRoomNr=1, lvl = level.getLevelNr();
		tmpArray=this.readRoom(lvl, lRoomNr);
		while(tmpArray!=null){
			System.out.println(Room.charString(tmpArray));
			
			Room raum = new Room(level,tmpArray);
			roomList.add(raum);
			tmpArray=this.readRoom(lvl, ++lRoomNr);
		}
		//procesprocess 
		iLineDesc = lineDesc.iterator();
		for(Room map: roomList ){
			preProcessMap(map, roomList);
		}
		//linked Doors
		for(Door door: doorList){
			int lead2Num = door.getLeadToNr(); 
			for(Door pDoor: doorList){
				//TODO mutiple error
				if(pDoor.getDoorNr()==lead2Num){
					door.setTarget(pDoor);
					break;
				}
			}
			if(door.getTarget()==null){
				System.out.print("target Door not found");
			}
		}
		return roomList;
	}
	private <T extends Map> void preProcessMap(T map, ArrayList<T> mapList){
		// next LineDesc Set for the next map
		String[][] tLineDesc = iLineDesc.next();
		for(int y=0; y< tLineDesc.length; y++){
			
			//process every PAS in Line
			for(int pasTI=0;pasTI<pasToken.length;pasTI++){
				// set curent MapArrayName Token of current PAS
				String pasMapArrayNameT = pasToken[pasTI][0];
				int x=0;
				// da erstes Element nur MapArray enthaelt
				int	lastPosOfLineDesc=0;
				// find every PAS in current line of MapArray
				while(tLineDesc[y][0].indexOf(pasMapArrayNameT,x)!=-1){
					x = tLineDesc[y][0].indexOf(pasMapArrayNameT,x);
					System.out.println("found PAS in map " + mapList.indexOf(map) +" at "+x+","+y);
					
					boolean foundDesc=false;
					int protoDesc=2; //first protoDesc at 2 of pasToken
					while(!foundDesc){
						//first is only MapArray of Names, so move on
						lastPosOfLineDesc++;
						if(!(lastPosOfLineDesc<tLineDesc[y].length)){
							break;
						}
						//check if Description suites the current PAS
						String pasDesc = tLineDesc[y][lastPosOfLineDesc];
						for(protoDesc=2;protoDesc<pasToken[pasTI].length;protoDesc++){
							if(foundDesc= pasDesc.matches(pasToken[pasTI][protoDesc])){
								break;
							}
						}
						
					}
					if(foundDesc){
						String[] cDesc=tLineDesc[y][lastPosOfLineDesc].split(",");
						//TODO more genric or mutiple Versions
						if(protoDesc == 2){
							int tDoorNr=Integer.parseInt(cDesc[1]), tLeadsToNr=Integer.parseInt(cDesc[2]); //!!TODO tDoor differ to tDoor in Door.class
							Door tDoor=new Door(x*Map.spritewidth,y*Map.spriteheight,tDoorNr,tLeadsToNr);
							doorList.add(tDoor);
							map.add(tDoor);
						}else if(protoDesc==3){
							int tDoorNr=Integer.parseInt(cDesc[1]);
							Door tDoor=new Door(x*Map.spritewidth,y*Map.spriteheight,tDoorNr,cDesc[2],Integer.parseInt(cDesc[3]));
							doorList.add(tDoor);
							map.add(tDoor);
						}
					}else{
						System.out.println("for processable Sprite, no Description suitable");
					}
					x++; //search for PAS after last x-Pos in MapArray
				}
			}
			
			
		}
		/*String spriteClassName="Door";
		try {
			Class cClass = Class.forName(this.getClass().getPackage()+"."+spriteClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
