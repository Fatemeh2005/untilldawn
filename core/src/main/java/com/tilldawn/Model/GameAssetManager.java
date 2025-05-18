package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    private final String Shana_idle0 = "Shana/Idle_0.png";
    private final String Shana_idle1 = "Shana/Idle_1.png";
    private final String Shana_idle2 = "Shana/Idle_2.png";
    private final String Shana_idle3 = "Shana/Idle_3.png";
    private final String Shana_idle4 = "Shana/Idle_4.png";
    private final String Shana_idle5 = "Shana/Idle_5.png";
    private final Texture Shana_idle0_tex = new Texture(Shana_idle0);
    private final Texture Shana_idle1_tex = new Texture(Shana_idle1);
    private final Texture Shana_idle2_tex = new Texture(Shana_idle2);
    private final Texture Shana_idle3_tex = new Texture(Shana_idle3);
    private final Texture Shana_idle4_tex = new Texture(Shana_idle4);
    private final Texture Shana_idle5_tex = new Texture(Shana_idle5);

    private final String Diamond_idle0 = "Diamond/Idle_0.png";
    private final String Diamond_idle1 = "Diamond/Idle_1.png";
    private final String Diamond_idle2 = "Diamond/Idle_2.png";
    private final String Diamond_idle3 = "Diamond/Idle_3.png";
    private final String Diamond_idle4 = "Diamond/Idle_4.png";
    private final Texture Diamond_idle0_tex = new Texture(Diamond_idle0);
    private final Texture Diamond_idle1_tex = new Texture(Diamond_idle1);
    private final Texture Diamond_idle2_tex = new Texture(Diamond_idle2);
    private final Texture Diamond_idle3_tex = new Texture(Diamond_idle3);
    private final Texture Diamond_idle4_tex = new Texture(Diamond_idle4);
    private final Animation<Texture> Shana_idle_frames = new Animation<>(0.1f, Shana_idle0_tex, Shana_idle1_tex, Shana_idle2_tex, Shana_idle3_tex, Shana_idle4_tex, Shana_idle5_tex);
    private final Animation<Texture> Diamond_idle_frames = new Animation<>(0.1f, Diamond_idle0_tex, Diamond_idle1_tex, Diamond_idle2_tex, Diamond_idle3_tex, Diamond_idle4_tex);

    private final String smg = "smg/SMGStill.png";
    private final Texture smgTexture = new Texture(smg);

    private final String bullet = "bullet.png";


    private GameAssetManager(){

    }

    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Animation<Texture> getShana_idle_animation() {
        return Shana_idle_frames;
    }

    public String getShana_idle0(){
        return Shana_idle0;
    }

    public Texture getSmgTexture(){
        return smgTexture;
    }

    public String getSmg(){
        return smg;
    }

    public String getBullet(){
        return bullet;
    }

    public Animation<Texture> getDiamond_idle_frames() {
        return Diamond_idle_frames;
    }

    public String getDiamond_idle0() {
        return Diamond_idle0;
    }
}
