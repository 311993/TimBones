package game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
			{"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
			{"00","06","07","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
			{"03","01","01","02","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
			{"00","04","05","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00",},
	};
	
	private static int[][] currentLevel;
	
	private static final Color[][] fullPalette = new Color[paletteHexReference.length][paletteHexReference[0].length];
	
	public static Keys keys = new Keys();
	
	private BufferedImage buffer = null;
	private Graphics g;
	
	private int WIDTH;
	private int HEIGHT;
	
	private int SIM_WIDTH = 256;
	private int SIM_HEIGHT = 240;
	
	private int ratio;
	
	private BufferedImage timBones,test, currentLevelImg, block;

	private Player p;
	
	private int screenX, screenY = 0;
	private int roomWidth = 32,roomHeight = 22; 
	
	private int segX, segY;
	
	public GameCanvas(int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		
		p = new Player(keys);
		
		setSize(WIDTH, HEIGHT);
	    setVisible(true);
	    addKeyListener(keys);
	    
	    buffer = new BufferedImage(SIM_WIDTH,SIM_HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);
   		g = buffer.getGraphics();
   		requestFocusInWindow();
	    
	    try{
	    	timBones = ImageIO.read(new File("src\\assets\\timBonesHat.png"));
	    	test = ImageIO.read(new File("src\\assets\\black.png"));
	    	//currentLevelImg = ImageIO.read(new File("src\\data\\testLevel3.png"));
	    	block = ImageIO.read(new File("src\\assets\\graybrick8x8.png"));
	    }catch(IOException e){
	    	e.printStackTrace();
	    }
   		
   		//Convert NES hex values to RGB colors
   		for(int hue = 0; hue < paletteHexReference.length; hue++){
   			for(int lit = 0; lit < paletteHexReference[0].length; lit++){
   				fullPalette[hue][lit] = Color.decode("#" + paletteHexReference[hue][lit]);
   			}
   		}   		
   		
   		try {
   			//util.DatManager.imageToDat(currentLevelImg, new File("src\\data\\level5.dat"));
			currentLevel = util.DatManager.datToArray(new File("src\\data\\level2.dat"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		
   		roomWidth = currentLevel[0].length;
   		roomHeight = currentLevel.length;
	}

	public GameCanvas(GraphicsConfiguration config) {
		super(config);
	}
	
	public void paint(Graphics window) {
		
		int drawMaxX = screenX/-8 + 32;
			if(drawMaxX > currentLevel[0].length - 1){drawMaxX = currentLevel[0].length - 1;}
		
		int drawMaxY = screenY/-8 + 22;
			if(drawMaxY > currentLevel.length - 1){drawMaxY = currentLevel.length - 1;}
				
		for(int j = screenY/-8; j <= drawMaxY; j++) {
			for(int i =  screenX/-8; i <= drawMaxX; i++) {
				switch(currentLevel[j][i]){ 
					case 1:
						collisionBox(i*8, 64 + j*8, 8, 8, block);
					break;
					default:
						g.drawImage(test, i*8 + screenX, 64 + j*8 + screenY, null);
					break;
				}
			}
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(0,56,256,8);
		
		g.drawImage(timBones, (int)p.x + screenX, (int)p.y - 8 + screenY, null);		
		
		if(WIDTH/SIM_WIDTH > HEIGHT/SIM_HEIGHT){
    		ratio = (int)Math.floor(HEIGHT/SIM_HEIGHT);
    	}else{
    		ratio = (int)Math.floor(WIDTH/SIM_WIDTH);
    	}	
		
		screenX = -8 + 128 - (int)p.x;
		
		if(screenX > 0 ){
			screenX = 0;
		}
		
		if(screenX < (roomWidth)*-8 + 256){
			screenX = (roomWidth)*-8 + 256;
		}
		
		screenY =  64 - 8 + 88 - (int)p.y;
		
		if(screenY > 0){
			screenY = 0;
		}
		
		if(screenY < (roomHeight)*-8 + 176){
			screenY = (roomHeight)*-8 + 176;
		}
		
		segX = (int)p.x/256;
		segY = (int)p.y/256;
		
		window.setColor(Color.BLACK);
		window.fillRect(0,0,200,200);
		
		window.setColor(Color.WHITE);
		window.drawString(segX +", " + segY, 20,20);
		
		window.drawImage(buffer,(WIDTH - SIM_WIDTH*ratio)/2, (HEIGHT - SIM_HEIGHT*ratio)/2, SIM_WIDTH*ratio, SIM_HEIGHT*ratio, null);
		
		window.setColor(Color.WHITE);
		window.drawRect((WIDTH - SIM_WIDTH*ratio)/2, (HEIGHT - SIM_HEIGHT*ratio)/2, SIM_WIDTH*ratio, SIM_HEIGHT*ratio);
		
		p.update();
	}
	
	private void collisionBox(int x, int y,int w, int h, BufferedImage img){
		if(p.x + p.w > x && p.x < x + w && p.y + p.h > y && p.y < y + h){
	        
	        // Friction
	        /*if(p.v_x < -0.05){
	            p.a_x = 0.1;
	        }else if(p.v_x > 0.05){
	            p.a_x = -0.1;
	        }else{
	            p.a_x = 0;
	            p.v_x = 0;
	        }*/
	        
	        if(p.previousX + p.w > x && p.previousX < x + w){
	            p.v_y = 0;
	            
	            if(p.previousY <= y){
	                p.y = y - p.h;
	                p.jumps = p.jumpsMax;
	                p.jumpPrevious = false;
	            }else{
	                p.y = y + h;
	            }
	        }
	        
	        if(p.previousY + p.h > y && p.previousY < (y + h)){
	            p.v_x = 0;
	            
	            if(p.previousX <= x){
	                p.x = x - p.w;
	            }else{
	                p.x = x + w;
	            }
	        }
	        
	    }
	    
	    g.drawImage(img, x + screenX, y + screenY,w,h,null);
	}
}