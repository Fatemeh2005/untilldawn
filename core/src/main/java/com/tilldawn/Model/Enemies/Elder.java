package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Model.GameAssetManager;

public class Elder extends Enemy {
    public Elder(float x, float y, float speed) {
        super(x, y, speed, 400, GameAssetManager.getGameAssetManager().getElder_frames());
    }

    @Override
    public void update(float delta, float playerX, float playerY) {

    }
}
