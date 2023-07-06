package game;

import java.io.File;

public class Locale {
	
	//point to index of 
	private int[] spritePalettes;
	
	private int[] tilePalettes;
	
	private String localSpriteAddress, localTileAddress, localMinibossAddress, localBossAddress;
	
	public Locale(File locFile) {
		// load data from .loc file
	}
	
	//values 0 - 7 are local sprite and tile palettes
	public int[] getLocalPalettes(){
		int[] temp = new int[spritePalettes.length + tilePalettes.length];
		
		for(int i = 0; i < spritePalettes.length; i++){
			temp[i] = spritePalettes[i];
		}
		
		for(int i = 0; i < tilePalettes.length; i++){
			temp[i+spritePalettes.length] = tilePalettes[i];
		}
		
		return temp;
	}

}
