package com.tilldawn.Model.Weapon;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Weapon {

    private WeaponTypes weaponTypes;

    private final Texture texture;
    private Sprite sprite;
    private int ammo;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int numberOfShoots;

    public Weapon(WeaponTypes weaponTypes){
        texture = weaponTypes.getGunTexture();
        sprite = new Sprite(texture);
        ammo = weaponTypes.getAmmoMax();
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
        this.weaponTypes = weaponTypes;
        this.numberOfShoots = 0;
    }

    public Sprite getSprite() {
        return sprite;
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

    public WeaponTypes getWeaponTypes() {
        return weaponTypes;
    }

    public void setWeaponTypes(WeaponTypes weaponTypes) {
        this.weaponTypes = weaponTypes;
    }

    public int getNumberOfShoots() {
        return numberOfShoots;
    }

    public void setNumberOfShoots(int numberOfShoots) {
        this.numberOfShoots = numberOfShoots;
    }
}
