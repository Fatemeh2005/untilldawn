package com.tilldawn.Model;

import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Enemies.Seed;
import com.tilldawn.Model.Enemies.Tree;
import com.tilldawn.Model.Weapon.WeaponTypes;

import java.io.*;
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

    private static boolean pausePressed = false;

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

    public static boolean isPausePressed() {
        return pausePressed;
    }

    public static void setPausePressed(boolean pausePressed) {
        Game.pausePressed = pausePressed;
    }

    public static void setSeeds(ArrayList<Seed> seeds) {
        Game.seeds = seeds;
    }

    public static void resetGame() {
        // Reset all variables to their initial values using setters
        setPlayer(new Player(PlayerTypes.SHANA));  // Assuming the default state for player is null
        setEnemies(new ArrayList<Enemy>());
        //user is not reseted
        setSelectedGameTimeInMinutes(0);
        setElapsedTimeInSeconds(0f);
        setReloadGunTimer(0f);
        setTrees(new ArrayList<Tree>());
        setSeeds(new ArrayList<Seed>());
        setGamePaused(false);
        setPausePressed(false);
    }

    public static void savePlayerData(Player player, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write player data (only ints and strings)
            writer.write("posX=" + player.getPosX());
            writer.newLine();
            writer.write("posY=" + player.getPosY());
            writer.newLine();
            writer.write("playerHealth=" + player.getPlayerHealth());
            writer.newLine();
            writer.write("level=" + player.getLevel());
            writer.newLine();
            writer.write("xp=" + player.getXp());
            writer.newLine();
            writer.write("numberOfKillsInGame=" + player.getNumberOfKillsInGame());
            writer.newLine();
            writer.write("isDead=" + player.isDead());
            writer.newLine();
            writer.write("chosen time=" + Game.getSelectedGameTimeInMinutes());
            writer.newLine();
            writer.write("passed time=" + Game.getElapsedTimeInSeconds());
            writer.newLine();
            writer.write("number of shoots=" + Game.getPlayer().getWeapon().getNumberOfShoots());
            writer.newLine();
            writer.write("reload gun timer=" + Game.getReloadGunTimer());
            writer.newLine();
            writer.write("gun type=" + Game.getPlayer().getWeapon().getWeaponTypes().name());
            writer.newLine();
            writer.write("player type=" + Game.getPlayer().getPlayerType().name());
            writer.newLine();

            // Add any other data you want to save here
            System.out.println("Player data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Player loadPlayerData(String filename) {
        Player player = new Player(PlayerTypes.SHANA); // Initialize player with default type

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("="); // Split the key-value pair
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    // Set the player attributes based on the key
                    switch (key) {
                        case "posX":
                            player.setPosX(Integer.parseInt(value));
                            break;
                        case "posY":
                            player.setPosY(Integer.parseInt(value));
                            break;
                        case "playerHealth":
                            player.setPlayerHealth(Integer.parseInt(value));
                            break;
                        case "level":
                            player.setLevel(Integer.parseInt(value));
                            break;
                        case "xp":
                            player.setXp(Integer.parseInt(value));
                            break;
                        case "numberOfKillsInGame":
                            player.setNumberOfKillsInGame(Integer.parseInt(value));
                            break;
                        case "isDead":
                            player.setDead(Boolean.parseBoolean(value));
                            break;
                            case "passed time":
                                Game.setElapsedTimeInSeconds(Float.parseFloat(value));
                                break;
                        case "chosen time":
                            Game.setSelectedGameTimeInMinutes(Integer.parseInt(value));
                            break;
                        case "number of shoots":
                            player.getWeapon().setNumberOfShoots(Integer.parseInt(value));
                            break;
                        case "reload gun timer":
                            Game.setReloadGunTimer(Float.parseFloat(value));
                            break;
                        case "gun type":
                            Game.getPlayer().getWeapon().setWeaponTypes(WeaponTypes.findWeaponTypeByName(value));
                            break;

                            case "player type":
                                Game.getPlayer().setPlayerType(PlayerTypes.findplayerTypeByName(value));
                    }
                }
            }
            System.out.println("Player data loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }
}
