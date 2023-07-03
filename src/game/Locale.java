package game;

import java.io.File;

public class Locale {
	
	//point to index of 
	private int[] spritePalettes;
	
	private int[] tilePalettes;
	
	//point to indices of the tile images stored in PPU that correspond to a given value in the tilemap of a room
	private int[] tileIDs;
	
	public Locale(File locFile) {
		// load data from .loc file
	}
	
	//values 0 - 7 are local sprite and tile palettes, then tileIDs
	public int[] getLocalValues(){
		int[] temp = new int[tileIDs.length + spritePalettes.length + tilePalettes.length];
		
		for(int i = 0; i < spritePalettes.length; i++){
			temp[i] = spritePalettes[i];
		}
		
		for(int i = 0; i < tilePalettes.length; i++){
			temp[i+spritePalettes.length] = tilePalettes[i];
		}
		
		for(int i = 0; i < tileIDs.length; i++){
			temp[i+spritePalettes.length+tilePalettes.length] = tileIDs[i];
		}
		
		return temp;
	}

}
