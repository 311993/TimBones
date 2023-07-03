package game;

public class Entity {	//TODO: should this be an abstract class?
	
	private double x;
    private double y;
    
    private int w;
    private int h;
    
    private int spriteSheetID;	//points to sprite sheet stored in PPU
    private byte[] spriteIDs; 	//points to specific sprites on the sheet
	private int palette;
    
    private boolean killFlag = false;
    
    public Entity(double x, double y, int w, int h, int id) {
    	setX(x);
		setY(y);
		setSize(w,h);
		setSpriteSheetID(id);
	}
    
    public void update(int[][] roomMap){
    	
    };
    
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

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
		
		byte[] temp = getSpriteIDs();
		
		this.setSpriteIDs(new byte[w/8 * h/8]);
	}

	public byte[] getSpriteIDs() {
		return spriteIDs;
	}

	public void setSpriteIDs(byte[] spriteIDs) {
		this.spriteIDs = spriteIDs;
	}
	
	public void setSpriteID(int index, byte spriteID) {
		this.spriteIDs[index] = spriteID;
	}

	public int getSpriteSheetID() {
		return spriteSheetID;
	}

	public void setSpriteSheetID(int spriteSheetID) {
		this.spriteSheetID = spriteSheetID;
	}

	public boolean isKillFlagged() {
		return killFlag;
	}

	public void kill() {
		this.killFlag = true;
	}

	public int getPalette() {
		return palette;
	}

	public void setPalette(int palette) {
		this.palette = palette;
	}

}
