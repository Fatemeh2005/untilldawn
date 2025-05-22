package com.tilldawn.Model;

public enum PlayerTypes {
    SHANA(5, 4, GameAssetManager.getGameAssetManager().getShana_idle0()),

    DIAMOND(7, 1, GameAssetManager.getGameAssetManager().getDiamond_idle0()),

    SCARLET(4, 5, GameAssetManager.getGameAssetManager().getScarlet_idle0()),

    LILITH(6, 3, GameAssetManager.getGameAssetManager().getLilith_idle0()),

    DASHER(3, 7, GameAssetManager.getGameAssetManager().getDasher_idle0());


    private final int health;

    private final int speed;

    private final String avatarImageAddress;

    PlayerTypes(int health, int speed, String avatarImageAddress) {
        this.health = health;
        this.speed = speed;
        this.avatarImageAddress = avatarImageAddress;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public String getAvatarImageAddress() {
        return avatarImageAddress;
    }
    public static PlayerTypes findPlayerTypeWithAddress(String address) {
        switch (address) {
            case "Diamond/Idle_0.png":
                return DIAMOND;
            case "Shana/Idle_0.png":

                return SHANA;
            case "Dasher/Idle_0.png":

                return DASHER;
            case "Scarlet/Idle_0.png":

                return SCARLET;
            case "Lilith/Idle_0.png":

                return LILITH;

        }
        return null;
    }
}
