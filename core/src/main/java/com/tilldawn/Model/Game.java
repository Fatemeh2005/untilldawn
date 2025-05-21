package com.tilldawn.Model;

import com.tilldawn.Model.Enemies.Enemy;

import java.util.ArrayList;

public class Game {
    private static Player player;

    private static ArrayList<Enemy> enemies;

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        Game.player = player;
    }

    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public static void setEnemies(ArrayList<Enemy> enemies) {
        Game.enemies = enemies;
    }
}
