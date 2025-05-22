package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Main;
import com.tilldawn.Model.CollisionRect;
import com.tilldawn.Model.Game;
import com.tilldawn.View.loseGameMenu;

public class Enemy {
    private float x, y;
    private float speed;
    private float stateTime;
    private Animation<Texture> animation;
    private CollisionRect rect;

    // In Enemy class
    private float lastDamageTime = 0;
    private static final float DAMAGE_COOLDOWN = 1.0f; // 1-second cooldown

    //TODO:this should change by enemy type
    private int hp = 100;

    public Enemy(float x, float y, float speed, Animation<Texture> animation) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.animation = animation;
        this.stateTime = 0f;

        // Assuming enemy sprite is 50*50 (adjust as needed)
        this.rect = new CollisionRect(x, y, 50, 50);
    }

    public void update(float delta, float playerX, float playerY) {
        float dx = playerX - x;
        float dy = playerY - y;
        float dist = (float) Math.sqrt(dx*dx + dy*dy);

        if (dist > 10f) { // Don't move if very close
            x += (dx/dist) * speed * delta;
            y += (dy/dist) * speed * delta;
            rect.move(x, y); // Keep rect in sync
        }
        else{
            if (stateTime - lastDamageTime >= DAMAGE_COOLDOWN) {
                Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() - 1); // Deal 10 damage (adjust as needed)
                lastDamageTime = stateTime; // Reset cooldown

                if (Game.getPlayer().getPlayerHealth() <= 0) {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new loseGameMenu());
                }
            }
        }
        stateTime += delta;
    }

    public void render(SpriteBatch batch) {
        Texture currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y);
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public CollisionRect getRect() { return rect; }

    public void takeDamage(int damage) {
        hp -= damage;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
