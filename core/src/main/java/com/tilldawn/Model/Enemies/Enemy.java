package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Model.CollisionRect;

public abstract class Enemy {
    protected float x, y;
    protected float speed;
    protected float stateTime;
    protected Animation<Texture> animation;
    protected CollisionRect rect;
    protected int hp;
    protected boolean isDead;
    protected boolean isDying;

    public Enemy(float x, float y, float speed, int hp, Animation<Texture> animation) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.hp = hp;
        this.animation = animation;
        this.stateTime = 0f;
        this.rect = new CollisionRect(x, y, 50, 50); // adjust size if needed
        this.isDead = false;
        this.isDying = false;
    }

    public abstract void update(float delta, float playerX, float playerY);

    public void takeDamage(int damage) {
        hp -= damage;
        if(hp <= 0) {
            isDying = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDying() {
        return isDying;
    }

    public void setDying(boolean dying) {
        isDying = dying;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public CollisionRect getRect() { return rect; }

    public void render(SpriteBatch batch) {
    }
}
