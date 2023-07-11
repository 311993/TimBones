package game;

public class PhysicsEntity extends Entity {
	
	private double velX = 0;
    private double velY = 0;
    
    private double accX = 0;
    private double accY = 9.81/20;	//TODO: make gravity + jumping operational based on a sim setting
	
    private double prevX;
    private double prevY;
    
    private double maxVel = 12;
    
	public PhysicsEntity(double x, double y, int w, int h, int id) {
		super(x, y, w, h, id);
		prevX = x;
		prevY = y;
	}
	
	public void update(int[][] roomMap, int t){
	    	
	   	prevX = getX();
	    prevY = getY();
	        
	    if(Math.abs(velY) > maxVel){
	      	velY = Math.signum(velY)*maxVel;
	    }
	        
	    setX(getX() + velX);
	    setY(getY() + velY);
	        
	    velX += accX;
	    velY += accY;
	    
	    super.update(roomMap, t);
	    }
	    
	    public boolean isColliding(int x2, int y2,int w2, int h2){
	    		return (getX() + getW() > x2 && getX() < x2 + w2 && getY() + getH() > y2 && getY() < y2 + h2);
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
		
		public double getMaxVel() {
			return maxVel;
		}

		public void setMaxVel(double maxVel) {
			this.maxVel = maxVel;
		}
		
		protected double getPrevX() {
			return prevX;
		}

		protected double getPrevY() {
			return prevY;
		}

}
