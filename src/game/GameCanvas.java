package game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import javax.imageio.ImageIO;

import java.awt.GraphicsConfiguration;
import java.awt.Image;

public class GameCanvas extends Canvas {
	
	private static final String[][] paletteHexReference = {
			{"5F5F5F", "AFAFAF", "EFEFEF", "FFFFFF"},
			{"001F97", "0057F7", "279FFF", "AFCFFF"},
			{"0F00BF", "1F3FFF", "4387FF", "B7B7FF"},
			{"4300A7", "671FF7", "876FFF", "C7AFFF"},
			{"7F0077", "AF07CF", "C75FFF", "E7A7FF"},
			{"8F0027", "D70057", "FF579F", "F7B7E7"},
			{"8F0700", "DF1700", "FF6F4F", "FFC7CF"},
			{"772700", "BF4700", "FF8700", "FFCF97"},
			{"473F00", "876700", "CFA700", "F7E797"},
			{"004700", "178700", "6FC700", "D7F78F"},
			{"004F0F", "009717", "1FCF2F", "AFFF97"},
			{"00472F", "008757", "17CF97", "9FF7CF"},
			{"004767", "007FA7", "00BFE7", "87E7F7"},
			{"000000", "171717", "474747", "8F8F8F"},
		};
	
	private static final String[][] roomMap = {
			{"03","04","04","07",},
			{"03","01","01","02",},
			{"08","05","06","09",},
	};
	
	  /*{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","06","07","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"03","01","01","02","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
		{"00","04","05","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},*/
	
	private static Room currentRoom;
	private static ArrayList<Room> roomList;
	
	private static final Color[][] fullPalette = new Color[paletteHexReference.length][paletteHexReference[0].length];
	
	public static Keys keys = new Keys();
	
	private BufferedImage buffer = null;
	private Graphics g;
	
	private int WIDTH;
	private int HEIGHT;
	
	private int SIM_WIDTH = 256;
	private int SIM_HEIGHT = 240;
	
	private int ratio;
	
	private BufferedImage timBones, ladBones, wall, currentLevelImg, block;

	private Player p;
	private Entity zom;
	
	private int screenX, screenY = 0;
	private int roomWidth = 32,roomHeight = 22; 
	
	private int segX, segY;
	
	public GameCanvas(int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		
		p = new Player(keys, timBones);
		zom = new Entity(96, 96, 16, 24, timBones);
		zom.setVelX(0.2);
		
		setSize(WIDTH, HEIGHT);
	    setVisible(true);
	    addKeyListener(keys);
	    
	    buffer = new BufferedImage(SIM_WIDTH,SIM_HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);
   		g = buffer.getGraphics();
   		requestFocusInWindow();
	    
   		
	    try{
	    	timBones = ImageIO.read(new File("src\\assets\\timBonesHat.png"));
	    	ladBones = ImageIO.read(new File("src\\assets\\ladBones.png")); 
	    	wall = ImageIO.read(new File("src\\assets\\black.png"));
	    	block = ImageIO.read(new File("src\\assets\\graybrick8x8.png"));
	  
	    	
	    	//currentLevelImg = ImageIO.read(new File("src\\data\\testLevel1.png"));
	    	
	    	//import room data from PNGs
	    	/*for(int i = 1; i < 10; i++){
	    		util.DatManager.imageToDat(ImageIO.read(new File("src\\data\\testLevel" + i +".png")), new File("src\\data\\level" + i +".dat"));
	    	}*/
	    	BufferedImage inp = ImageIO.read(new File("C:\\Users\\squir\\Desktop\\Beryl\\assets\\cornerBeach.png"));
	    	ImageIO.write(util.ImageTools.downscale(inp, 1200/16), "PNG", new File("C:\\Users\\squir\\Desktop\\Beryl\\assets\\cornerBeach2.png"));
	    	
	    	roomList = parseRooms(roomMap);
	    	
	    	currentRoom = roomList.get(1);
	    	
	    }catch(IOException e){
	    	e.printStackTrace();
	    }
   		
   		//Convert NES hex values to RGB colors
   		for(int hue = 0; hue < paletteHexReference.length; hue++){
   			for(int lit = 0; lit < paletteHexReference[0].length; lit++){
   				fullPalette[hue][lit] = Color.decode("#" + paletteHexReference[hue][lit]);
   			}
   		}   		
   		
   		/*try {
   			//util.DatManager.imageToDat(currentLevelImg, new File("src\\data\\level1.dat"));
			//currentLevel = util.DatManager.datToArray(new File("src\\data\\level1.dat"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
   		
   		roomWidth = currentRoom.getData()[0].length;
   		roomHeight = currentRoom.getData().length;
	}

	public GameCanvas(GraphicsConfiguration config) {
		super(config);
	}
	
	public void paint(Graphics window) {
		
	//Draw Tiles
		int drawMaxX = screenX/-8 + 32;
			if(drawMaxX > currentRoom.getData()[0].length - 1){drawMaxX = currentRoom.getData()[0].length - 1;}
		
		int drawMaxY = screenY/-8 + 22;
			if(drawMaxY > currentRoom.getData().length - 1){drawMaxY = currentRoom.getData().length - 1;}
				
		for(int j = screenY/-8; j <= drawMaxY; j++) {
			for(int i =  screenX/-8; i <= drawMaxX; i++) {
				switch(currentRoom.getData()[j][i]){ 
					case 1:
						collisionBox(i*8, 64 + j*8, 8, 8, block);
					break;
					default:
						g.drawImage(wall, i*8 + screenX, 64 + j*8 + screenY, null);
					break;
				}
			}
		}
		
	//Draw player
		g.drawImage(p.isSmall() ? ladBones : timBones, (int)p.getX() + screenX, (int)p.getY() - 8 + screenY, null);	
		g.drawImage(timBones, (int)zom.getX() + screenX, (int)zom.getY()-8 + screenY, null);
		
	//Clear Menu Area
		g.setColor(Color.BLACK);
		g.fillRect(0,0,256,64);
	
	//Temp
		for(int i = 0; i < paletteHexReference.length; i++){
			for(int j = 0; j < paletteHexReference[i].length; j++){
				g.setColor(new Color(Integer.parseInt(paletteHexReference[i][j], 16)));
				g.fillRect(8+8*i, 8+8*j, 8, 8);
			}
		}
		
	//Calculate Screen Scrolling
		screenX = -8 + 128 - (int)p.getX();
		
		if(screenX > 0 ){
			screenX = 0;
		}
		
		if(screenX < (roomWidth)*-8 + 256){
			screenX = (roomWidth)*-8 + 256;
		}
		
		screenY =  64 - 8 + 88 - (int)p.getY();
		
		if(screenY > 0){
			screenY = 0;
		}
		
		if(screenY < (roomHeight)*-8 + 176){
			screenY = (roomHeight)*-8 + 176;
		}
		
	//Determine which section of the current room the player is in
		segX = (int)p.getX()/256;
		segY = (int)p.getY()/256;
		
		window.setColor(Color.BLACK);
		window.fillRect(0,0,200,200);
		
		window.setColor(Color.WHITE);
		window.drawString(segX +", " + segY, 20,20);
		
	//Draw Simulation Onto Canvas
		if(WIDTH/SIM_WIDTH > HEIGHT/SIM_HEIGHT){
    		ratio = (int)Math.floor(HEIGHT/SIM_HEIGHT);
    	}else{
    		ratio = (int)Math.floor(WIDTH/SIM_WIDTH);
    	}
		
		window.drawImage(buffer,(WIDTH - SIM_WIDTH*ratio)/2, (HEIGHT - SIM_HEIGHT*ratio)/2, SIM_WIDTH*ratio, SIM_HEIGHT*ratio, null);
		
		window.setColor(Color.WHITE);
		window.drawRect((WIDTH - SIM_WIDTH*ratio)/2, (HEIGHT - SIM_HEIGHT*ratio)/2, SIM_WIDTH*ratio, SIM_HEIGHT*ratio);
		
	//Update player movement
		p.update(currentRoom.getData());
		zom.update(currentRoom.getData());
	//Check Doors
		if(p.getX() >= roomWidth*8 || p.getX() <= -16 || p.getY() >= roomHeight*8 + 64|| p.getY() <= 64 - 16 ){
			switchRooms();
		}
		
	}
	
	
	/** Constructs rectangular rooms from a hexadecimal matrix
	 * 		@param roomMap - a 2D array of two-digit hexadecimal Strings
	 *  	@return an ArrayList of size 256 of rectangular Rooms
	 *  	@throws FileNotFoundException when the room data file associated with a room present in roomMap does not exist
	 *  	@throws NumberFormatException when roomMap contains Strings that are not valid two-digit hexadecimal numbers 
	 **/
	private static ArrayList<Room> parseRooms(String[][] roomMap){
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
	
	private void switchRooms(){
		
		int oldRoomWidth = roomWidth, oldRoomHeight = roomHeight;

   		if(p.getX() <= -16){
   			
   			int y = currentRoom.getY() + segY;
   			p.setY(p.getY() - 22*8*segY);
   			
   			currentRoom = roomList.get(Integer.parseInt(roomMap[currentRoom.getY() + segY][currentRoom.getX() -1], 16));
   			
   			roomWidth = currentRoom.getData()[0].length;
   	   		roomHeight = currentRoom.getData().length;
   			
   	   		p.setX(roomWidth*8 - 17);
   	   		p.setY(p.getY() + 22*8*(y - currentRoom.getY()));
   		
   	
   	   		
   		}else if(p.getX() >= oldRoomWidth*8){
   		   	
   			int y = currentRoom.getY() + segY;
   			p.setY(p.getY() - 22*8*segY);
   			
   			currentRoom = roomList.get(Integer.parseInt(roomMap[currentRoom.getY() + segY][currentRoom.getX() + currentRoom.getWidth()], 16));
   			
   			roomWidth = currentRoom.getData()[0].length;
   	   		roomHeight = currentRoom.getData().length;
   			
   			p.setX(0);
   	   		p.setY(p.getY() + 22*8*(y - currentRoom.getY()));

   	   		
   	   		
		} 
   		
   		else if(p.getY() <=  64 - 16){
   			
   			int x = currentRoom.getX() + segX;
   			p.setX(p.getX() - 32*8*segX);
   			
   			currentRoom = roomList.get(Integer.parseInt(roomMap[currentRoom.getY() - 1][currentRoom.getX() + segX], 16));
   			
   			roomWidth = currentRoom.getData()[0].length;
   	   		roomHeight = currentRoom.getData().length;
   			
			p.setY(roomHeight*8 + 64 - 17);
			p.setX(p.getX() + 32*8*(x - currentRoom.getX()));
			
   		}else if(p.getY() >= oldRoomHeight*8 + 64){
			
   			int x = currentRoom.getX() + segX;
   			p.setX(p.getX() - 32*8*segX);
   			
   			currentRoom = roomList.get(Integer.parseInt(roomMap[currentRoom.getY() + currentRoom.getHeight()][currentRoom.getX() + segX], 16));
   			
   			roomWidth = currentRoom.getData()[0].length;
   	   		roomHeight = currentRoom.getData().length;
   			
   			p.setY(65);
   			p.setX(p.getX() + 32*8*(x - currentRoom.getX()));
		}
	}
	
	//TODO: run collisions based on entity positions rather than for every block
	private void collisionBox(int x, int y,int w, int h, BufferedImage img){
		p.collision(x,y,w,h);
		zom.collision(x, y, w, h);
	    g.drawImage(img, x + screenX, y + screenY,w,h,null);
	}
}