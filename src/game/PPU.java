package game;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PPU {
	
	private int BPP;
	
	private byte[][] palette1 = {
			{0,		0,		0,		-17},
			{0,		70,		-128,	-17},
			{0,		103,	-89,	-17}
	};
	
	private byte[][] palette2 = {
			{0,		119,	-64,	-17},
			{0,		40,		70,		-17},
			{0,		0,		0,		-17}
	};
	
	private byte[][] palette3 = {
			{0,		95, 	-113,	-81,},
			{0,		95,		-113,	-81,},
			{0,		95,		-113,	-81,}
	};
	
	private IndexColorModel[] palettes = {
			new IndexColorModel(2, 4, palette1[0], palette1[1], palette1[2], 0),
			new IndexColorModel(2, 4, palette2[0], palette2[1], palette2[2], 0),
			new IndexColorModel(2, 4, palette3[0], palette3[1], palette3[2], 0),
	};
	
	private ArrayList<BufferedImage> spriteSheets;
	private ArrayList<IndexColorModel> allPalettes; //TODO: change palettes[] to an int array that points here
	
	public PPU(int BPP){
		//TODO: implement variable BPP
		//TODO:load images from directory to proper list
		//TODO:load palettes from files to list
		
		spriteSheets = new ArrayList<BufferedImage>();
		
		this.BPP = BPP;
		try {
			spriteSheets.add(to2BPP(ImageIO.read(new File("src\\assets\\playerSheet.png")), 0));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}
	
	public BufferedImage to2BPP(BufferedImage src, int palette){
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,src.getWidth(), src.getHeight(), 2), new Point(0,0));
		BufferedImage output = new BufferedImage(palettes[palette], raster, false, null);

		for(int y = 0; y < output.getHeight(); y++){
			for(int x = 0; x < output.getWidth(); x++){
				
				int[] key = {src.getRaster().getSample(x, y, 0)/85};
				raster.setPixel(x,y,key);
				
			}
		}
		
		return output;
	}
	
	//TODO: setPalette(int palette, int paletteID) to change to a palette in the list
	public void setPalette(int palette, byte[][] newPalette){
		
		IndexColorModel temp = palettes[palette];
		palettes[palette] = new IndexColorModel(2, 4, newPalette[0], newPalette[1], newPalette[2], 0);
		
		updateSprites(palette, temp);
		
	}
	
	private BufferedImage swapPalette(BufferedImage img, int palette){
		return new BufferedImage(palettes[palette], img.getRaster(), false, null);
	}
	
	private void updateSprites(int palette, IndexColorModel prev){
		for(int i = 0; i < spriteSheets.size(); i++){
			BufferedImage img = spriteSheets.get(i);
			if(img.getColorModel().equals(prev)){
				spriteSheets.set(i, swapPalette(img, palette));
			}
		}
	}
	
	public BufferedImage render(Entity e){
		
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,e.getW(), e.getH(), 2), new Point(0,0));
		BufferedImage output = new BufferedImage(palettes[e.getPalette()], raster, false, null);
		WritableRaster srcRaster = spriteSheets.get(e.getSpriteSheetID()).copyData(null);
		
		for(int j = 0; j < e.getH()/8; j++){
			for(int i = 0; i < e.getW()/8; i++){
				
				int index = j*e.getW()/8 + i;
				
				raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,8, 8, 2), new Point(0,0));
				
				for(int y = 0; y < raster.getHeight(); y++){
					for(int x = 0; x < raster.getWidth(); x++){
						raster.setPixel(x, y, srcRaster.getPixel(8*(e.getSpriteID(index)%16)+x, 8*(e.getSpriteID(index)/16)+y, new int[1]));
					}
				}
				
				BufferedImage sprite = new BufferedImage(palettes[e.getPalette()], raster, false, null);
				byte transform = e.getTransform(index);
				
				if(transform%4 >= 2){sprite = util.ImageTools.flipHorizontal(sprite);}
				if((transform%4)%2 > 0){sprite = util.ImageTools.flipVertical(sprite);}
				
				for(byte k = 0; k < transform/4; k++){
					sprite = util.ImageTools.rotateRight(sprite);
				}
				
				
				output.getGraphics().drawImage(sprite, i*8, j*8, null);
			}
		}
		
		return output;
	}
}
