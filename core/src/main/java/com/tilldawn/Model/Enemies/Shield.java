package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tilldawn.Control.WorldController;

public class Shield {
    private float shieldRadius = 1000f; // Initially no shield
    private float shieldShrinkRate = 10f; // Rate at which the shield shrinks per second
    private float shildCenterx;
    private float shildCentery;
    private Texture shieldTexture = new Texture("Elder/shield.png");
    private Sprite shieldSprite = new Sprite(shieldTexture);

    public float getShieldRadius() {
        return shieldRadius;
    }

    public void setShieldRadius(float shieldRadius) {
        this.shieldRadius = shieldRadius;
    }

    public float getShieldShrinkRate() {
        return shieldShrinkRate;
    }

    public void setShieldShrinkRate(float shieldShrinkRate) {
        this.shieldShrinkRate = shieldShrinkRate;
    }

    public float getShildCenterx() {
        return shildCenterx;
    }

    public void setShildCenterx(float shildCenterx) {
        this.shildCenterx = shildCenterx;
    }

    public float getShildCentery() {
        return shildCentery;
    }

    public void setShildCentery(float shildCentery) {
        this.shildCentery = shildCentery;
    }

    public Shield(float shildCenterx, float shildCentery) {
        this.shildCenterx = shildCenterx;
        this.shildCentery = shildCentery;
    }
    public void render(SpriteBatch batch) {
        if (WorldController.isIsShieldActive()) {

            // Update the sprite's size based on the shield radius
            shieldSprite.setSize(shieldRadius*2 ,shieldRadius*2 );  // Set size based on radius (diameter)

            // Position the sprite in the world
            shieldSprite.setPosition(shildCenterx - shieldRadius, shildCentery - shieldRadius);  // Center the sprite

            // Draw the shield
            shieldSprite.draw(batch);
        }
        }
    public void dispose() {
        shieldTexture.dispose();
    }
    }

