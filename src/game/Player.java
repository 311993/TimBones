package game;

public class Player {

	double x = 16;
    double y = 96;
    
    double v_x = 0;
    double v_y = 0;
    
    double a_x = 0;
    double a_y = 9.81/20;
    
    int w = 16;
    int h = 24;
    double spd = 2;
    
    double previousX = 200;
    double previousY = 200;
    
    double terminalVelocity  = 12;
    
    int jumps =  2;
    boolean jumpPrevious = false;
    int jumpsMax =  999999;
    boolean jumpKeyLast = false;

    private Keys keys;
    
	public Player(Keys keys) {
		this.keys = keys;
	}

    public void update(){
        if(keys.getValue(37)){
            v_x = -spd;
        }
        
        else if(keys.getValue(39)){
            v_x = spd;
        }
        
        else{
            v_x = 0;
        }
        
        if(keys.getValue(38) && jumps > 0 && !jumpKeyLast){
            v_y = -4.2;
            jumps --;
            jumpPrevious = true;
        }
        
        if(keys.getValue(38) && jumpPrevious){
            if(v_y < 0){v_y -= 0.24;}
        }else if(jumpPrevious){
            jumpPrevious = false;
        }
        
        previousX = x;
        previousY = y;
        jumpKeyLast = keys.getValue(38);
        
        if(Math.abs(v_y) > terminalVelocity){
        	v_y = Math.signum(v_y)*terminalVelocity;
        }
        
        x += v_x;
        y += v_y;
        
        v_x += a_x;
        v_y += a_y;
        
        if(jumps == jumpsMax && v_y > a_y){
            jumps--;
        }
        
    };
}