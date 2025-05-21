package com.tilldawn.Model.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;

public class Bullet {

    private Texture texture;
    private Sprite sprite;
    private Vector2 direction;
    private int damage = 5;

    public Bullet(float startX, float startY, float targetX, float targetY) {
        this.texture = new Texture(GameAssetManager.getGameAssetManager().getBullet());
        this.sprite = new Sprite(texture);
        this.sprite.setSize(20, 20);
        this.sprite.setPosition(startX, startY);

        Vector2 dir = new Vector2(targetX - startX, targetY - startY);
        this.direction = dir.nor();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public int getDamage() {
        return damage;
    }
}

