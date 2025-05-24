package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Model.CollisionRect;

public class Tree {
    private float x, y;

    private CollisionRect rect;

    private Texture texture;

    private Sprite sprite;

    public Tree(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.rect = new CollisionRect(x, y, texture.getWidth(), texture.getHeight());
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public CollisionRect getRect() {
        return rect;
    }
}
