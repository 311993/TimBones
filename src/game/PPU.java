package game;

import java.awt.Graphics;
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
	
	private byte[][] palette0 = {
			{0,		0,		0,		-17},
			{0,		70,		-128,	-17},
			{0,		103,	-89,	-17}
	};
	
	private byte[][] palette1 = {
			{0,		119,	-64,	-1},
			{0,		40,		70,		-120},
			{0,		0,		0,		0}
	};
	
	private byte[][] palette2 = {
			{0,		95, 	-113,	-81,},
			{0,		95,		-113,	-81,},
			{0,		95,		-113,	-81,}
	};
	
	private byte[][] palette3 = {
			{0,		95, 	-113,	-81,},
			{0,		95,		-113,	-81,},
			{0,		95,		-113,	-81,}
	};
	
	private byte[][] palette4 = {
			{0,		0, 		-113,	-17,},
			{0,		70,		0,		-17,},
			{0,		103,	38,		-17,}
	};
	
	private IndexColorModel[] palettes = {
			new IndexColorModel(2, 4, palette0[0], palette0[1], palette0[2], 0),
			new IndexColorModel(2, 4, palette1[0], palette1[1], palette1[2], 0),
			new IndexColorModel(2, 4, palette2[0], palette2[1], palette2[2], 0),
			new IndexColorModel(2, 4, palette3[0], palette3[1], palette3[2], 0),
			new IndexColorModel(2, 4, palette4[0], palette4[1], palette4[2], 0),
	};
	
	private ArrayList<BufferedImage> spriteSheets;
	private ArrayList<IndexColorModel> allPalettes; //TODO: change palettes[] to an int array that points here?
	
	public PPU(int BPP){
		//TODO: implement variable BPP
		//TODO:load images from directory to proper list
		//TODO:load palettes from files to list
		
		spriteSheets = new ArrayList<BufferedImage>();
		
		this.BPP = BPP;
		try {
			spriteSheets.add(to2BPP(ImageIO.read(new File("src\\assets\\playerSheet.png")), 0));
			spriteSheets.add(to2BPP(ImageIO.read(new File("src\\assets\\menuSheet.png")), 0));
		}catch (IOException e) {
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
		
		palettes[palette] = new IndexColorModel(2, 4, newPalette[0], newPalette[1], newPalette[2], 0);		
	}
	
	private BufferedImage renderSprite(int spriteSheet, int index, int palette){
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,8, 8, 2), new Point(0,0));
		BufferedImage output = new BufferedImage(palettes[palette], raster, false, null);
		WritableRaster srcRaster = spriteSheets.get(spriteSheet).copyData(null);
		
		raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,8, 8, 2), new Point(0,0));
		
		for(int y = 0; y < raster.getHeight(); y++){
			for(int x = 0; x < raster.getWidth(); x++){
				raster.setPixel(x, y, srcRaster.getPixel(8*(index%16)+x, 8*(index/16)+y, new int[1]));
			}
		}
		return new BufferedImage(palettes[palette], raster, false, null);
	}
	
	private BufferedImage renderText(String text, int palette){
		text = text.toUpperCase();
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,text.length()*8, 8, 2), new Point(0,0));
		BufferedImage output = new BufferedImage(palettes[4], raster, false, null);
		WritableRaster srcRaster = spriteSheets.get(1).copyData(null);
		
		for(int i = 0; i < text.length(); i++){
			
			int index = text.charAt(i);
			if(index > 47 && index < 58){
				index -=48;
			}else if(index > 63 && index < 91){
				index -= 55;
			}else{
				switch(index){
				case ',':	index = 0;	break;
				case '!':	index = 1;	break;
				case '\'':	index = 2;	break;
				case '&':	index = 3;	break;
				case '.':	index = 4;	break;
				case '\"':	index = 5;	break;
				case '?':	index = 6;	break;
				case '-':	index = 7;	break;
				case '~':	index = 8;	break;
				case '^':	index = 9;	break;
				case '_':	index = 10;	break;
				default:	index = 11;	break;
				}
				index += 36;
			
			}
			
			raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,8, 8, 2), new Point(0,0));
					
			for(int y = 0; y < raster.getHeight(); y++){
				for(int x = 0; x < raster.getWidth(); x++){
					raster.setPixel(x, y, srcRaster.getPixel(8*(index%16)+x, 8*(index/16)+y, new int[1]));
				}
			}
				
			BufferedImage sprite = new BufferedImage(palettes[4], raster, false, null);
						
			output.getGraphics().drawImage(sprite, i*8, 0, null);
		}
	
		return output;
	}
	
	public BufferedImage renderBox(int w, int h){
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,w*8, h*8, 2), new Point(0,0));
		BufferedImage output = new BufferedImage(palettes[4], raster, false, null);
		
		output.getGraphics().drawImage(util.ImageTools.flipVertical(renderSprite(1,73,4)), 0, 0, null);
		output.getGraphics().drawImage(util.ImageTools.flipHorizontal(util.ImageTools.flipVertical(renderSprite(1,73,4))), 0, h*8-8, null);
		output.getGraphics().drawImage(renderSprite(1,73,4), w*8-8, 0, null);
		output.getGraphics().drawImage(util.ImageTools.flipHorizontal(renderSprite(1,73,4)), w*8-8, h*8-8, null);
		
		for(int i = 1; i <	w - 1; i++){					
			output.getGraphics().drawImage(renderSprite(1,72,4), i*8, 0, null);
			output.getGraphics().drawImage(util.ImageTools.flipHorizontal(renderSprite(1,72,4)), i*8, h*8-8, null);
		}
		
		for(int i = 1; i <	h - 1; i++){					
			output.getGraphics().drawImage(util.ImageTools.rotateLeft(renderSprite(1,72,4)), 0, i*8, null);
			output.getGraphics().drawImage(util.ImageTools.rotateRight(renderSprite(1,72,4)), w*8-8, i*8, null);
		}
	
		return output;
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
	
	public BufferedImage renderMenu(Player p){
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,256, 48, 2), new Point(0,0));
		BufferedImage output = new BufferedImage(palettes[4], raster, false, null);
		Graphics g = output.getGraphics();
		
		g.drawImage(renderText("~HEALTH~", 0), 8, 8, null);
		for(int i = 0; i <= p.getMaxHealth()+1; i+=2){
			BufferedImage barSegment;
			if(i == 0){
				if(p.getHealth() >= 0){
					barSegment = renderSprite(1,50, 4);
				}else{
					barSegment = renderSprite(1,48, 4);
				}
			}else if( i == p.getMaxHealth()+1){
				if(p.getHealth() == p.getMaxHealth()){
					barSegment = renderSprite(1,53, 4);
				}else{
					barSegment = renderSprite(1, 54, 4);
				}
			}else{
				if(i <= p.getHealth()){
					barSegment = renderSprite(1,51, 4);
				}else if(i - 1 == p.getHealth()){
					barSegment = renderSprite(1,52, 4);
				}else{
					barSegment = renderSprite(1,49, 4);
				}
			}
			g.drawImage(barSegment, i*4, 16, null);
		}
		
		g.drawImage(renderText("~ORGONE~", 0), 8, 24, null);
		for(int i = 0; i <= p.getMaxMP()+1; i+=2){
			BufferedImage barSegment;
			if(i == 0){
				if(p.getMP() >= 0){
					barSegment = renderSprite(1,55, 4);
				}else{
					barSegment = renderSprite(1,48, 4);
				}
			}else if( i == p.getMaxMP()+1){
				if(p.getMP() == p.getMaxMP()){
					barSegment = renderSprite(1,58, 4);
				}else{
					barSegment = renderSprite(1, 74, 4);
				}
			}else{
				if(i <= p.getMP()){
					barSegment = renderSprite(1,56, 4);
				}else if(i - 1 == p.getMP()){
					barSegment = renderSprite(1,57, 4);
				}else{
					barSegment = renderSprite(1,49, 4);
				}
			}
			g.drawImage(barSegment, i*4, 32, null);
		}
		
		g.drawImage(renderBox(3,3),96,8,null);
		g.drawImage(renderBox(9,3),136,8,null);
		g.drawImage(renderText("ITEMS", 0), 88, 32, null);
		g.drawImage(renderText("COURTYD.", 0), 144, 32, null);
		
		//Castle Map
		g.drawImage(renderSprite(1,64,4),216, 8, null);
		g.drawImage(renderSprite(1,65,4),224, 8, null);
		g.drawImage(renderSprite(1,66,4),232, 8, null);
		g.drawImage(renderSprite(1,67,4),240, 8, null);
		g.drawImage(renderSprite(1,68,4),216, 16, null);
		g.drawImage(renderSprite(1,69,4),224, 16, null);
		g.drawImage(renderSprite(1,70,4),232, 16, null);
		g.drawImage(renderSprite(1,68,4),240, 16, null);

		g.drawImage(renderSprite(1,68,4),216, 24, null);
		g.drawImage(renderSprite(1,68,4),224, 24, null);
		g.drawImage(renderSprite(1,68,4),232, 24, null);
		g.drawImage(renderSprite(1,68,4),240, 24, null);
		g.drawImage(renderSprite(1,68,4),216, 32, null);
		g.drawImage(renderSprite(1,68,4),224, 32, null);
		g.drawImage(renderSprite(1,68,4),232, 32, null);
		g.drawImage(renderSprite(1,68,4),240, 32, null);
		
		return output;
	}
}
