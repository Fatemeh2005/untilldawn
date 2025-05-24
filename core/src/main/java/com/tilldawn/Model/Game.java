package com.tilldawn.Model;

import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Enemies.Seed;
import com.tilldawn.Model.Enemies.Tree;

import java.util.ArrayList;

public class Game {
    private static Player player;

    private static ArrayList<Enemy> enemies = new ArrayList<>();

    private static User currentUser;

    private static int selectedGameTimeInMinutes = 0;
    private static float passedTimeInSeconds = 0;

    private static ArrayList<Tree> trees = new ArrayList<>();

    private static ArrayList<Seed>seeds = new ArrayList<>();


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

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Game.currentUser = currentUser;
    }
    public static int getSelectedGameTimeInMinutes() {
        return selectedGameTimeInMinutes;
    }

    public static void setSelectedGameTimeInMinutes(int minutes) {
        selectedGameTimeInMinutes = minutes;
    }

    public static float getElapsedTimeInSeconds() {
        return passedTimeInSeconds;
    }

    public static void setElapsedTimeInSeconds(float seconds) {
        passedTimeInSeconds = seconds;
    }

    public static void addElapsedTime(float delta) {
        passedTimeInSeconds += delta;
    }

    public static ArrayList<Tree> getTrees() {
        return trees;
    }

    public static void setTrees(ArrayList<Tree> trees) {
        Game.trees = trees;
    }

    public static ArrayList<Seed> getSeeds() {
        return seeds;
    }
}
