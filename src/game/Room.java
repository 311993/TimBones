package game;

import java.io.File;

import util.RoomNotFoundException;

public class Room{
	
	private int x, y, width, height;
	private String name;
	private int[][] data;
	
	public Room(int x, int y, int width, int height, String name, int levelNum) throws RoomNotFoundException{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.data = util.DatManager.datToArray(new File("src\\data\\level" + levelNum + ".dat")); 
	}
	
	public Room(int x, int y, int width, int height, int levelNum) {
		this(x, y, width, height, "", levelNum);
	}
	
	public String toString(){
		return width + " x " + height + " room at " + x + ", " + y;
	}
}