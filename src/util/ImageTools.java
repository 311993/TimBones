package util;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageTools {
	
	public ImageTools() {
		// TODO Auto-generated constructor stub
	}
	
	public static BufferedImage downscale(BufferedImage original, int factor){
		BufferedImage output = new BufferedImage(original.getWidth()/factor, original.getHeight()/factor, BufferedImage.TYPE_4BYTE_ABGR);
				
		for(int y = 0; y < output.getHeight(); y++){
			for(int x = 0; x < output.getWidth(); x++){
				
				output.setRGB(x, y, original.getRGB(x*factor, y*factor));
			}
		}
		
		return output;
	}
	
	public static BufferedImage upscale(BufferedImage original, int factor){
		BufferedImage output = new BufferedImage(original.getWidth()*factor, original.getHeight()*factor, BufferedImage.TYPE_4BYTE_ABGR);
		
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
	
	public static BufferedImage flipVertical(BufferedImage original){
		BufferedImage output = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				output.setRGB(x, output.getHeight() - 1 - y, original.getRGB(x, y));
			}
		}
		
		return output;
	}
	
	public static BufferedImage flipHorizontal(BufferedImage original){
		BufferedImage output = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				output.setRGB(output.getWidth() - 1 - x, y, original.getRGB(x, y));
			}
		}
		
		return output;
	}

	public static BufferedImage rotateRight(BufferedImage original){
		BufferedImage output = new BufferedImage(original.getHeight(), original.getWidth(), BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				output.setRGB(original.getHeight() - 1 - y, x, original.getRGB(x, y));
			}
		}
		
		return output;
	}
	
	public static BufferedImage rotateLeft(BufferedImage original){
		BufferedImage output = new BufferedImage(original.getHeight(), original.getWidth(), BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				output.setRGB(y, original.getWidth() - 1 - x, original.getRGB(x, y));
			}
		}
		
		return output;
	}
	
	public static BufferedImage recolor(BufferedImage original, Color[] find,  Color[] replace){
		
		BufferedImage output = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
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
