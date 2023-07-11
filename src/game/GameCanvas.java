package game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.GraphicsConfiguration;

public class GameCanvas extends Canvas {
	
	private static final long serialVersionUID = -6910426644233784376L;

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
			//{"03","04","04","07",},
			//{"03","01","01","02",},
			//{"08","05","06","09",},
			{"01"}
	};
	
	  /* {"00","00","00","00","00","00","00","07","07","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","07","07","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","07","07","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","06","06","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","04","04","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","03","03","04","04","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","03","03","04","04","05","05","00","00","00","00","00",},
	   * {"00","00","00","00","00","02","02","01","01","01","01","00","00","00","00","00",},
	   * {"00","00","00","00","00","02","02","01","01","01","01","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
	   * {"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
	   * */
	
	private static Room currentRoom;
	private static ArrayList<Room> roomList;
	
	private static final Color[][] fullPalette = new Color[paletteHexReference.length][paletteHexReference[0].length];
	
	private Keys keys;
	private PPU ppu;
	private APU apu; 
	
	private BufferedImage buffer = null;
	private Graphics g;
	
	private int WIDTH;
	private int HEIGHT;
	
	private int SIM_WIDTH = 256;
	private int SIM_HEIGHT = 224;
	
	private int ratio;
	
	private BufferedImage timBones, timOverlay, ladBones, currentLevelImg, menu;

	private Player p;
	//private Creature zom;
	
	private int screenX, screenY = 0;
	private int roomWidth = 16,roomHeight = 11; 
	
	private int segX, segY;

	private int t = 0;

	private BufferedImage block, black, wall, deco, grass;
	
	private ArrayList<Creature> monsters;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Entity> fgEffects;
	private ArrayList<Entity> bgEffects;
	private ArrayList<Item> items;

	private BufferedImage timSheet;
	
	public GameCanvas(int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		
		keys = new Keys();
		ppu = new PPU(2);
		apu = new APU();
		
		monsters = new ArrayList<Creature>();
		projectiles = new ArrayList<Projectile>();
		fgEffects = new ArrayList<Entity>();
		bgEffects = new ArrayList<Entity>();
		items = new ArrayList<Item>();
		
		p = new Player(keys);
		
		monsters.add(p);
		fgEffects.add(new Hat(p, keys));
		fgEffects.get(0).setPalette(1);
		//monsters.add(new Creature(96, 96, 16, 32, 0));
		//monsters.get(1).setPalette(2);
		//monsters.get(1).setVelX(0.2);
		
		setSize(WIDTH, HEIGHT);
	    setVisible(true);
	    addKeyListener(keys);
	    
	    buffer = new BufferedImage(SIM_WIDTH,SIM_HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);
   		g = buffer.getGraphics();
   		requestFocusInWindow();
	    
   		
	    try{
	    	//timBones = ppu.to2BPP(ImageIO.read(new File("src\\assets\\timBonesGray.png")), 0);
	    	//timOverlay = ppu.to2BPP(ImageIO.read(new File("src\\assets\\timBonesOverlay.png")), 1);
	    	timSheet = ImageIO.read(new File("src\\assets\\timSheet.png"));
	    	ladBones = ImageIO.read(new File("src\\assets\\ladBones.png")); 
	    	black = ImageIO.read(new File("src\\assets\\black.png"));
	    	wall = ImageIO.read(new File("src\\assets\\backBricks.png"));
	    	block = ImageIO.read(new File("src\\assets\\brick16x16.png"));
	    	grass = ImageIO.read(new File("src\\assets\\rug.png"));
	    	deco = ImageIO.read(new File("src\\assets\\pillarFull.png"));
	    	
	    	//currentLevelImg = ImageIO.read(new File("src\\data\\grassTest.png"));
	    	
	    	//import room data from PNGs
	    	/*for(int i = 1; i < 10; i++){
	    		util.DatManager.imageToDat(ImageIO.read(new File("src\\data\\testLevel" + i +".png")), new File("src\\data\\level" + i +".dat"));
	    	}
	    	BufferedImage inp = ImageIO.read(new File("C:\\Users\\squir\\Desktop\\Beryl\\assets\\cornerBeach.png"));
	    	ImageIO.write(util.ImageTools.downscale(inp, 1200/16), "PNG", new File("C:\\Users\\squir\\Desktop\\Beryl\\assets\\cornerBeach2.png"));
	    	*/
	    	Color[] key = {Color.BLACK, new Color(63, 63, 63)};
	    	//util.DatManager.imageToDat(ImageIO.read(new File("src\\data\\grassTest.png")), key);
	    	roomList = Room.parseRooms(roomMap);
	    	
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
   		
   		roomWidth = currentRoom.getTilemap()[0].length;
   		roomHeight = currentRoom.getTilemap().length;
	}

	public GameCanvas(GraphicsConfiguration config) {
		super(config);
	}
	
	public void paint(Graphics window) {
	
	//Clear Menu Area
	g.clearRect(0, 0, 256, 224);
	
	//Draw Tiles
		int drawMaxX = screenX/-16 + 16;
		if(drawMaxX > roomWidth - 1){drawMaxX = roomWidth - 1;}
		
		int drawMaxY = screenY/-16 + 11;
		if(drawMaxY > roomHeight - 1){drawMaxY = roomHeight - 1;}
				
		for(int j = screenY/-16; j <= drawMaxY; j++) {
			for(int i =  screenX/-16; i <= drawMaxX; i++) {
				switch(currentRoom.getTilemap()[j][i]){ 
					case 1:
						collisionBox(i*16, 48 + j*16, 16, 16);
						g.drawImage(block, i*16 + screenX, 48 + j*16 + screenY,null);
					break;
					case 2:
						collisionBox(i*16, 48 + j*16 + 8, 16, 8);
						g.drawImage(grass,i*16 + screenX, 48 + j*16 + screenY, null);
					break;
					case 3:
						g.drawImage(wall,i*16 + screenX, 48 + j*16 + screenY, null);
					break;
					default:
						g.drawImage(black, i*16 + (int)(screenX), 48 + j*16 + screenY, null);
					break;
				}
			}
		}
		
	/*//Draw player
		g.drawImage(p.isSmall() ? ladBones : timBones, (int)p.getX() + screenX, (int)p.getY() + screenY, null);	
		if(!p.isSmall()){
			g.drawImage(timOverlay,(int)p.getX() + screenX, (int)p.getY() - 8 + screenY, null);
		}
		g.drawImage(timBones, (int)zom.getX() + screenX, (int)zom.getY() + screenY, null);
	*/	
		
		g.drawImage(deco, 48, 64,null);
		g.drawImage(deco, 160, 64,null);
		
		for(Creature m : monsters){
			//TODO: after collisions are moved to update(); in creature, update() call can be moved here
			//if(!m.equals(p)){
				g.drawImage(ppu.render(m), (int)m.getX() + screenX  + m.getxOffset(), (int)m.getY() + screenY + m.getyOffset(), null);
				for(Projectile p : m.getProjectiles()){
						p.update(currentRoom.getTilemap(), t);
						g.drawImage(ppu.render(p), (int)p.getX() + screenX  + p.getxOffset(), (int)p.getY() + screenY + p.getyOffset(), null);
						
						if(p.getX() + screenX + p.getxOffset() < -p.getW() || p.getX() + screenX + p.getxOffset() >256 || p.getY() + screenY + p.getyOffset() < 48 - p.getH() || p.getY() + screenY + p.getyOffset() >240 ){
							p.kill();
						}
				}
			//}
		}
						
		for(Entity e : fgEffects){
			e.update(currentRoom.getTilemap(), t);
			g.drawImage(ppu.render(e), (int)e.getX() + screenX + e.getxOffset(), (int)e.getY() + screenY + e.getyOffset(), null);
		}
		
	//Clear Menu Area
		g.setColor(Color.BLACK);
		g.fillRect(0,0,256,48);
		g.drawImage(ppu.renderMenu(p), 0, 0, null);	
	//Update player movement
		//p.update(currentRoom.getTilemap());
		//zom.update(currentRoom.getTilemap());
		for(Creature m : monsters){
			m.update(currentRoom.getTilemap(), t);
		}
		
	//Calculate Screen Scrolling
		screenX = -16 + 128 - (int)p.getX();
		
		if(screenX > 0 ){
			screenX = 0;
		}
		
		if(screenX < (roomWidth)*-16 + 256){
			screenX = (roomWidth)*-16 + 256;
		}
		
		screenY =  48 - 16 + 88 - (int)p.getY();
		
		if(screenY > 0){
			screenY = 0;
		}
		
		if(screenY < (roomHeight)*-16 + 176){
			screenY = (roomHeight)*-16 + 176;
		}
		
	//Determine which section of the current room the player is in
		segX = (int)p.getX()/256;
		segY = (int)p.getY()/256;
		
		window.setColor(Color.BLACK);
		window.fillRect(0,0,200,200);
		
		window.setColor(Color.WHITE);
		window.drawString(t%4 + "", 20,20);
		
		//Check Doors
		if(p.getX() >= roomWidth*16 || p.getX() <= -16 || p.getY() >= roomHeight*16 + 48|| p.getY() <= 48 ){
			switchRooms();
		}
		
	//Draw Simulation Onto Canvas
		if(WIDTH/SIM_WIDTH > HEIGHT/SIM_HEIGHT){
    		ratio = (int)Math.floor(HEIGHT/SIM_HEIGHT);
    	}else{
    		ratio = (int)Math.floor(WIDTH/SIM_WIDTH);
    	}
		
		window.drawImage(buffer,(WIDTH - SIM_WIDTH*ratio)/2, (HEIGHT - SIM_HEIGHT*ratio)/2, SIM_WIDTH*ratio, SIM_HEIGHT*ratio, null);
		
		window.setColor(Color.WHITE);
		window.drawRect((WIDTH - SIM_WIDTH*ratio)/2, (HEIGHT - SIM_HEIGHT*ratio)/2, SIM_WIDTH*ratio, SIM_HEIGHT*ratio);
		
		t++;
	}
	
	private void switchRooms(){
		
		int oldRoomWidth = roomWidth, oldRoomHeight = roomHeight;

   		if(p.getX() <= -16){
   			
   			int y = currentRoom.getY() + segY;
   			p.setY(p.getY() - 22*8*segY);
   			
   			currentRoom = roomList.get(Integer.parseInt(roomMap[currentRoom.getY() + segY][currentRoom.getX() -1], 16));
   			
   			roomWidth = currentRoom.getTilemap()[0].length;
   	   		roomHeight = currentRoom.getTilemap().length;
   			
   	   		p.setX(roomWidth*8 - 17);
   	   		p.setY(p.getY() + 22*8*(y - currentRoom.getY()));
   		
   	
   	   		
   		}else if(p.getX() >= oldRoomWidth*16){
   		   	
   			int y = currentRoom.getY() + segY;
   			p.setY(p.getY() - 22*8*segY);
   			
   			currentRoom = roomList.get(Integer.parseInt(roomMap[currentRoom.getY() + segY][currentRoom.getX() + currentRoom.getWidth()], 16));
   			
   			roomWidth = currentRoom.getTilemap()[0].length;
   	   		roomHeight = currentRoom.getTilemap().length;
   			
   			p.setX(0);
   	   		p.setY(p.getY() + 22*8*(y - currentRoom.getY()));

   	   		
   	   		
		} 
   		
   		else if(p.getY() <=  48 - 16){
   			
   			int x = currentRoom.getX() + segX;
   			p.setX(p.getX() - 32*8*segX);
   			
   			currentRoom = roomList.get(Integer.parseInt(roomMap[currentRoom.getY() - 1][currentRoom.getX() + segX], 16));
   			
   			roomWidth = currentRoom.getTilemap()[0].length;
   	   		roomHeight = currentRoom.getTilemap().length;
   			
			p.setY(roomHeight*8 + 48 - 17);
			p.setX(p.getX() + 32*8*(x - currentRoom.getX()));
			
   		}else if(p.getY() >= oldRoomHeight*16 + 48){
			
   			int x = currentRoom.getX() + segX;
   			p.setX(p.getX() - 32*8*segX);
   			
   			currentRoom = roomList.get(Integer.parseInt(roomMap[currentRoom.getY() + currentRoom.getHeight()][currentRoom.getX() + segX], 16));
   			
   			roomWidth = currentRoom.getTilemap()[0].length;
   	   		roomHeight = currentRoom.getTilemap().length;
   			
   			p.setY(65);
   			p.setX(p.getX() + 32*8*(x - currentRoom.getX()));
		}
	}
	
	//TODO: run collisions based on entity positions rather than for every block
	private void collisionBox(int x, int y,int w, int h){
		//p.collision(x,y,w,h);
		//zom.collision(x, y, w, h);
		for(Creature m : monsters){
			m.collision(x, y, w, h);
		}
	}

	public int getT() {
		return t;
	}
}