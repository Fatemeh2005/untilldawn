package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy {
    private float x, y;
    private float speed;
    private float stateTime;
    private Animation<Texture> animation;

    public Enemy(float x, float y, float speed, Animation<Texture> animation) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.animation = animation;
        this.stateTime = 0f;
    }

    public void update(float delta, float playerX, float playerY) {
        float dx = playerX - x;
        float dy = playerY - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > 0.001f) {
            x += (dx / dist) * speed * delta;
            y += (dy / dist) * speed * delta;
        }

        stateTime += delta;
    }

    public void render(SpriteBatch batch) {
        Texture currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y);
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
