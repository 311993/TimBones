package io.github.TimBones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class GameSettings implements Json.Serializable{

    private static final int[] defaultKeys = {37, 39, 38, 40, 88, 90, 13, 16};
    public static final String[] keyNames = {"left","right","up","down","a","b","start","select"};

    public Map<String, List<Integer>> keys;

    public double volume;

    public boolean fullscreen;
    public int zoom;
    public double gamma;
    public int colorblind;

    public int iframes;
    public double gamespeed;
    public double health;

    private static GameSettings gameSettings = new GameSettings();

    /**Construct a GameSettings object with the default settings.*/
    private GameSettings(){

        this.keys = new HashMap<>();

        for(int i = 0; i < 8; i++) {
            this.keys.put(keyNames[i], new ArrayList<>());
            this.keys.get(keyNames[i]).add(defaultKeys[i]);
        }
        this.volume = 100.0;

        this.fullscreen= true;
        this.zoom= -1;
        this.gamma= 1.0;
        this.colorblind= 0;

        this.iframes= 15;
        this.gamespeed= 1.0;
        this.health= 1.0;
    }

    /**Get reference to GameSettings singleton.*/
    public static GameSettings getReference(){
        return gameSettings;
    }

    /**Load GameSettings singleton with saved settings.json file.*/
    public static void getSettingsFromJSON(){

        //Read in file
        Json json = new Json();
        GameSettings settings = json.fromJson(GameSettings.class, Gdx.files.internal("data/settings.json"));

        System.out.println(settings.volume);

        //Always have arrow keys do movement
        for(int i = 0; i < 8; i++) {
            if(!settings.keys.get(keyNames[i]).contains(defaultKeys[i])) {
                settings.keys.get(keyNames[i]).add(defaultKeys[i]);
            }
        }

        //Impose setting bounds
        if(settings.volume > 100.0) {
            settings.volume = 100.0;
        }else if(settings.volume < 0.0){
            settings.volume = 0.0;
        }

        if(settings.zoom < -1){
            settings.zoom = -1;
        }

        if(settings.gamma < 1.0){
            settings.gamma = 1.0;
        }

        if(settings.colorblind < 0 || settings.colorblind > 4){
            settings.colorblind = 0;
        }

        if(settings.iframes < 1){
            settings.iframes = 1;
        }

        if(settings.gamespeed < 0.01){
            settings.gamespeed = 0.01;
        }

        if(settings.gamespeed > 5.0){
            settings.gamespeed = 5.0;
        }

        if(settings.health < 0.0){
            settings.health = 0.0;
        }

        //Copy new settings
        for(Field foo : settings.getClass().getFields()){
            try {
                foo.set(gameSettings, foo.get(settings));
            } catch (IllegalAccessException e) {
                //just skip missing
            }
        }
    }

    /** Write a GameSetting object's settings to settings.json file.*/
    public static void sendSettingsToJSON(){

        Json json = new Json();
        File settingsFile = new File(Gdx.files.getLocalStoragePath() + "data/settings.json");

        try {
            settingsFile.createNewFile();

            Gdx.files.local("data/settings.json").writeString(json.prettyPrint(gameSettings), false);
        } catch (IOException e) {
            System.err.println("Writing settings to local file settings.json failed.");
        }
    }

    @Override
    public void write(Json json) {
        json.setOutputType(JsonWriter.OutputType.javascript);

        for(Field foo : this.getClass().getFields()){
            try{
                //Special case for keys map
                if(foo.getName().equals("keys")){

                    json.writeObjectStart("keys");

                    for(String keyName : this.keys.keySet()){
                        json.writeArrayStart(keyName);
                        for(int key : this.keys.get(keyName)){
                            json.writeValue(key);
                        }
                        json.writeArrayEnd();
                    }

                    json.writeObjectEnd();

                }else {
                    json.writeValue(foo.getName(), foo.get(this));
                }
            } catch (IllegalAccessException e) {
                //just skip
            }
        }
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.volume = jsonData.getDouble("volume", 100);
        this.fullscreen = jsonData.getBoolean("fullscreen", true);
        this.zoom = jsonData.getInt("zoom", -1);
        this.gamma = jsonData.getDouble("gamma", 1);
        this.colorblind = jsonData.getInt("colorblind", 0);
        this.iframes = jsonData.getInt("iframes", 15);
        this.gamespeed = jsonData.getDouble("gamespeed", 1);
        this.health = jsonData.getDouble("health", 1);

        this.keys = new HashMap<>();
        for(String keyName : keyNames) {
            this.keys.put(keyName, new ArrayList<>());

            for (int key : jsonData.get("keys").get(keyName).asIntArray()) {
                this.keys.get(keyName).add(key);
            }
        }
    }
}
