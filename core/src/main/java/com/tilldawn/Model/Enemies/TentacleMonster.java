package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Main;
import com.tilldawn.Model.AudioManager;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.loseGameMenu;

public class TentacleMonster extends Enemy {
    private float lastDamageTime = 0;
    private static final float DAMAGE_COOLDOWN = 1.0f;
    private float deathTime;
    private Animation<Texture> deathAnimation;

    public TentacleMonster(float x, float y, float speed, Animation animation) {
        super(x, y, speed, 25, animation); // HP = 25 for SimpleEnemy
        this.deathTime = 0f;
        this.deathAnimation = GameAssetManager.getGameAssetManager().getPlayerDeathAnimation();
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        float dx = playerX - x;
        float dy = playerY - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (isDying) {
            deathTime += delta ;
            if (deathTime >= 0.6f) {
                // Once the death animation is finished, remove the enemy from the game
                isDead = true;
                Game.getEnemies().remove(this);
            }
            return;  // Skip the regular update if the monster is dead
        }

        if (dist > 10f) {
            x += (dx / dist) * speed * delta;
            y += (dy / dist) * speed * delta;
            rect.move(x, y);
        } else {
            if (stateTime - lastDamageTime >= DAMAGE_COOLDOWN) {
                Game.getPlayer().takeDamage();
               // Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() - 1);
                lastDamageTime = stateTime;

                if (Game.getPlayer().getPlayerHealth() < 0) {
                    Game.getPlayer().setDead(true);
                    AudioManager.getInstance().playLoseSound();
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new loseGameMenu());
                }
            }
        }

        stateTime += delta;
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
