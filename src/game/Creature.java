package game;

import java.util.ArrayList;

public class Creature extends PhysicsEntity {
	
    private double jumpVel = 4.2;
    private int jumps = 1;
    private boolean prevJump = false;
    private int jumpsMax = 1;
    private int health, maxHealth;
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public Creature(double x, double y, int w, int h, int id) {
		super(x, y, w, h, id);
	}
	
	public void update(int[][] roomMap, int t){
		super.update(roomMap, t);
		
		 if(jumps == jumpsMax && getVelY() > getAccY()){
	            jumps--;
	     }
		 
		 for(int  i = 0; i < projectiles.size(); i++){
			 if(projectiles.get(i).isKillFlagged()){
				 projectiles.remove(i);
				 i--;
			 }
		 }
	}
	
	public void collision(int x2, int y2,int w2, int h2){
		boolean isColliding = isColliding(x2, y2, w2, h2);
		
		if(isColliding){
			if(getPrevX() + getW() > x2 && getPrevX() < x2 + w2){
	            setVelY(0);
	            
	            if(getPrevY() <= y2){
	                setY(y2 - getH());
	                jumps = jumpsMax;
	                prevJump = false;
	            }else{
	                setY(y2 + h2);
	            }
	        }
	        
	        if(getPrevY() + getH() > y2 && getPrevY() < (y2 + h2)){
	            setVelX(0);
	            
	            if(getPrevX() <= x2){
	                setX(x2 - getW());
	            }else{
	                setX(x2 + w2);
	            }
	        }
		}
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

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
}
