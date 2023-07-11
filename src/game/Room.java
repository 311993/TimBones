package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

/**A representation of a single continuous room or screen.
 * Stores room location and size, tilemap, and default entity data.**/
public class Room{
	
	private int x, y, width, height;
	private String name;
	private int[][] tilemap;
	private Entity[] entities;
	private boolean bossRoom;
	private boolean itemGet;
	
	public Room(int x, int y, int width, int height, String name, int levelNum) throws FileNotFoundException{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		
		try {
			this.tilemap = util.DatManager.datToArray(new File("src\\data\\level" + levelNum + ".dat"));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Room File Does Not Exist");
		} 
	}
	
	public Room(int x, int y, int width, int height, int levelNum) throws FileNotFoundException {
		this(x, y, width, height, " "+levelNum, levelNum);
	}
	
	/** Constructs rectangular rooms from a hexadecimal matrix
	 * 		@param roomMap - a 2D array of two-digit hexadecimal Strings
	 *  	@return an ArrayList of size 256 of rectangular Rooms
	 *  	@throws FileNotFoundException when the room data file associated with a room present in roomMap does not exist
	 *  	@throws NumberFormatException when roomMap contains Strings that are not valid two-digit hexadecimal numbers 
	 **/
	public static ArrayList<Room> parseRooms(String[][] roomMap){
		ArrayList<Room> output  = new ArrayList<Room>();
		
		while(output.size() < 255){ output.add(null); }
		
		for(int y = 0; y < roomMap.length; y++) {
			for(int x = 0; x < roomMap[y].length; x++) {
				
				String id = roomMap[y][x];
					
				if(id.length() <= 2 && !id.equals("00") && Objects.isNull( output.get(Integer.parseInt(id, 16)) ) ){
					
					int w = 1,h = 1,xStart = x, yStart = y;
					
					while(x + 1 < roomMap[y].length && roomMap[y][x+1].equals(id)){
						w++;
						x++;
					}
					
					while(y + 1 < roomMap.length && roomMap[y+1][x].equals(id)){
						h++;
						y++;
					}
				
					try {
						output.set(Integer.parseInt(id, 16), new Room(xStart, yStart, w, h, Integer.parseInt(id, 16)));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				
					y = yStart;
					
				
				}
			}
		}
		
		return output;
	};
	
	public String toString(){
		return "Room " + name + " :: "+ width + " x " + height + " room at (" + x + ", " + y + ")";
	}
	
	public int[][] getTilemap(){
		return tilemap;
	}
	
	public int getX(){
		return x;
	};
	
	public int getY(){
		return y;
	};
	
	public int getWidth(){
		return width;
	};
	
	public int getHeight(){
		return height;
	};
	
	public String getName(){
		return name;
	};
	
	
}
