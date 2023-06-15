package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {
	
	private double x;
    private double y;
    
    private double velX = 0;
    private double velY = 0;
    
    private double accX = 0;
    private double accY = 9.81/20;
    
    private int w;
    private int h;
    
    private double prevX;
    private double prevY;
    
    private double maxVel = 12;
    private double jumpVel = 4.2;
    
    private int jumps = 1;
    private boolean prevJump = false;
    private int jumpsMax = 1;
    
    //TODO: change to be a pointer to an array of buffered image arrays in gamecanvas e.g. [//tim bones sprites [], //mimic sprites [], ...]
    private BufferedImage sprite;
	
    public Entity(int x, int y, int w, int h, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.sprite = sprite;
		
		prevX = x;
		prevY = y;
	}
    
    public void update(int[][] roomMap){
    	
    	prevX = x;
        prevY = y;
        
        if(Math.abs(velY) > maxVel){
        	velY = Math.signum(velY)*maxVel;
        }
        
        x += velX;
        y += velY;
        
        velX += accX;
        velY += accY;
        
        if(jumps == jumpsMax && velY > accY){
            jumps--;
        }
    }
    
    public void collision(int x2, int y2,int w2, int h2){
    		if(x + w > x2 && x < x2 + w2 && y + h > y2 && y < y2 + h2){
    	        
    	        if(prevX + w > x2 && prevX < x2 + w2){
    	            velY = 0;
    	            
    	            if(prevY <= y2){
    	                y = y2 - h;
    	                jumps = jumpsMax;
    	                prevJump = false;
    	            }else{
    	                y = y2 + h2;
    	            }
    	        }
    	        
    	        if(prevY + h > y2 && prevY < (y2 + h2)){
    	            velX = 0;
    	            
    	            if(prevX <= x2){
    	                x = x2 - w;
    	            }else{
    	                x = x2 + w2;
    	            }
    	        }
    	        
    	    }
    }
    
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public double getAccX() {
		return accX;
	}

	public void setAccX(double accX) {
		this.accX = accX;
	}

	public double getAccY() {
		return accY;
	}

	public void setAccY(double accY) {
		this.accY = accY;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public double getMaxVel() {
		return maxVel;
	}

	public void setMaxVel(double maxVel) {
		this.maxVel = maxVel;
	}

	public double getJumpVel() {
		return jumpVel;
	}

	public void setJumpVel(double jumpVel) {
		this.jumpVel = jumpVel;
	}

	public int getJumps() {
		return jumps;
	}

	public void setJumps(int jumps) {
		this.jumps = jumps;
	}
	
	public void useJump(){
		this.jumps--;
	}

	public int getJumpsMax() {
		return jumpsMax;
	}

	public void setJumpsMax(int jumpsMax) {
		this.jumpsMax = jumpsMax;
	}

	public boolean isPrevJump() {
		return prevJump;
	}

	public void setPrevJump(boolean prevJump) {
		this.prevJump = prevJump;
	}

}
