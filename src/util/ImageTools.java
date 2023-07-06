package util;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ImageTools {
	
	public static BufferedImage downscale(BufferedImage original, int factor){
		BufferedImage output = new BufferedImage(original.getWidth()/factor, original.getHeight()/factor, original.getType());
				
		for(int y = 0; y < output.getHeight(); y++){
			for(int x = 0; x < output.getWidth(); x++){
				
				output.setRGB(x, y, original.getRGB(x*factor, y*factor));
			}
		}
		
		return output;
	}
	
	public static BufferedImage upscale(BufferedImage original, int factor){
		BufferedImage output = new BufferedImage(original.getWidth()*factor, original.getHeight()*factor, original.getType());
		
		int[] colors = new int[factor*factor];
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				for(int i = 0; i < colors.length; i++){
					colors[i] = original.getRGB(x, y);
				}
				output.setRGB(x*factor, y*factor, factor, factor, colors, 0, factor);
			}
		}
		
		return output;
	}
	
	public static BufferedImage flipHorizontal(BufferedImage original){
		WritableRaster srcRaster = original.getRaster();
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(srcRaster.getTransferType(),original.getWidth(), original.getHeight(), original.getColorModel().getPixelSize()), new Point(0,0));
		BufferedImage output = new BufferedImage(original.getColorModel(), raster, original.isAlphaPremultiplied(), null);
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				raster.setPixel(x, output.getHeight() - 1 - y, srcRaster.getPixel(x, y, new int[1]));
			}
		}
		
		return output;
	}
	
	public static BufferedImage flipVertical(BufferedImage original){		
		WritableRaster srcRaster = original.getRaster();
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(srcRaster.getTransferType(),original.getWidth(), original.getHeight(), original.getColorModel().getPixelSize()), new Point(0,0));
		BufferedImage output = new BufferedImage(original.getColorModel(), raster, original.isAlphaPremultiplied(), null);
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				raster.setPixel(output.getWidth() - 1 - x, y, srcRaster.getPixel(x, y, new int[1]));
			}
		}
		
		return output;
	}

	public static BufferedImage rotateRight(BufferedImage original){
		WritableRaster srcRaster = original.getRaster();
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(srcRaster.getTransferType(),original.getWidth(), original.getHeight(), original.getColorModel().getPixelSize()), new Point(0,0));
		BufferedImage output = new BufferedImage(original.getColorModel(), raster, original.isAlphaPremultiplied(), null);
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				raster.setPixel(original.getHeight() - 1 - y, x, srcRaster.getPixel(x, y, new int[1]));
			}
		}
		
		return output;
	}
	
	public static BufferedImage rotateLeft(BufferedImage original){
		WritableRaster srcRaster = original.getRaster();
		WritableRaster raster = Raster.createWritableRaster(new MultiPixelPackedSampleModel(srcRaster.getTransferType(),original.getWidth(), original.getHeight(), original.getColorModel().getPixelSize()), new Point(0,0));
		BufferedImage output = new BufferedImage(original.getColorModel(), raster, original.isAlphaPremultiplied(), null);
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				raster.setPixel(y, original.getWidth() - 1 - x, srcRaster.getPixel(x, y, new int[1]));
			}
		}
		
		return output;
	}
	
	public static BufferedImage recolor(BufferedImage original, Color[] find,  Color[] replace){
		
		BufferedImage output = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				for(int m = 0; m < find.length; m++){
					if(original.getRGB(x, y) == find[m].getRGB()){
						output.setRGB(x, y, replace[m].getRGB());
					}else{
						output.setRGB(x, y, original.getRGB(x, y));
					}
				}
			}
		}
		
		return output;
	}
}
