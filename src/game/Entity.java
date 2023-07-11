package game;

public class Entity {	//TODO: should this be an abstract class?
	
	public static final byte VERTICAL_REFLECTION = 1, 		VERTREF = 1;
	public static final byte HORIZONTAL_REFLECTION = 2, 	HORZREF= 2;
	public static final byte ROTATION_90 = 4, 				ROT90 = 4;
	public static final byte ROTATION_180 = 8, 				ROT180 = 8;
	public static final byte ROTATION_270 = 12, 			ROT270 = 12;
	
	private double x;
    private double y;
    
	private int xOffset, yOffset;
    
    private int w;
    private int h;
    
	private int palette;
    private int spriteSheetID;	//points to sprite sheet stored in PPU
    private byte[] spriteIDs; 	//points to specific sprites on the sheet
    
    private byte[] transforms; 	//codes sprites for rotation/reflection
    //vr = 1, hr = 2, 90 deg = 4, 180 = 8 - add to combine ---------------- or, (degrees/90) * 4 + vr + hr*2
    
    /*
    0	none		1	vert ref		2	hor ref			3	2 ref
	4	90 deg		5	90 deg + vr		6	90 deg + hr		7	90 deg + 2r
	8	180 deg		9	180 deg + vr	10	180 deg + hr	11	180 deg + 2r
	12	270 deg		13	270 deg + vr	14	270 deg + hr	15	270 deg + 2r
    */
        
    private boolean killFlag = false;
    
    public Entity(double x, double y, int w, int h, int id) {
    	setX(x);
		setY(y);
		setSpriteIDs(new byte[w/8*h/8]);
		setSize(w,h);
		setSpriteSheetID(id);
	}
    
    public void update(int[][] roomMap, int t){
    	
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
		
		this.setTransforms(new byte[w/8 * h/8]);
		this.setSpriteIDs(new byte[w/8 * h/8]);
		
		for(int i = 0; i < temp.length && i < getSpriteIDs().length;i++){
			setSpriteID(i, temp[i]);
		}
	}

	public byte[] getSpriteIDs() {
		return spriteIDs;
	}
	
	public byte getSpriteID(int index) {
		return this.spriteIDs[index];
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

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public byte[] getTransforms() {
		return transforms;
	}

	public void setTransforms(byte[] transforms) {
		this.transforms = transforms;
	}
	
	public void setTransform(int index, byte transform) {
		this.transforms[index] = transform;
	}

	public byte getTransform(int index) {
		return this.transforms[index];
	}

}
