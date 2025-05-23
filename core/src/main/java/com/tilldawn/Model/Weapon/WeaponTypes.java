package com.tilldawn.Model.Weapon;

import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Model.GameAssetManager;

public enum WeaponTypes {
    //TODO:sprite be added here

    REVOLVER(20, 1, 1, 6, GameAssetManager.getGameAssetManager().getRevolverTexture()),

    SHOTGUN(10, 4, 1, 2, GameAssetManager.getGameAssetManager().getShotGunTexture()),

    SMGDUAL(8, 1, 2, 24, GameAssetManager.getGameAssetManager().getSmgTexture()),;


    private final int damage;

    private final int projectile;

    private final int timeReload;

    private final int AmmoMax;

    private final Texture gunTexture;

    WeaponTypes(int damage, int projectile, int timeReload, int ammoMax, Texture gunTexture) {
        this.damage = damage;
        this.projectile = projectile;
        this.timeReload = timeReload;
        AmmoMax = ammoMax;
        this.gunTexture = gunTexture;
    }

    public int getDamage() {
        return damage;
    }

    public int getProjectile() {
        return projectile;
    }

    public int getTimeReload() {
        return timeReload;
    }

    public int getAmmoMax() {
        return AmmoMax;
    }

    public Texture getGunTexture() {
        return gunTexture;
    }
    public static WeaponTypes findWeaponTypeByName(String name) {
        switch (name) {
            case "Revolver":
                return REVOLVER;
            case "Shot Gun":
                return SHOTGUN;
                case "SMGs Dual":
                    return SMGDUAL;

        }
        return null;
    }
}
