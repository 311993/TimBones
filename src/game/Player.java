package game;

public class Player extends Creature{
	
    private double spd = 2; //1->2->3->4
  
    private boolean jumpKeyLast = false;
    
    private boolean small = false;
    private long morphTimestamp = System.currentTimeMillis();
    
    private int maxMP, MP;
    
    private Keys keys;
    
    private boolean facing = true;
    
	public Player(Keys keys) {
		super(32, 96, 16, 24, 0);
		this.keys = keys;
		this.setMaxHealth(15);
		this.setHealth(15);
		this.setMaxMP(15);
		this.setMP(15);
		this.setyOffset(-2);
		
		byte[] sprites = {
			00,01,
			16,17,
			32,33,
		};
		
		this.setSpriteIDs(sprites);
	}

    public void update(int[][] roomMap, int t){
    	
    	//Smol Switching
    	if(keys.getValue(16) && System.currentTimeMillis() - morphTimestamp > 500){
    		
    		setSmallness(!isSmall());
    		setJumps(0);
    		morphTimestamp = System.currentTimeMillis();
    		
    		if(isSmall()){
    			setSize(8, 16);
    			setMaxVel(6);
    			setAccY(9.81/27);
    			setyOffset(0);
    			
    			if(!facing){
    				byte[] transforms = {1,1};
                	this.setTransforms(transforms);
    			}
    			
    			this.setSpriteID(0, (byte)72);
            	this.setSpriteID(1, (byte)88);
    		}else{
    			setY(getY()-16);
    			setSize(16, 24);
    			setMaxVel(12);
    			setAccY(9.81/20);
    			setyOffset(-2);
    			
    			if(facing){
    				this.setSpriteID(0, (byte)0);	this.setSpriteID(1, (byte)1);
        			this.setSpriteID(2, (byte)16);	this.setSpriteID(3, (byte)17);
        			this.setSpriteID(4, (byte)32);	this.setSpriteID(5, (byte)33);
    			}else{
    				byte[] transforms = {1,1,1,1,1,1,};
   		        	this.setTransforms(transforms);
    				this.setSpriteID(0, (byte)1);	this.setSpriteID(1, (byte)0);
            		this.setSpriteID(2, (byte)17);	this.setSpriteID(3, (byte)16);
            		this.setSpriteID(4, (byte)33);	this.setSpriteID(5, (byte)32);
    			}
    		}
    	}
    	
    	//move keys
        if(keys.getValue(37)){
            setVelX(-spd);
            
            if(isSmall()){
               	byte[] transforms = {1,1};
            	this.setTransforms(transforms);
            	
            }else{
            int frame = (t/4)%4;
            
            switch(frame){
            	case 0:
            		this.setSpriteID(0, (byte)1);	this.setSpriteID(1, (byte)0);
            		this.setSpriteID(2, (byte)17);	this.setSpriteID(3, (byte)16);
            		this.setSpriteID(4, (byte)33);	this.setSpriteID(5, (byte)32);
            	break;
            	case 1:
            		this.setSpriteID(0, (byte)3);	this.setSpriteID(1, (byte)2);
            		this.setSpriteID(2, (byte)19);	this.setSpriteID(3, (byte)18);
            		this.setSpriteID(4, (byte)33);	this.setSpriteID(5, (byte)32);
                break;
            	case 2:
            		this.setSpriteID(0, (byte)3);	this.setSpriteID(1, (byte)2);
            		this.setSpriteID(2, (byte)19);	this.setSpriteID(3, (byte)18);
            		this.setSpriteID(4, (byte)35);	this.setSpriteID(5, (byte)34);
                break;
            	case 3:
            		this.setSpriteID(0, (byte)1);	this.setSpriteID(1, (byte)0);
            		this.setSpriteID(2, (byte)17);	this.setSpriteID(3, (byte)16);
            		this.setSpriteID(4, (byte)35);	this.setSpriteID(5, (byte)34);
                break;
            }
            
            byte[] transforms = {1,1,1,1,1,1,};
        	this.setTransforms(transforms);
        }
        	facing = false;
            
        }
        
        else if(keys.getValue(39)){
            setVelX(spd);
            
            if(isSmall()){
            	byte[] transforms = {0,0};
            	this.setTransforms(transforms);
            	
            }else{
            	int frame = (t/4)%4;
            
            	switch(frame){
            		case 0:
            			this.setSpriteID(0, (byte)0);	this.setSpriteID(1, (byte)1);
            			this.setSpriteID(2, (byte)16);	this.setSpriteID(3, (byte)17);
            			this.setSpriteID(4, (byte)32);	this.setSpriteID(5, (byte)33);
            		break;
            		case 1:
            			this.setSpriteID(0, (byte)2);	this.setSpriteID(1, (byte)3);
            			this.setSpriteID(2, (byte)18);	this.setSpriteID(3, (byte)19);
            			this.setSpriteID(4, (byte)32);	this.setSpriteID(5, (byte)33);
            		break;
            		case 2:
            			this.setSpriteID(0, (byte)2);	this.setSpriteID(1, (byte)3);
            			this.setSpriteID(2, (byte)18);	this.setSpriteID(3, (byte)19);
            			this.setSpriteID(4, (byte)34);	this.setSpriteID(5, (byte)35);
            		break;
            		case 3:
            			this.setSpriteID(0, (byte)0);	this.setSpriteID(1, (byte)1);
            			this.setSpriteID(2, (byte)16);	this.setSpriteID(3, (byte)17);
            			this.setSpriteID(4, (byte)34);	this.setSpriteID(5, (byte)35);
            		break;
            	}
            	
            	byte[] transforms = {0,0,0,0,0,0,};
            	this.setTransforms(transforms);
            }
            	facing = true;
            
            
        }
        
        else{
            setVelX(0);
        }
        
        if(keys.getValue(88) && getJumps() > 0 && !jumpKeyLast){
            setVelY(-getJumpVel());
            useJump();
            setPrevJump(true);
            this.setMP(getMP()-1);
        }
        
        if(keys.getValue(88) && isPrevJump()){
            if(getVelY() < 0){setVelY(getVelY() - 0.24);}
        }else if(isPrevJump()){
            setPrevJump(false);
        }
        
        if(keys.getValue(90)){
        	if(getProjectiles().size() == 0){
        		RotatingProjectile p = new RotatingProjectile(getX(), getY(), 16, 16, facing, 0);
        		p.setPalette(0);
        		byte[] sprites = {(byte)7, (byte)6, (byte)6, (byte)7,};
        		p.setSpriteIDs(sprites);
        		p.setDiagSprites(sprites);
        		byte[] sprites2 = {(byte)8, (byte)8, 71, 71};
        		p.setStraightSprites(sprites2);
        		byte[] transforms = {3,0,3,0};
        		if(facing){p.setVelX(4);}else{p.setVelX(-4);}
        		p.setTransforms(transforms);
        		p.setDiagTransforms(transforms);
        		byte[] transforms2 = {6,4,0,0};
        		p.setStraightTransforms(transforms2);
        		getProjectiles().add(p);
        		
        		RotatingProjectile p2 = new RotatingProjectile(getX(), getY(), 16, 16, facing, 0);
        		p2.setPalette(1);
        		byte[] sprites3 = {(byte)9, (byte)10, (byte)11, (byte)12,};
        		p2.setSpriteIDs(sprites3);
        		p2.setDiagSprites(sprites3);
        		byte[] sprites4 = {(byte)13, (byte)14, 71, 71};
        		p2.setStraightSprites(sprites4);
        		byte[] transforms3 = {0,0,0,0};
        		if(facing){p2.setVelX(2);}else{p2.setVelX(-2);}
        		p2.setTransforms(transforms3);
        		p2.setDiagTransforms(transforms3);
        		byte[] transforms4 = {0,0,0,0};
        		p2.setStraightTransforms(transforms4);
        		p2.setAccY(9.81/27);
        		p2.setVelY(-6);
        		getProjectiles().add(p2);
        	}
        }
        
        super.update(roomMap, t);
        
        jumpKeyLast = keys.getValue(88);
        
    }

	public boolean isSmall() {
		return small;
	}

	public void setSmallness(boolean small) {
		this.small = small;
	};
	
	public boolean isFacingEast(){
		return facing;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public void setMaxMP(int mp) {
		this.maxMP = mp;
	}

	public int getMP() {
		return MP;
	}

	public void setMP(int orgone) {
		this.MP = orgone;
	}
}