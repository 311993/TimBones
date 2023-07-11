package game;

public class RotatingProjectile extends Projectile {
	
	private byte[] diagSprites;
	private byte[] straightSprites;
	private byte[] diagTransforms;
	private byte[] straightTransforms;
	private boolean cw;
	private byte timeOffset;
	
	public RotatingProjectile(double x, double y, int w, int h, boolean cw, int id) {
		super(x, y, w, h, id);
		this.cw = cw;
		timeOffset = 32;
	}
	
	public void update(int[][] roomMap, int t){
		super.update(roomMap, t);
		
		if(timeOffset > 31){
			timeOffset = (byte) (t%32);
			if(!cw){
				
				this.setTransforms(straightTransforms);
				byte[] transforms = {(byte) ((16 + (getTransform(1)-4))%16),(byte) ((16 + (getTransform(3)-4)%16)),(byte) ((16 + (getTransform(0)-4)%16)),(byte) ((16 + (getTransform(2)-4)%16)),};
				straightTransforms = transforms;
				
				byte[] sprites = {getStraightSprite(1),getStraightSprite(3),getStraightSprite(0),getStraightSprite(2)};
				setStraightSprites(sprites);
			}
		}
		
		t -= timeOffset;
		
		if(t%4 == 0){
			if(t%8 == 0 || straightSprites.length == 0){
				setxOffset(0);
				setyOffset(0);
				
				if(cw){
					byte[] sprites = {getDiagSprite(2),getDiagSprite(0),getDiagSprite(3),getDiagSprite(1)};
					this.setSpriteIDs(sprites);
					this.setDiagSprites(sprites);
					
					this.setTransforms(diagTransforms);
					byte[] transforms = {(byte) ((getTransform(2)+4)%16),(byte) ((getTransform(0)+4)%16),(byte) ((getTransform(3)+4)%16),(byte) ((getTransform(1)+4)%16)};
					this.setTransforms(transforms);
					diagTransforms = transforms;
					
				}else{
					byte[] sprites = {getDiagSprite(1),getDiagSprite(3),getDiagSprite(0),getDiagSprite(2)};
					this.setSpriteIDs(sprites);
					this.setDiagSprites(sprites);
					
					this.setTransforms(diagTransforms);
					byte[] transforms = {(byte) ((16 + (getTransform(1)-4))%16),(byte) ((16 + (getTransform(3)-4))%16),(byte) ((16 + (getTransform(0)-4))%16),(byte) ((16 + (getTransform(2)-4)%16)%16),};
					this.setTransforms(transforms);
					diagTransforms = transforms;
				}
				
				
			}else{
								
				if(cw){
					
					switch(t%32){
					case 4:	setxOffset(-4);	break;
					case 12:setyOffset(-4);	break;
					case 20:setxOffset(4);	break;
					case 28:setyOffset(4);	break;
					}
					
					byte[] sprites = {getStraightSprite(2),getStraightSprite(0),getStraightSprite(3),getStraightSprite(1)};
					this.setSpriteIDs(sprites);
					this.setStraightSprites(sprites);
					

					this.setTransforms(straightTransforms);
					byte[] transforms = {(byte) ((getTransform(2)+4)%16),(byte) ((getTransform(0)+4)%16),(byte) ((getTransform(3)+4)%16),(byte) ((getTransform(1)+4)%16)};
					this.setTransforms(transforms);
					straightTransforms = transforms;
				}else{
					
					switch(t%32){
					case 4:	setyOffset(-4);	break;
					case 12:setxOffset(-4);	break;
					case 20:setyOffset(4);	break;
					case 28:setxOffset(4);	break;
					}
					
					byte[] sprites = {getStraightSprite(1),getStraightSprite(3),getStraightSprite(0),getStraightSprite(2)};
					this.setSpriteIDs(sprites);
					this.setStraightSprites(sprites);
					
					this.setTransforms(straightTransforms);
					byte[] transforms = {(byte) ((16 + (getTransform(1)-4))%16),(byte) ((16 + (getTransform(3)-4)%16)),(byte) ((16 + (getTransform(0)-4)%16)),(byte) ((16 + (getTransform(2)-4)%16)),};
					this.setTransforms(transforms);
					straightTransforms = transforms;
				}
			}
		}
	}
	
	public void setTransforms(byte[] transforms){
		super.setTransforms(transforms);
	}
	
	public byte getStraightSprite(int index) {
		return straightSprites[index];
	}

	public byte[] getDiagSprites() {
		return diagSprites;
	}
	
	public byte getDiagSprite(int index) {
		return diagSprites[index];
	}

	public void setDiagSprites(byte[] diagSprites) {
		this.diagSprites = diagSprites;
	}

	public byte[] getStraightSprites() {
		return straightSprites;
	}

	public void setStraightSprites(byte[] straightSprites) {
		this.straightSprites = straightSprites;
	}

	public void setStraightTransforms(byte[] straightTransforms) {
		this.straightTransforms = straightTransforms;
	}
	
	public void setDiagTransforms(byte[] diagTransforms) {
		this.diagTransforms = diagTransforms;
	}
	
}
