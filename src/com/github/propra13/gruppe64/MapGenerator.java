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
				"D:Door:D,[0-9]*,[0-9]*:D,[a-z]*,[0-9]*",
				"$:Switch"
			};
	private String[][] pasToken;
	private String[][] tLineDesc;
	private ArrayList<Door> doorList;
	private ArrayList<Room> roomList;
	/** 
	 * @param gFilePath 
	 * the generic filepath
	 */
	public MapGenerator(String gFilePath) {
		this();
		pathToken = gFilePath.split("%i");
	}
		
		
	public MapGenerator(){
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
	public MapGenerator(String gFilePath, ArrayList<Door> outsideDoors) {
		this(gFilePath);
		doorList.addAll(outsideDoors);
	}
	//Array
	private String pathString(int lvl, int rm){
		if (pathToken.length>2){
			return pathToken[0]+lvl+pathToken[1]+rm+pathToken[2];
		}
		return null;
	}


	/* 
	 * Ruft res/Karten/Level[lvl]_Raum[room].txt auf und bestimmt seine Groe��e
	 * 
	 * AUSGABE:
	 * - int[] {Zeilenanzahl des Files, max. Zeilenlaenge im File}
	 * 	ODER
	 * - null wenn res/Karten/Level[lvl]_Raum[room].txt nicht existiert
	 */
	public int[] getMapSize(String fPath){
		
		int linelength = 0;
		int linenumber = 0;
		String current_line="";
		
		FileReader f;
		//System.out.print(this.pathString(lvl,room));
		try {
			
			f = new FileReader(fPath);

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
	public char[][] readFile(int mapwidth, int mapheight, String fPath){
		
		char[][] map = new char[mapheight][mapwidth];
		
		FileReader f;
		String currentline;
		try {
			//"res/Karten/Level"+lvl+"_Raum"+room+".txt"
			f = new FileReader(fPath);
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
	public char[][] readArrayList(String fPath){
		
		int mapwidth, mapheight;
		int[] mapsize = getMapSize(fPath);
		
		if(mapsize==null){return null;}
		mapheight= mapsize[0];
		mapwidth = mapsize[1];
		
		char[][] map = readFile( mapwidth, mapheight, fPath);
		return map;
	}
	public  ArrayList<Room> generateRoomList(Level level){
		roomList = new ArrayList<Room>();
		char[][] tmpArray;
		int lRoomNr=1, lvl = level.getLevelNr();
		tmpArray=this.readArrayList(this.pathString(lvl, lRoomNr));
		while(tmpArray!=null){
			System.out.println(Room.charString(tmpArray));
			
			Room raum = new Room(level,tmpArray,lRoomNr);
			roomList.add(raum);
			tmpArray=this.readArrayList(this.pathString(lvl,++lRoomNr));
		}
		//procesprocess 
		iLineDesc = lineDesc.iterator();
		for(Room map: roomList ){
			preProcessMap(map);
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
	public Map generateMap(Class<? extends Map> mapClass,String mapFile){
		char[][] tArray=readArrayList(mapFile);Map map=null;
		if(mapClass.equals(Shop.class))
			map = new Shop(tArray);
		else
			map = new World(tArray);
		iLineDesc = lineDesc.iterator();
		this.preProcessMap(map); //needs no post process
		return map;
	}
	private <T extends Map> void preProcessMap(T map){
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
					if(map instanceof Room)System.out.println("found PAS in map " + ((Room)map).roomNr +" at "+x+","+y);
					
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
							Door tDoor=new Door(x*map.spritewidth,y*map.spriteheight,tDoorNr,tLeadsToNr);
							doorList.add(tDoor);
							map.add(tDoor);
						}else if(protoDesc==3){ //special Door
							//int tDoorNr=Integer.parseInt(cDesc[1]);
							Door tDoor=new Door(x*map.spritewidth,y*map.spriteheight,-1,cDesc[1],Integer.parseInt(cDesc[2]));
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
	public int  maxDoorNr(){
		int maxDoorNr=0;
		for(Door iDoor: doorList){
			if(iDoor.getDoorNr()>maxDoorNr)maxDoorNr=iDoor.getDoorNr();
		}
		return maxDoorNr;
	}
}
