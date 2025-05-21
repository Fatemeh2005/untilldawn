package com.tilldawn.Model.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.GameAssetManager;

import java.util.ArrayList;

public class Weapon {
//    private int xPosition;
//    private int yPosition;
    private final Texture smgTexture = new Texture(GameAssetManager.getGameAssetManager().getSmg());
    private Sprite smgSprite = new Sprite(smgTexture);
    private int ammo = 30;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public Weapon(){
        smgSprite.setX((float) Gdx.graphics.getWidth() / 2 );
        smgSprite.setY((float) Gdx.graphics.getHeight() / 2);
        smgSprite.setSize(50,50);
    }

    public Sprite getSmgSprite() {
        return smgSprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }
}
