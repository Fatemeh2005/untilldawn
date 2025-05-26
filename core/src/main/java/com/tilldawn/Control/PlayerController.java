package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.tilldawn.Main;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.Player;
import com.tilldawn.View.loseGameMenu;

public class PlayerController {

    // Constants for background dimensions
    private final float WORLD_WIDTH = 20000;
    private final float WORLD_HEIGHT = 20000;

    // Also define your player's sprite dimensions
    private final float PLAYER_WIDTH = Game.getPlayer().getPlayerSprite().getWidth();
    private final float PLAYER_HEIGHT = Game.getPlayer().getPlayerSprite().getHeight();
    GameController controller;

    private float lastShootTime = 0f;  // Time of the last shot
    private float shootCooldown = 0.2f;  // Cooldown time between shots (in seconds)

    public PlayerController(GameController controller) {
        this.controller = controller;
    }

    public void update() {
        // If player is dead, do not allow any further movement or idle animation

        handlePlayerInput();  // Handle player input for movement
        Game.getPlayer().getPlayerSprite().setPosition(Game.getPlayer().getPosX(), Game.getPlayer().getPosY());
        Game.getPlayer().getPlayerSprite().draw(Main.getBatch());
    }

    public void handlePlayerInput() {
        // Ensure that movement only happens if the player is alive
        if (Game.getPlayer().isDead()) {
            return;  // Skip handling input if the player is dead
        }

        int speed = Game.getPlayer().isSpeedBoostActive() ? Game.getPlayer().getSpeed()*2 : Game.getPlayer().getSpeed();
        // Get the mouse position
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        int newX = Game.getPlayer().getPosX();
        int newY = Game.getPlayer().getPosY();

        if (Gdx.input.isKeyPressed(Game.getKeyUp())) {
            newY += speed;
            idleAnimation(Game.getPlayer().getAnimations());
        }
        if (Gdx.input.isKeyPressed(Game.getKeyDown())) {
            newY -= speed;
            idleAnimation(Game.getPlayer().getAnimations());
        }
        if (Gdx.input.isKeyPressed(Game.getKeyRight())) {
            newX += speed;
            idleAnimation(Game.getPlayer().getAnimations());
        }
        if (Gdx.input.isKeyPressed(Game.getKeyLeft())) {
            newX -= speed;
            idleAnimation(Game.getPlayer().getAnimations());
            Game.getPlayer().getPlayerSprite().flip(true, false);
        }

        //reload
        if (Gdx.input.isKeyPressed(Game.getReloadGun())) {
            Game.setReloadOn(true);
            return;
        }

        if ((Gdx.input.isButtonPressed(Game.getShoot()) || Gdx.input.isKeyJustPressed(Game.getShoot())) &&
            (TimeUtils.nanoTime() - lastShootTime) / 1000000000f > shootCooldown) {
            // Handle shooting with the current mouse coordinates
            controller.getWeaponController().handleWeaponShoot(mouseX, mouseY);
            lastShootTime = TimeUtils.nanoTime();  // Update last shoot time
            return;  // Prevent other code from running while shooting
        }

        // Cheat codes, etc.
        if (Gdx.input.isKeyJustPressed(Input.Keys.H) && Game.getPlayer().getPlayerHealth() == 0) {
            Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() + 1);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            Game.setElapsedTimeInSeconds(Game.getElapsedTimeInSeconds() + 60f);
            return;
        }

        // Eternity cheat code
        if (Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
            Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() + 5);
            return;
        }

        //xp cheat code
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            Game.getPlayer().setXp(Game.getPlayer().getXp() + 3);
            return;
        }

        // Clamp new position to stay within background/world
        newX = (int) MathUtils.clamp(newX, 0, WORLD_WIDTH - PLAYER_WIDTH);
        newY = (int) MathUtils.clamp(newY, 0, WORLD_HEIGHT - PLAYER_HEIGHT);

        Game.getPlayer().setPosX(newX);
        Game.getPlayer().setPosY(newY);
    }

    public void render(SpriteBatch batch) {
        // Render player sprite if not dead
        if (!Game.getPlayer().isDead()) {
            Game.getPlayer().getPlayerSprite().draw(batch);
        }
    }

    public void idleAnimation(Animation<Texture> animation) {
        // Only show idle animation if the player is alive

        Game.getPlayer().getPlayerSprite().setRegion(animation.getKeyFrame(Game.getPlayer().getTime()));

        if (!animation.isAnimationFinished(Game.getPlayer().getTime())) {
            Game.getPlayer().setTime(Game.getPlayer().getTime() + Gdx.graphics.getDeltaTime());
        } else {
            Game.getPlayer().setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

}
