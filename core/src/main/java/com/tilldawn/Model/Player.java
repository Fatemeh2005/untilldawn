package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Main;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Weapon.Weapon;
import com.tilldawn.Model.Weapon.WeaponTypes;
import com.tilldawn.View.loseGameMenu;

public class Player {

    private Texture playerTexture ;
    private Sprite playerSprite ;
    private PlayerTypes playerType ;
    private int posX;
    private int posY;
    private int playerHealth;
    private CollisionRect rect ;
    private float time = 0;
    private int speed ;
    private Animation<Texture>animations;
    private Weapon weapon = new Weapon(WeaponTypes.SMGDUAL);
    private int level = 1;
    //TODO:check if this is right:
    private int xp = 0;
    private boolean levelUp = false;
    private Animation<Texture> deathAnimation;
    private boolean isDead = false;  // Track if player is dead
    private float deathAnimationTime = 0;

    public void setDeathAnimation() {

        isDead = true;
        deathAnimationTime = 0;  // Reset death animation time to start fresh
    }


    private float lastDamageTime = 0f; // Time since last damage
    private final float damageCooldown = 1f; // 1 second between hits

    private int numberOfKillsInGame = 0;
    private User user;

    public int getSpeed() {
        return speed;
    }

    public Player(PlayerTypes playerType) {
        playerTexture = playerType.getAvatarTexture();
        this.animations = playerType.getAnimation();
        this.playerSprite = new Sprite(playerTexture);

        // Initialize posX and posY to screen center
        this.posX =  Gdx.graphics.getWidth() / 2;
        this.posY =  Gdx.graphics.getHeight() / 2;

        playerSprite.setPosition(posX, posY); // Sync sprite position
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);

        rect = new CollisionRect(posX, posY, playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.playerType = playerType;
        this.speed = playerType.getSpeed();
        this.playerHealth = playerType.getHealth();
        rect = new CollisionRect(posX, posY, playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.deathAnimation = GameAssetManager.getGameAssetManager().getPlayerDeathAnimation();
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }


    public int getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public Animation<Texture> getAnimations() {
        return animations;
    }
    public void setPosX(int posX) {
        this.posX = posX;
        playerSprite.setX(posX); // Keep sprite in sync
        rect.move(posX, posY); // Sync collision box
    }

    public void setPosY(int posY) {
        this.posY = posY;
        playerSprite.setY(posY); // Keep sprite in sync
        rect.move(posX, posY); // Sync collision box
    }

    public void takeDamageIfInRange(Enemy enemy, float damage, float range) {
        float dx = enemy.getX() - posX;
        float dy = enemy.getY() - posY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance < range && time - lastDamageTime > damageCooldown) {
            playerHealth -= damage;
            if (playerHealth < 0) playerHealth = 0;
            lastDamageTime = time;
        }
    }

    public void update(float delta) {
        time += delta;
        if (Game.getPlayer().isDead()) {
            // If the player is dead, update the death animation frame
            deathAnimationTime += delta;  // Increment the time for death animation
            // Set the player sprite to show the death animation frame
            playerSprite.setRegion(deathAnimation.getKeyFrame(deathAnimationTime));

            // Once the death animation finishes, transition to the lose screen
            if (deathAnimation.isAnimationFinished(deathAnimationTime)) {
                Main.getMain().getScreen().dispose(); // Dispose of the current screen
                Main.getMain().setScreen(new loseGameMenu()); // Transition to lose game menu
            }

            return; // Skip further updates (e.g., movement, etc.)
        }
        rect.move(posX, posY);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public PlayerTypes getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerTypes playerType) {
        this.playerType = playerType;
    }

    public void setAnimations(Animation<Texture> animations) {
        this.animations = animations;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        if (xp > level * 20) {
            level++;
            levelUp = true;
            xp = xp - (level - 1) * 20;
        }
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isLevelUp() {
        return levelUp;
    }

    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }

    public int getNumberOfKillsInGame() {
        return numberOfKillsInGame;
    }
    public void setNumberOfKillsInGame(int numberOfKillsInGame) {
        this.numberOfKillsInGame = numberOfKillsInGame;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public Animation<Texture> getDeathAnimation() {
        return deathAnimation;
    }

    public float getDeathAnimationTime() {
        return deathAnimationTime;
    }

    public void setDeathAnimationTime(float deathAnimationTime) {
        this.deathAnimationTime = deathAnimationTime;
    }
}
