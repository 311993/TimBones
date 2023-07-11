package game;

public class OverlayEntity extends Entity {
	
	private Entity leader;
	
	public OverlayEntity(Entity leader, int xOffset, int yOffset, int w, int h, int id) {
		super(leader.getX() + xOffset, leader.getY() + yOffset, 0, 0, id);
		this.setxOffset(xOffset);
		this.setyOffset(yOffset);
		this.setX(leader.getX());
		this.setY(leader.getY());
		this.setSize(w, h);
		this.leader = leader;
	}
	
	public void update(int[][] roomMap, int t){
		this.setX(leader.getX());
		this.setY(leader.getY());
		
		if(leader.isKillFlagged()){
			this.kill();
		}
		
		super.update(roomMap, t);
	}
	
	protected Entity getLeader(){
		return leader;
	}
}
