package game;

public class OverlayEntity extends Entity {
	
	private Entity leader;
	private int xOffset, yOffset;
	
	public OverlayEntity(Entity leader, int xOffset, int yOffset, int id) {
		super(leader.getX() + xOffset, leader.getY() + yOffset, 0, 0, id);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.leader = leader;
	}
	
	public void update(int[][] roomMap){
		this.setX(leader.getX() + xOffset);
		this.setY(leader.getY() + yOffset);
		this.setSize(leader.getW(), leader.getH());
		
		if(leader.isKillFlagged()){
			this.kill();
		}
	}

}
