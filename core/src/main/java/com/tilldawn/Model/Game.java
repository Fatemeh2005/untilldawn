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
    private static float reloadGunTimer = 0;

    private static ArrayList<Tree> trees = new ArrayList<>();

    private static ArrayList<Seed>seeds = new ArrayList<>();

    private static boolean gamePaused = false;

    private static int keyUp ;
    private static int keyDown ;
    private static int keyLeft;
    private static int keyRight;
    private static int reloadGun;
    private static int shoot;

    private static boolean reloadOn = false;
    private static boolean autoReloadOn = false;


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

    public static boolean isGamePaused() {
        return gamePaused;
    }

    public static void setGamePaused(boolean gamePaused) {
        Game.gamePaused = gamePaused;
    }

    public static int getKeyUp() {
        return keyUp;
    }

    public static void setKeyUp(int keyUp) {
        Game.keyUp = keyUp;
    }

    public static int getKeyDown() {
        return keyDown;
    }

    public static void setKeyDown(int keyDown) {
        Game.keyDown = keyDown;
    }

    public static int getKeyLeft() {
        return keyLeft;
    }

    public static void setKeyLeft(int keyLeft) {
        Game.keyLeft = keyLeft;
    }

    public static int getKeyRight() {
        return keyRight;
    }

    public static void setKeyRight(int keyRight) {
        Game.keyRight = keyRight;
    }

    public static int getReloadGun() {
        return reloadGun;
    }

    public static void setReloadGun(int reloadGun) {
        Game.reloadGun = reloadGun;
    }

    public static int getShoot() {
        return shoot;
    }

    public static void setShoot(int shoot) {
        Game.shoot = shoot;
    }

    public static boolean isReloadOn() {
        return reloadOn;
    }

    public static void setReloadOn(boolean reloadOn) {
        Game.reloadOn = reloadOn;
    }

    public static float getReloadGunTimer() {
        return reloadGunTimer;
    }

    public static void setReloadGunTimer(float reloadGunTimer) {
        Game.reloadGunTimer = reloadGunTimer;
    }

    public static boolean isAutoReloadOn() {
        return autoReloadOn;
    }

    public static void setAutoReloadOn(boolean autoReloadOn) {
        Game.autoReloadOn = autoReloadOn;
    }
}
