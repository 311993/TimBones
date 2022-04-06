package game;

public class Room{
	
	private int x, y, width, height;
	private String name;
	private String data;
	
	public Room(int x, int y, int width, int height, String name, int levelNum) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.data = 
	}
	
	public Room(int x, int y, int width, int height, int levelNum) {
		this(x, y, width, height, "", levelNum);
	}
	
	public String toString(){
		return width + " x " + height + " room at " + x + ", " + y;
	}
}
