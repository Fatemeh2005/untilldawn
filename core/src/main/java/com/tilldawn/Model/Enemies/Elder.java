package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Main;
import com.tilldawn.Model.AudioManager;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.loseGameMenu;


public class Elder extends Enemy {
    private float lastDamageTime = 0;
    private static final float DAMAGE_COOLDOWN = 1.0f;
    private float deathTime;
    private Animation<Texture> deathAnimation;

    private float dashTimer = 0f;   // Timer to track dash cooldown
    private boolean isDashing = false; // Whether the Elder is currently dashing
    private float dashDuration = 1.0f;  // Dash lasts for 1 second
    private float dashSpeedMultiplier = 2.5f; // Speed multiplier when dashing
    private float dashTimeRemaining = 0f;  // Time remaining for the current dash

    public Elder(float x, float y, float speed, Animation animation) {
        super(x, y, speed, 400, animation);
        this.deathTime = 0f;
        this.deathAnimation = GameAssetManager.getGameAssetManager().getPlayerDeathAnimation();
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        dashTimer += delta;  // Increase the dash timer by delta time

        if (dashTimer >= 5f && !isDashing) {
            isDashing = true;
            dashTimer = 0f;
            dashTimeRemaining = dashDuration;
        }

        if (isDashing) {
            dashTowardsPlayer(playerX, playerY, delta);

            dashTimeRemaining -= delta;
            if (dashTimeRemaining <= 0) {
                isDashing = false;
            }
        }

        // Handle the Elder's death animation
        if (isDying) {
            deathTime += delta;
            if (deathTime >= 0.6f) {

                isDead = true;
                Game.getPlayer().setNumberOfKillsInGame(Game.getPlayer().getNumberOfKillsInGame() + 1);
                Game.getEnemies().remove(this);
            }
            return;  // Skip the regular update if the monster is dead
        }

        stateTime += delta;
    }

    private void dashTowardsPlayer(float playerX, float playerY, float delta) {
        float dx = playerX - x;
        float dy = playerY - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > 10f) {
            // Dash with a speed multiplier
            x += (dx / dist) * speed * dashSpeedMultiplier * delta;
            y += (dy / dist) * speed * dashSpeedMultiplier * delta;
            rect.move(x, y);
        } else {
            if (stateTime - lastDamageTime >= DAMAGE_COOLDOWN) {
                Game.getPlayer().takeDamage();
                lastDamageTime = stateTime;

                if (Game.getPlayer().getPlayerHealth() < 0) {
                    Game.getPlayer().setDead(true);
                    AudioManager.getInstance().playLoseSound();
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new loseGameMenu());
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isDying) {
            Texture deathFrame = deathAnimation.getKeyFrame(deathTime);
            batch.draw(deathFrame, x, y);
        } else {
            // Draw the regular animation for the monster
            Texture currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, x, y);
        }
    }
}
