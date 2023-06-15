package game;

import java.awt.image.BufferedImage;

public class Player extends Entity{
	
    private double spd = 2;
  
    private boolean jumpKeyLast = false;
    
    private boolean small = false;
    private long morphTimestamp = System.currentTimeMillis();
    
    private Keys keys;
    
	public Player(Keys keys, BufferedImage sprite) {
		super(16, 96, 16, 24, sprite);
		this.keys = keys;
	}

    public void update(int[][] roomMap){
    	
    	//Smol Switching
    	if(keys.getValue(16) && System.currentTimeMillis() - morphTimestamp > 500){
    		
    		setSmallness(!isSmall());
    		setJumps(0);
    		morphTimestamp = System.currentTimeMillis();
    		
    		if(isSmall()){
    			setSize(8, 8);
    			setMaxVel(6);
    			setAccY(9.81/27);
    		}else{
    			setY(getY()-16);
    			setSize(16, 24);
    			setMaxVel(12);
    			setAccY(9.81/20);
    		}
    	}
    	
    	//move keys
        if(keys.getValue(37)){
            setVelX(-spd);
        }
        
        else if(keys.getValue(39)){
            setVelX(spd);
        }
        
        else{
            setVelX(0);
        }
        
        if(keys.getValue(38) && getJumps() > 0 && !jumpKeyLast){
            setVelY(-getJumpVel());
            useJump();
            setPrevJump(true);
        }
        
        if(keys.getValue(38) && isPrevJump()){
            if(getVelY() < 0){setVelY(getVelY() - 0.24);}
        }else if(isPrevJump()){
            setPrevJump(false);
        }
        
        super.update(roomMap);
        
        jumpKeyLast = keys.getValue(38);
        
    }

	public boolean isSmall() {
		return small;
	}

	public void setSmallness(boolean small) {
		this.small = small;
	};
}