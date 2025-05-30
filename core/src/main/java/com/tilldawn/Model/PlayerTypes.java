package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Model.Weapon.WeaponTypes;

public enum PlayerTypes {
    SHANA(5, 4, GameAssetManager.getGameAssetManager().getShana_idle0_tex(), GameAssetManager.getGameAssetManager().getShana_idle_frames()),

    DIAMOND(7, 1, GameAssetManager.getGameAssetManager().getDiamond_idle0_tex(), GameAssetManager.getGameAssetManager().getDiamond_idle_frames()),

    SCARLET(4, 5, GameAssetManager.getGameAssetManager().getScarlet_idle0_tex(), GameAssetManager.getGameAssetManager().getScarlet_idle_frames()),

    LILITH(6, 3, GameAssetManager.getGameAssetManager().getLilith_idle0_tex(), GameAssetManager.getGameAssetManager().getLilith_idle_frames()),

    DASHER(3, 7, GameAssetManager.getGameAssetManager().getDasher_idle0_tex(), GameAssetManager.getGameAssetManager().getDasher_idle_frames()),
    ;


    private final int health;

    private final int speed;

    private final Texture avatarTexture;
    private final Animation<Texture> animation;

    PlayerTypes(int health, int speed, Texture avatarTexture, Animation<Texture> animation) {
        this.health = health;
        this.speed = speed;
        this.avatarTexture = avatarTexture;
        this.animation = animation;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public Texture getAvatarTexture() {
        return avatarTexture;
    }

    public Animation<Texture> getAnimation() {
        return animation;
    }

    public static PlayerTypes findplayerTypeByName(String name) {
        System.out.println(name);
        switch (name) {
            case "SHANA":
                return SHANA;
            case "DIAMOND":
                return DIAMOND;
            case "SCARLET":
                return SCARLET;
            case "LILITH":
                return LILITH;
            case "DASHER":
                return DASHER;

        }
        return null;
    }
}
