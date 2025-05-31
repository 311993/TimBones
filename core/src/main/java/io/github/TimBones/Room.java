package io.github.TimBones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;

public class Room {

    private int w,h;
    private String region;
    private char[][] tilemap;
    private char[] entities;
    private boolean bossRoom;

    public Room(){
        this(0);
    }

    public Room(int num){
        FileHandle roomFile = Gdx.files.local("data/rooms/room" + num + ".hex");
        byte[] temp = roomFile.readBytes();

        assert temp[0] == num;

        w = temp[1] & 0xFF;
        h = temp[2] & 0xFF;

        region = "";
        for(int i = 0; i < 9; i++){
            region += (char)(temp[3 + i] & 0xFF);
        }

        tilemap = new char[h*11][w*16];
        for(int j = 0; j < h*11; j++){
            for(int i = 0; i < w*16; i++){
                tilemap[j][i] = (char)(temp[j*w*16 + i + 16] & 0xFF);
            }
        }
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public String getRegion() {
        return region;
    }

    public char[][] getTilemap() {
        return tilemap;
    }

    public char[] getEntities() {
        return entities;
    }

    public boolean isBossRoom() {
        return bossRoom;
    }

    public void setTilemap(char[][] tilemap) {
        this.tilemap = tilemap;
    }
}
