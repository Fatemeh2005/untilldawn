package com.tilldawn.Model.Weapon;

public enum WeaponTypes {
    //TODO:sprite be added here

    REVOLVER(20, 1, 1, 6),

    SHOTGUN(10, 4, 1, 2),

    SMGDUAL(8, 1, 2, 24);


    private final int damage;

    private final int projectile;

    private final int timeReload;

    private final int AmmoMax;

    WeaponTypes(int damage, int projectile, int timeReload, int ammoMax) {
        this.damage = damage;
        this.projectile = projectile;
        this.timeReload = timeReload;
        AmmoMax = ammoMax;
    }
}
