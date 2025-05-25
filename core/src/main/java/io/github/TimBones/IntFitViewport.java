package io.github.TimBones;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**Viewport that scales to fit the largest possible integer scaled dimensions while maintaining aspect ratio.*/
public class IntFitViewport extends FitViewport {

    public IntFitViewport(float worldWidth, float worldHeight){
        super(worldWidth, worldHeight);
    }

    public IntFitViewport(float worldWidth, float worldHeight, Camera camera){
        super(worldWidth, worldHeight, camera);
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {

        int ratio = (int)Math.min(screenWidth/super.getWorldWidth(), screenHeight/super.getWorldHeight());

        super.update((int)(ratio*super.getWorldWidth()), (int)(ratio*super.getWorldHeight()), centerCamera);
        super.setScreenX((screenWidth - super.getScreenWidth())/2);
        super.setScreenY((screenHeight - super.getScreenHeight())/2);
    }
}
