package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.Model.AudioManager;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.loseGameMenu;

import java.util.ArrayList;

public class EyeBat extends Enemy{

    private float lastDamageTime = 0;
    private static final float DAMAGE_COOLDOWN = 1.0f;
    private ArrayList<EnemyBullet> bullets = new ArrayList<>();
    private float shootTimer = 0f;
    private float deathTime;
    private Animation<Texture> deathAnimation;

    public EyeBat(float x, float y, float speed, Animation<Texture> animation) {
        super(x, y, speed, 50, animation);
        this.deathTime = 0f;
        this.deathAnimation = GameAssetManager.getGameAssetManager().getPlayerDeathAnimation();
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        //TODO:alan serfan mesl tentacle e
        float dx = playerX - x;
        float dy = playerY - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (isDying) {
            deathTime += delta ;
            if (deathTime >= 0.6f) {
                // Once the death animation is finished, remove the enemy from the game
                isDead = true;
                Game.getPlayer().setNumberOfKillsInGame(Game.getPlayer().getNumberOfKillsInGame() + 1);
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
                Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() - 1);
                lastDamageTime = stateTime;

                if (Game.getPlayer().getPlayerHealth() < 0) {
                    Game.getPlayer().setDead(true);
                    AudioManager.getInstance().playLoseSound();
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new loseGameMenu());
                }
            }
        }
        shootTimer += delta;

        if (shootTimer >= 3f) {
            shootTimer = 0f;
            shootAtPlayer();
        }

        // update bullets
        ArrayList<EnemyBullet> toRemove = new ArrayList<>();
        for (EnemyBullet bullet : bullets) {
            Sprite sprite = bullet.getSprite();
            sprite.draw(Main.getBatch());

            // Use stored direction, not recompute
            Vector2 direction = bullet.getDirection();
            sprite.setX(sprite.getX() + direction.x * 5);
            sprite.setY(sprite.getY() + direction.y * 5);

            // Bullet collision with player

            if (sprite.getBoundingRectangle().overlaps(Game.getPlayer().getRect().getRectangle())) {
                Game.getPlayer().takeDamage();
                Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() - 1);

                toRemove.add(bullet);
                break;
            }

        }
        bullets.removeAll(toRemove);

        stateTime += delta;
    }
    private void shootAtPlayer() {
        int startX = (int) x;
        int startY = (int) y;

        float playerX = Game.getPlayer().getPosX() + Game.getPlayer().getPlayerSprite().getWidth() / 2;
        float playerY = Game.getPlayer().getPosY() + Game.getPlayer().getPlayerSprite().getHeight() / 2;

        Vector2 direction = new Vector2(playerX - startX, playerY - startY).nor();

        EnemyBullet bullet = new EnemyBullet(startX, startY);
        bullet.setDirection(direction);  // 🛠️ Set the direction!
        bullets.add(bullet);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isDying()) {
            Texture deathFrame = deathAnimation.getKeyFrame(deathTime);
            batch.draw(deathFrame, x, y);
        } else {
            // Draw the regular animation for the monster
            Texture currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, x, y);
        }
    }

}
