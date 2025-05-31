package io.github.TimBones;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.Collections;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Viewport viewport;
    private int t,x,y;
    private PPU ppu;
    private byte[][] tiles;
    private Keys keys;

    @Override
    public void create() {
        viewport = new IntFitViewport(256,240); //240 resolution - bottom + top 8 cutoff (i.e. set them black)
        t = 0;
        x=0;
        y=0;

        ppu = PPU.getReference();

        ppu.loadSheets(new Texture("towerw.png"), new Texture("palBox.bmp"));

        int[] pals = {0x0F011121, 0x101112, 0x202122, 0x303132, 0x040506, 0x141516, 0x242526, 0x343536};
        ppu.setPalettes(pals);

        int[] attrTable = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        //int[] attrTable = {0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100,};
        //int[] attrTable = {0x00000000,0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF,};
        ppu.setAttributeTable(attrTable);

        Room room0 = new Room(0);

        tiles = new byte[32][34];

        for(int j = 7; j < 29; j++){
            for(int i = 1; i < 33; i++){
                tiles[j][i] = (byte) (96 + 2*room0.getTilemap()[(j-7)/2][(i-1)/2] + 16*((j+1)%2) + (i+1)%2);
            }
        }

        GameSettings settings = GameSettings.getReference();
        GameSettings.getSettingsFromJSON();
        GameSettings.sendSettingsToJSON();
        this.keys = new Keys();
        Gdx.input.setInputProcessor(keys);
    }

    @Override
    public void render() {
        ppu.sendToOAM(0x01010000 + ((x%256) << 8) + (y%240));
        if(keys.left()){
            x--;
        }else if(keys.right()){
            x++;
        }

        if(keys.up()){
            y--;
        }else if(keys.down()){
            y++;
        }
        //Integer[] attrTable = {0, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100,};
        Integer[] attrTable = {0xF3F3F3F3,0x00000000, 0x00000000,0x00000000,0x00000000, 0x00000000, 0x00000000,0x00000000,0x00000000, 0x00000000, 0x00000000,0x00000000,0x00000000, 0x00000000, 0x00000000,0x00000000,0x00000000,};//0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF,};
        //Collections.rotate(Arrays.asList(attrTable), t / 16);
        int[] newAttrTable = new int[17];
        for(int i = 0; i < 17; i++){
            newAttrTable[i] = Integer.rotateRight(attrTable[i], 0);//2*(t / 16));
        }
        ppu.setAttributeTable(newAttrTable);
        ppu.render(viewport, this.tiles, 0,0, 7, 32);
        t--;
    }

    @Override
    public void dispose() {
        ppu.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
