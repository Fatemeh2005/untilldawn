package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Model.CollisionRect;

public class Enemy {
    private float x, y;
    private float speed;
    private float stateTime;
    private Animation<Texture> animation;
    private CollisionRect rect;

    //TODO:this should change by enemy type
    private int hp = 100;

    public Enemy(float x, float y, float speed, Animation<Texture> animation) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.animation = animation;
        this.stateTime = 0f;

        // Assuming enemy sprite is 32x32 (adjust as needed)
        this.rect = new CollisionRect(x, y, 32, 32);
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
