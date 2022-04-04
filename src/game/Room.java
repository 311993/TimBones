package game;

public class Room{
	
	private int x, y, width, height;
	private String name;
	
	public Room(int x, int y, int width, int height, String name) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
	}
	
	public Room(int x, int y, int width, int height) {
		this(x, y, width, height, "");
	}
	
	
}
