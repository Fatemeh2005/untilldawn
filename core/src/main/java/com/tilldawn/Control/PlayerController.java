package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Main;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.Player;

import java.util.ArrayList;

public class PlayerController {

    public void update() {
        handlePlayerInput();
        Game.getPlayer().getPlayerSprite().setPosition(Game.getPlayer().getPosX() , Game.getPlayer().getPosY());
        Game.getPlayer().getPlayerSprite().draw(Main.getBatch());
    }

public void handlePlayerInput() {
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        Game.getPlayer().setPosY(Game.getPlayer().getPosY() + Game.getPlayer().getSpeed()); // ⬆️ Move up (now correct)
        idleAnimation(Game.getPlayer().getAnimations());
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        Game.getPlayer().setPosY(Game.getPlayer().getPosY() - Game.getPlayer().getSpeed()); // ⬇️ Move down (now correct)
        idleAnimation(Game.getPlayer().getAnimations());
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        Game.getPlayer().setPosX(Game.getPlayer().getPosX() + Game.getPlayer().getSpeed()); // ➡️ Move right (correct)
        idleAnimation(Game.getPlayer().getAnimations());
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        Game.getPlayer().setPosX(Game.getPlayer().getPosX() - Game.getPlayer().getSpeed()); // ⬅️ Move left (correct)
        idleAnimation(Game.getPlayer().getAnimations());
        Game.getPlayer().getPlayerSprite().flip(true, false); // Flip sprite when moving left
    }
}

    public void render(SpriteBatch batch) {
        Game.getPlayer().getPlayerSprite().draw(batch);
    }


    public void idleAnimation(Animation<Texture> animation){

        Game.getPlayer().getPlayerSprite().setRegion(animation.getKeyFrame(Game.getPlayer().getTime()));

        if (!animation.isAnimationFinished(Game.getPlayer().getTime())) {
            Game.getPlayer().setTime(Game.getPlayer().getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            Game.getPlayer().setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }
    public void checkEnemyCollisions() {
        ArrayList<Enemy> enemies = Game.getEnemies();
        Player player = Game.getPlayer();

        for (Enemy enemy : enemies) {
            // Check if enemy rectangle overlaps with player rectangle
            if (enemy.getRect().getRectangle().overlaps(player.getPlayerSprite().getBoundingRectangle())) {
                // Player takes damage (adjust damage value as needed)
                takeDamage(1);

                // Optional: Add knockback effect
//                applyKnockback(player, enemy);

                // Break after first collision to avoid multiple hits in one frame
                break;
            }
        }
    }
    public void takeDamage(int damage) {
        Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() - damage);
        if (Game.getPlayer().getPlayerHealth() <= 0) {
            // Handle player death
            Game.getPlayer().setPlayerHealth(0);
            handleDeath();
        }
    }
    private void handleDeath() {
        // Optional: Play death animation
        float deathTime = Gdx.graphics.getDeltaTime();

        // Example: If you have a death animation
//        if (Game.getPlayer().getDeathAnimation() != null) {
//            Texture frame = Game.getPlayer().getDeathAnimation().getKeyFrame(deathTime);
//            Game.getPlayer().getPlayerSprite().setRegion(frame);
//        }

        // Optional: Restart the game after death animation finishes
        if (deathTime > 3.0f) { // Wait 3 seconds before restarting
           // Game.restartGame(); // You'll need to implement this in your Game class
        }
    }
}
