package com.tilldawn.Model;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class DamagePopup {
    private Vector2 position;
    private String text;
    private float timeAlive;
    private float maxTime = 1.0f; // seconds
    private BitmapFont font;
    private Color color;

    public DamagePopup(float x, float y, String text, BitmapFont font) {
        this.position = new Vector2(x, y);
        this.text = text;
        this.font = font;
        this.timeAlive = 0f;
        this.color = new Color(1, 0, 0, 1); // red color, full opacity
    }

    public boolean isFinished() {
        return timeAlive >= maxTime;
    }

    public void update(float delta) {
        timeAlive += delta;
        position.y += delta * 30; // move up 30 units per second
        color.a = 1 - (timeAlive / maxTime); // fade out
    }

    public void render(SpriteBatch batch) {
        font.setColor(color);
        font.draw(batch, text, position.x, position.y);
    }
}
