package io.github.TimBones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

/**Singleton class controlling the rendering of textures (tiles + sprites) to the viewport.*/
public class PPU {

    //Singleton PPU instance
    private static final PPU ppu = new PPU();

    //NES system palette
    private static final Color[] nesPal = {
        new Color(0x5F5F5FFF),	new Color(0x0F00BFFF),	new Color(0x001F97FF),	new Color(0x4300A7FF),	new Color(0x7F0077FF),	new Color(0x8F0027FF),	new Color(0x8F0700FF),	new Color(0x772700FF),	new Color(0x473F00FF),	new Color(0x004700FF),	new Color(0x004F0FFF),	new Color(0x00472FFF),	new Color(0x004767FF),	new Color(0x000000FF),	new Color(0x000000FF),	new Color(0x000000FF),
        new Color(0xAFAFAFFF),	new Color(0x0057F7FF),	new Color(0x1F3FFFFF),	new Color(0x671FF7FF),	new Color(0xAF07CFFF),	new Color(0xD70057FF),	new Color(0xDF1700FF),	new Color(0xBF4700FF),	new Color(0x876700FF),	new Color(0x178700FF),	new Color(0x009717FF),	new Color(0x008757FF),	new Color(0x007FA7FF),	new Color(0x171717FF),	new Color(0x000000FF),	new Color(0x000000FF),
        new Color(0xEFEFEFFF),	new Color(0x279FFFFF),	new Color(0x4387FFFF),	new Color(0x876FFFFF),	new Color(0xC75FFFFF),	new Color(0xFF579FFF),	new Color(0xFF6F4FFF),	new Color(0xFF8700FF),	new Color(0xCFA700FF),	new Color(0x6FC700FF),	new Color(0x1FCF2FFF),	new Color(0x17CF97FF),	new Color(0x00BFE7FF),	new Color(0x474747FF),	new Color(0x000000FF),	new Color(0x000000FF),
        new Color(0xFFFFFFFF),	new Color(0xAFCFFFFF),	new Color(0xB7B7FFFF),	new Color(0xC7AFFFFF),	new Color(0xE7A7FFFF),	new Color(0xF7B7E7FF),	new Color(0xFFC7CFFF),	new Color(0xFFCF97FF),	new Color(0xF7E797FF),	new Color(0xD7F78FFF),	new Color(0xAFFF97FF),	new Color(0x9FF7CFFF),	new Color(0x87E7F7FF),	new Color(0x8F8F8FFF),	new Color(0x000000FF),	new Color(0x000000FF),
    };

    //Fields
    private final SpriteBatch spriteBatch, tileBatch;
    private final ShaderProgram spriteShader;
    private final ShaderProgram tileShader;
    private Texture spriteSheet, tileSheet;

    private final float[] palette;
    private int[] attributeTable;

    private final int[] OAM;
    private byte OAMindex;

    //Shader uniform locations
    private final int u_palette;
    private final int u_attributeTable;
    private final int u_spritePal;
    private final int u_hScroll;
    private final int u_vScroll;

    /**Private constructor to prevent instantiation.*/
    private PPU(){

        //Set fields to initially clear/zero
        spriteBatch = new SpriteBatch(64);
        tileBatch = new SpriteBatch(28*32);

        tileSheet = null;
        spriteSheet = null;

        palette = new float[96];
        attributeTable = new int[17]; //actual NES attribute table is TL-TR-BL-BR per byte; each int in this array is attribute table for one col

        OAM = new int[64];
        OAMindex = 0;

        //Load shaders
        ShaderProgram.pedantic = false;

        tileShader = new ShaderProgram(Gdx.files.internal("shaders/commonVertexShader.glsl"),Gdx.files.internal("shaders/tileFragmentShader.glsl"));
        tileBatch.setShader(tileShader);

        spriteShader = new ShaderProgram(Gdx.files.internal("shaders/commonVertexShader.glsl"),Gdx.files.internal("shaders/spriteFragmentShader.glsl"));
        spriteBatch.setShader(spriteShader);

        //Locate shader uniforms
        tileShader.bind();
        u_attributeTable = tileShader.getUniformLocation("attributeTable[0]");
        u_palette = tileShader.getUniformLocation("palette[0]");
        u_hScroll = tileShader.getUniformLocation("hScroll");
        u_vScroll = tileShader.getUniformLocation("vScroll");

        spriteShader.bind();
        u_spritePal = spriteShader.getUniformLocation("spritePal[0]");
    }

    /**Return reference to PPU singleton instance.*/
    public static PPU getReference(){
        return ppu;
    }

    /**Load tile + sprite sheets. Must set both sheets before calling render().*/
    public void loadSheets(Texture tileSheet, Texture spriteSheet){
        assert tileSheet != null;
        assert spriteSheet != null;

        if(this.tileSheet != null){
            this.tileSheet.dispose();
        }
       this.tileSheet = tileSheet;

        if(this.spriteSheet != null){
            this.spriteSheet.dispose();
        }
        this.spriteSheet = spriteSheet;
    }

    /** Releases all resources of this object. */
    public void dispose(){
        tileSheet.dispose();
        spriteSheet.dispose();

        tileShader.dispose();
        spriteShader.dispose();

        tileBatch.dispose();
        spriteBatch.dispose();
    }

    /**
     * Set tile attribute table. Each int in the array represents the palette attributes for one 256x16 pixel column, with the highest 2 bits representing the first 16x16 metatile, etc.
     * The array must have length 17.
     * */
    public void setAttributeTable(int[] attributeTable){
        assert attributeTable.length == 17 : "Parameter attributeTable must have length 17.";
        this.attributeTable = attributeTable;
    }

    /**
     * Set palette #{@code index} to the colors indicated by {@code palette}. {@code index} should be 0-7.
     * {@code palette} = Color 0 | Color 1 | Color 2 | Color 3. (e.g. Color 0 is highest 4 bits).
     * Color 0 will be set only for index 0, which will set color 0 for palettes 0-3. Color 0 is always transparent for palettes 4-7.
     * */
    public void setPalette(int index, int palette){

        //Update chosen palette
        Color c;
        for(int i = 1; i < 4; i++) {

            c = nesPal[(palette & 0xFF << 8*(3-i)) >> 8*(3-i)];
            this.palette[index * 12 + i*3]     = c.r;
            this.palette[index * 12 + i*3 + 1] = c.g;
            this.palette[index * 12 + i*3 + 2] = c.b;
        }

        //Set backdrop color if palette 0 is chosen
        if(index == 0) {
            for (int i = 0; i < 4; i++) {
                c = nesPal[(palette & 0xFF << 24) >> 24];
                this.palette[i*12] = c.r;
                this.palette[i*12 + 1] = c.g;
                this.palette[i*12 + 2] = c.b;
            }
        }
    }

    /**
     * Set all palettes to the colors indicated by {@code palettes}, which should be of length 8.
     * See setPalette for encoding information.
     * */
    public void setPalettes(int[] palettes){
        for(int i = 0; i < 8; i++){
           setPalette(i, palettes[i]);
        }
    }

    /**Render tiles from byte array and sprites from OAM to viewport.
     * tiles byte array should 32 x 34 and hold tile sheet indices of tiles to be rendered.*/
    public void render(Viewport viewport, byte[][] tiles, int hScroll, int vScroll, int startScroll, int endScroll){

        ScreenUtils.clear(0f, 0f, 0f, 1f);
        viewport.apply();

        renderTiles(viewport, tiles, hScroll, vScroll, startScroll, endScroll);

        renderSprites(viewport);
    }

    /**
     * Add sprite data to OAM if there is room left. Return number of sprites written, which is 64 if full.
     * spriteData should be of the form: 000 | flipV | flipH | invisible | palette (2) | ID (8) | X Pos (8) | Y Pos (8).
     * Sprites must be rewritten to OAM every frame.
     * */
    public int sendToOAM(int spriteData){
        if(OAMindex < 64){
            OAM[OAMindex] = spriteData;
            OAMindex++;
        }
        return OAMindex;
    }

    /**Render tiles to viewport.*/
    private void renderTiles(Viewport viewport, byte[][] tiles, int hScroll, int vScroll, int startScroll, int endScroll) {

        //Set up rendering
        tileBatch.setProjectionMatrix(viewport.getCamera().combined);
        tileBatch.begin();
        tileBatch.disableBlending();

        //Send uniforms
        tileShader.setUniform3fv(u_palette, palette, 0, palette.length);

        for(int i = 0; i < 17; i++) {
            tileShader.setUniformi(u_attributeTable + i, attributeTable[i]);
        }

        vScroll %= 8;
        hScroll %= 8;

        //Draw tiles
        int row_hScroll;
        int row_vScroll;
        for(int j = 0; j < 32; j++){

            if(j >= startScroll && j <= endScroll) {
                row_hScroll = hScroll;
                row_vScroll = vScroll;
            }else{
                row_hScroll = 0;
                row_vScroll = 0;
            }

            tileShader.setUniformi(u_hScroll, row_hScroll);
            tileShader.setUniformi(u_vScroll, row_vScroll);

            for(int i = 0; i < 34; i++){

                //Get tile id
//                int yCoord = (j + vOffset) % 32;
//                yCoord += (yCoord >= 0) ? 0 : 32;
//
//                int xCoord = (i + hOffset) % 34;
//                xCoord += (xCoord >= 0) ? 0 : 34;

                int id = tiles[j][i];
                id = (id >= 0) ? id : id + 256;

                tileBatch.draw(tileSheet, i*8f + row_hScroll - 8, 232 - j*8f - row_vScroll + 8, 8f, 8f, (id % 16)*8, (id / 16)*8, 8, 8, false, false);
            }
        }

        tileBatch.end();
    }

    /**Render sprites to viewport. Clears OAM.*/
    private void renderSprites(Viewport viewport) {
        //Set up rendering
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        //Draw until OAM empty
        while(OAMindex > 0){
            OAMindex--;

            int spriteData = OAM[OAMindex];

            //Draw only if visible
            if((spriteData & 0x04000000) == 0) {
                //Send palette uniform
                spriteShader.setUniform3fv(u_spritePal, palette, 48 + 12 * ((spriteData & 0x03000000) >> 24), 12);

                //Decode spriteData + draw
                int id = (spriteData & 0xFF0000) >> 16;

                spriteBatch.draw(
                    (id & 1) == 0 ? spriteSheet : tileSheet,
                    (spriteData & 0xFF00) >> 8,
                    spriteData & 0xFF,
                    8, 16,
                    ((id >> 1) & 0x0F) * 8,
                    (id >> 5) * 8,
                    8, 16,
                    (spriteData & 0x08000000) > 0, (spriteData & 0x10000000) > 0
                );
            }
        }

        spriteBatch.end();
    }

}
