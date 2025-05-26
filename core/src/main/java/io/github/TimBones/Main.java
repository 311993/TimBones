package io.github.TimBones;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.Collections;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Viewport viewport;
    private int t;
    private PPU ppu;
    private byte[][] tiles;

    @Override
    public void create() {
        viewport = new IntFitViewport(256,240); //240 resolution - bottom + top 8 cutoff (i.e. set them black)
        t = 0;

        ppu = PPU.getReference();

        ppu.loadSheets(Gdx.files.internal("tileSheetTestBit.png"), Gdx.files.internal("palBox.bmp"));

        int[] pals = {0x0F000102, 0x101112, 0x202122, 0x303132, 0x040506, 0x141516, 0x242526, 0x343536};
        ppu.setPalettes(pals);

        int[] attrTable = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        //int[] attrTable = {0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100,};
        //int[] attrTable = {0x00000000,0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF,};
        ppu.setAttributeTable(attrTable);

        tiles = new byte[32][34];

        for(int j = 0; j < 30; j++){
            for(int i = 0; i < 32; i++){
                tiles[j][i] = (byte)(j * 16 + i % 16);
            }
        }
    }

    @Override
    public void render() {
        ppu.sendToOAM(0x11000000);
        //Integer[] attrTable = {0, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100,};
        Integer[] attrTable = {0xFFFFFFFF,0x00000000, 0x00000000,0x00000000,0x00000000, 0x00000000, 0x00000000,0x00000000,0x00000000, 0x00000000, 0x00000000,0x00000000,0x00000000, 0x00000000, 0x00000000,0x00000000,0x00000000,};//0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF, 0x00000000, 0x55555555, 0xAAAAAAAA, 0xFFFFFFFF,};
        Collections.rotate(Arrays.asList(attrTable), t / 16);
        int[] newAttrTable = new int[17];
        for(int i = 0; i < 17; i++){
            newAttrTable[i] = Integer.rotateRight(attrTable[i], 2*(t / 16));
        }
        ppu.setAttributeTable(newAttrTable);
        ppu.render(viewport, this.tiles, t % 16,t % 16, -t / 8, -t / 8);
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
