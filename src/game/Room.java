package game;

import java.io.File;
import java.io.FileNotFoundException;

public class Room{
	
	private int x, y, width, height;
	private String name;
	private int[][] data;
	
	public Room(int x, int y, int width, int height, String name, int levelNum) throws FileNotFoundException{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		
		try {
			this.data = util.DatManager.datToArray(new File("src\\data\\level" + levelNum + ".dat"));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Room File Does Not Exist");
		} 
	}
	
	public Room(int x, int y, int width, int height, int levelNum) throws FileNotFoundException {
		this(x, y, width, height, "", levelNum);
	}
	
	public String toString(){
		return width + " x " + height + " room at " + x + ", " + y;
	}
}
