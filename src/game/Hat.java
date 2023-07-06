package game;

public class Hat extends OverlayEntity{
	
	private Player p;
	private Keys keys;
	
	public Hat(Player leader, Keys keys) {
		super(leader, 0, -8, 16, 32, 0);
		this.keys = keys;
		p = (Player)leader;
		
		this.setSpriteID(0, (byte)48);	this.setSpriteID(1, (byte)49);
		this.setSpriteID(2, (byte)64);	this.setSpriteID(3, (byte)65);
		this.setSpriteID(4, (byte)71);	this.setSpriteID(5, (byte)71);
		this.setSpriteID(6, (byte)80);	this.setSpriteID(7, (byte)81);
	}
	
	public void update(int[][] roomMap, int t){
		
		
			if(p.isSmall()){
				this.setSize(8, 16);
				this.setyOffset(0);
			}else{
				this.setyOffset(-8);
				this.setSize(16, 32);
				this.setSpriteID(4, (byte)71);	this.setSpriteID(5, (byte)71);
				
				if(p.isFacingEast()){
					this.setSpriteID(0, (byte)48);	this.setSpriteID(1, (byte)49);
					this.setSpriteID(2, (byte)64);	this.setSpriteID(3, (byte)65);
					this.setSpriteID(6, (byte)80);	this.setSpriteID(7, (byte)81);
					byte[] transforms = {0,0,0,0,0,0,0,0};
					this.setTransforms(transforms);
				}else{
					this.setSpriteID(0, (byte)49);	this.setSpriteID(1, (byte)48);
					this.setSpriteID(2, (byte)65);	this.setSpriteID(3, (byte)64);
					this.setSpriteID(6, (byte)81);	this.setSpriteID(7, (byte)80);
					byte[] transforms = {1,1,1,1,0,0,1,1};
	        		this.setTransforms(transforms);
				}
			}
		
		if(!p.isSmall()){
			if(keys.getValue(39) /*|| (p.isFacingEast() && Math.abs(p.getVelY()) > 1)*/){
			
				int frame = (t/4)%4;
            
				switch(frame){
            		case 0:
            			this.setSpriteID(0, (byte)48);	this.setSpriteID(1, (byte)49);
            			this.setSpriteID(2, (byte)64);	this.setSpriteID(3, (byte)65);
            			this.setSpriteID(6, (byte)80);	this.setSpriteID(7, (byte)81);
            		break;
            		case 1:
            			this.setSpriteID(0, (byte)50);	this.setSpriteID(1, (byte)51);
            			this.setSpriteID(2, (byte)66);	this.setSpriteID(3, (byte)67);
            			this.setSpriteID(6, (byte)82);	this.setSpriteID(7, (byte)83);
            		break;
            		case 2:
            			this.setSpriteID(0, (byte)52);	this.setSpriteID(1, (byte)53);
            			this.setSpriteID(2, (byte)68);	this.setSpriteID(3, (byte)69);
            			this.setSpriteID(6, (byte)84);	this.setSpriteID(7, (byte)85);
            		break;
            		case 3:
            			this.setSpriteID(0, (byte)54);	this.setSpriteID(1, (byte)55);
            			this.setSpriteID(2, (byte)64);	this.setSpriteID(3, (byte)65);
            			this.setSpriteID(6, (byte)86);	this.setSpriteID(7, (byte)87);
            		break;
				}
			
				byte[] transforms = {0,0,0,0,0,0,0,0};
				this.setTransforms(transforms);
		
			}
			if(keys.getValue(37) /*|| (!p.isFacingEast() && Math.abs(p.getVelY()) > 1)*/){
			
				int frame = (t/4)%4;
            
				switch(frame){
					case 0:
						this.setSpriteID(0, (byte)49);	this.setSpriteID(1, (byte)48);
						this.setSpriteID(2, (byte)65);	this.setSpriteID(3, (byte)64);
						this.setSpriteID(6, (byte)81);	this.setSpriteID(7, (byte)80);
					break;
					case 1:
						this.setSpriteID(0, (byte)51);	this.setSpriteID(1, (byte)50);
						this.setSpriteID(2, (byte)67);	this.setSpriteID(3, (byte)66);
						this.setSpriteID(6, (byte)83);	this.setSpriteID(7, (byte)82);
					break;
					case 2:
						this.setSpriteID(0, (byte)53);	this.setSpriteID(1, (byte)52);
						this.setSpriteID(2, (byte)69);	this.setSpriteID(3, (byte)68);
						this.setSpriteID(6, (byte)85);	this.setSpriteID(7, (byte)84);
            		break;
					case 3:
						this.setSpriteID(0, (byte)55);	this.setSpriteID(1, (byte)54);
						this.setSpriteID(2, (byte)65);	this.setSpriteID(3, (byte)64);
						this.setSpriteID(6, (byte)87);	this.setSpriteID(7, (byte)86);
					break;
				}
			
				byte[] transforms = {1,1,1,1,0,0,1,1};
        		
				this.setTransforms(transforms);
			}
		}else{
			if(p.isFacingEast()){ byte[] transforms = {0,0}; this.setTransforms(transforms);}else{ byte[] transforms = {1,1}; this.setTransforms(transforms);}
			this.setSpriteID(0, (byte)73);
			this.setSpriteID(1, (byte)89);

		}
		
		super.update(roomMap, t);
	}
}
