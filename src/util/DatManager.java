package util;

import java.io.FileWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

/**DatManager provides utility methods to import and manage level data**/

public class DatManager {

	private static Color[] key = {Color.BLACK};
	
	public DatManager() {
		// TODO Auto-generated constructor stub
	}
	
	/**Converts an image to a new level data file (intended for use with a level editor or simply an image editor)
	 * Black = Solid Block 1
	 * Other colors = Background 1
	 * @param image - the image to source data from
	 * @return the generated file**/
	public static File imageToDat(BufferedImage image) throws IOException{
		File dat = null;
		FileWriter writer = null;
		int n = 0;
		
		do{
			dat = new File("src\\data\\level" + n + ".dat");
			n++;
		}while(!dat.createNewFile());
		
		writer = new FileWriter(dat);
		
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
			
					Color c = new Color(image.getRGB(x, y));
					
					int desig = 0;
					
					for(int i = 0; i < key.length; i++){
						if(c.equals(key[i])){
							desig = i + 1;
							break;
						}
					}
					
					writer.write(desig + " ");
			} 
			writer.write("\n");
		
		}
		
		writer.close();
		return dat;
		
	}
	
	/**Overwrites an existing level data file with data from an image 
	 * @param image - the image to source data from
	 * @param target - the existing file to overwrite
	 * @see {@link #imageToDat(BufferedImage)}**/
	public static File imageToDat(BufferedImage image, File target) throws IOException{
		File dat = target;
		FileWriter writer = new FileWriter(dat);
		
		writer.write(image.getWidth() + " " + image.getHeight() + "\n");
		
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
			
					Color c = new Color(image.getRGB(x, y));
					
					int desig = 0;
					
					for(int i = 0; i < key.length; i++){
						if(c.equals(key[i])){
							desig = i + 1;
							break;
						}
					}
					
					writer.write(desig + " ");
			} 
			writer.write("\n");
		
		}
		
		writer.close();
		return dat;
	}
	
	/**Converts level data to 2D int array
	 * @param data - the File to source data from
	 * @return a 2D int array describing the level data**/
	public static int[][] datToArray(File data) throws FileNotFoundException{
		Scanner reader = new Scanner(data);
		
		int width = reader.nextInt();
		int height = reader.nextInt();
		int[][] output = new int[height][width];
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				output[y][x] = reader.nextInt();
			}
		}
		
		reader.close();
		return output;
		
	}
	/**Encrypts a boolean array as a gibberish-looking hexadecimal string
	 * @param arr - the array to be encrypted
	 * @param path - the path of the File to be overwritten **WARNING** will overwrite the file passed in
	 * @return the encrypted File**/
	public static File Encrypt(boolean[] arr, String path) throws IOException{
    	
    	//To big int
    	BigInteger n = BigInteger.ZERO;
    	for(int i = 0; i < arr.length; i++){
    		if(arr[i]){
    			n = n.add((BigInteger.TWO).pow(i));
    		}
    	}
    	
    	//To hex
    	String hex = n.toString(16);
    	
    	//To gibberish
    	int init = (int)(Math.random()*15);
    	String data = Integer.toHexString(init);
    	
    	String[] digits = hex.split("");
    	
    	for(int i = 0; i < init; i++){
    		data += Integer.toHexString((int)(Math.random()*15));
    	}
    	
    	for(String s : digits){
    		data += s;
    		
    		for(int i = 0; i < Integer.parseInt(s,16); i++){
    			data += Integer.toHexString((int)(Math.random()*15));
    		}
    	}
    	
    	
    	//Write to File
    	
    	File f = null;
    	
    		f = new File(path);
    		
    		if(f.exists()){
    			f.delete();
    		}
    		
    		f.createNewFile();
    		
    		f.setWritable(true);
    		f.setReadable(true);
    		
    	FileWriter writer = new FileWriter(f);
    	
    	writer.write(data + " " + Integer.toHexString(arr.length));
    	
    	writer.close();
    	return f;
    	
    }
    
	/**Decrypts a file encrypted with {@link #Encrypt(boolean[])}
	 * @param f - the encrypted file
	 * @return the decrypted boolean array**/
    public static boolean[] Decrypt(File f) throws IOException{
    	//Read from file
    	Scanner reader = new Scanner(f);
    	
    	String data = reader.next();
    	    	
    	//To Hex
    	int index = Integer.parseInt(data.substring(0,1),16);
    	data = data.substring(1);
    	
    	String hex = "";
    	
    	while(index < data.length()){
    		hex+= data.substring(index, index+1);
    		index += Integer.parseInt(data.substring(index, index+1), 16) + 1;
    	} 
    	    	
    	//To big int
    	
    	BigInteger n = new BigInteger(hex,16);
        
    	//To boolean array
    	
    	int digits = Integer.parseInt(reader.next(),16);
    	
    	boolean[] arr = new boolean[digits];
    	
    	for(int i = digits -1; i >= 0; i--){
    		if(BigInteger.TWO.pow(i).compareTo(n) <= 0){
    			arr[i] = true;
    			n = n.subtract(BigInteger.TWO.pow(i));
    		}else{
    			arr[i] = false;
    		};
    	}
    	
    	reader.close();
    	return arr;
    }

}
