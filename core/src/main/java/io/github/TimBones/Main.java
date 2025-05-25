package io.github.TimBones;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Viewport viewport;
    private long t;
    private PPU ppu;

    @Override
    public void create() {
        viewport = new IntFitViewport(256,240); //240 resolution - bottom + top 8 cutoff (i.e. set them black)
        t = 0;

        ppu = PPU.getReference();

        ppu.loadSheets(Gdx.files.internal("tileSheetTestBit.png"), Gdx.files.internal("palBox.bmp"));

        int[] pals = {0x0F000102, 0x101112, 0x202122, 0x303132, 0x040506, 0x141416, 0x242526, 0x343536};
        ppu.setPalettes(pals);

        int[] attrTable = {0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100, 0b11100100111001001110010011100100,};
        ppu.setAttributeTable(attrTable);
    }

    @Override
    public void render() {
        ppu.sendToOAM(0x00000000);
        ppu.render(viewport, new byte[30*32]);
        t++;
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
