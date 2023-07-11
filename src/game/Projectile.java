package game;

public class Projectile extends PhysicsEntity {
		
	public Projectile(double x, double y, int w, int h, int id) {
		super(x, y, w, h, id);
		this.setAccY(0);
	}
	
	public void update(int[][] roomMap, int t){
		super.update(roomMap, t);
		
	}
	
}
