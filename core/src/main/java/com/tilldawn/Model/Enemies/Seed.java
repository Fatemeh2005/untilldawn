package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.Model.CollisionRect;

public class Seed {
    private Texture texture;
    private float x, y;
    private CollisionRect collisionRect;

    public Seed(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.collisionRect = new CollisionRect(x, y, texture.getWidth(), texture.getHeight());
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    // Add any other methods you need (e.g., for collection)
}
