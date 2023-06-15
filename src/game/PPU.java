package game;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class PPU {
	
	private static byte[][] palette1 = {
			{0,		0,		0,		-17},
			{0,		70,		-128,	-17},
			{0,		103,	-89,	-17}
	};
	
	private static byte[][] palette2 = {
			{0,		119,	-64,	-17},
			{0,		40,		70,		-17},
			{0,		0,		0,		-17}
	};
	
	private static IndexColorModel[] palettes = {
			new IndexColorModel(2, 4, palette1[0], palette1[1], palette1[2], 0),
			new IndexColorModel(2, 4, palette2[0], palette2[1], palette2[2], 0),
	};
	
	public static BufferedImage to2BPP(BufferedImage src, int palette){
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
	
	public static void newPalette(int palette, byte[][] newPalette){
		palettes[palette] = new IndexColorModel(2, 4, newPalette[0], newPalette[1], newPalette[2], 0);
	}
	
	public static BufferedImage swapPalette(BufferedImage img, int palette){
		return new BufferedImage(palettes[palette], img.getRaster(), false, null);
	}

}
