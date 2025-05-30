package com.tilldawn.Model.Weapon;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Weapon {

    private WeaponTypes weaponTypes;

    private Texture texture;
    private Sprite sprite;
    private int ammo;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int numberOfShoots;
    private int projectile;
    private float reloadTime;
    private int damage;

    //for ability
    private float powerBoostTimer = 0f;
    private boolean isPowerBoostActive = false;

    public Weapon(WeaponTypes weaponTypes){
        texture = weaponTypes.getGunTexture();
        sprite = new Sprite(texture);
        ammo = weaponTypes.getAmmoMax();
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
        this.weaponTypes = weaponTypes;
        this.numberOfShoots = 0;
        this.projectile = weaponTypes.getProjectile();
        this.reloadTime = weaponTypes.getTimeReload();
        this.damage = weaponTypes.getDamage();
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
        this.projectile = weaponTypes.getProjectile();
        this.reloadTime = weaponTypes.getTimeReload();
        sprite = new Sprite(texture);
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
        this.damage = weaponTypes.getDamage();
        this.texture = weaponTypes.getGunTexture();

    }

    public int getNumberOfShoots() {
        return numberOfShoots;
    }

    public void setNumberOfShoots(int numberOfShoots) {
        this.numberOfShoots = numberOfShoots;
    }

    public int getProjectile() {
        return projectile;
    }

    public void setProjectile(int projectile) {
        this.projectile = projectile;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getPowerBoostTimer() {
        return powerBoostTimer;
    }

    public void setPowerBoostTimer(float powerBoostTimer) {
        this.powerBoostTimer = powerBoostTimer;
    }

    public boolean isPowerBoostActive() {
        return isPowerBoostActive;
    }

    public void setPowerBoostActive(boolean powerBoostActive) {
        isPowerBoostActive = powerBoostActive;
    }
}
