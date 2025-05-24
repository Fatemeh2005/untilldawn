package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Model.GameAssetManager;

public class EnemyBullet {
    private Texture texture = GameAssetManager.getGameAssetManager().getEyeBatBulletTex();
    private Sprite sprite = new Sprite(texture);
    private int damage = 5;
    private int x;
    private int y;
    private Vector2 direction;

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public EnemyBullet(int x, int y){
        sprite.setSize(20, 20);
        this.x = x;
        this.y = y;
        sprite.setX(x);  // Use the provided X position
        sprite.setY(y);  // Use the provided Y position
//        sprite.setX((float) Gdx.graphics.getWidth() / 2);
//        sprite.setY((float) Gdx.graphics.getHeight() / 2);
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}



