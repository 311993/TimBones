package io.github.TimBones;

import com.badlogic.gdx.InputAdapter;
import java.util.HashMap;
import java.util.Map;

public class Keys extends InputAdapter {

    // L|R|U|D|A|B|Start|Select
    private final boolean[] state;
    private Map<Integer, Integer> bindings;

    /**Construct a new key listener.*/
    public Keys(){
        state = new boolean[8];
        bindings = new HashMap<>();

        updateBindings();
    }

    /**Update key bindings based on GameSettings.*/
    public void updateBindings(){
        GameSettings settings = GameSettings.getReference();

        bindings = new HashMap<>();

        for(int i = 0; i < 8; i++){
            for(Integer key : settings.keys.get(GameSettings.keyNames[i])) {
                bindings.put(key, i);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
       state[bindings.get(keycode)] = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        state[bindings.get(keycode)] = false;
        return false;
    }

    //Get key state methods

    public boolean left(){ return state[0];}
    public boolean right(){ return state[1];}
    public boolean up(){ return state[2];}
    public boolean down(){ return state[3];}
    public boolean a(){ return state[4];}
    public boolean b(){ return state[5];}
    public boolean start(){ return state[6];}
    public boolean select(){ return state[7];}

}
