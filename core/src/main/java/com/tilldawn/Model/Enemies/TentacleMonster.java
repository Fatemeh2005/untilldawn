package com.tilldawn.Model.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;
import com.tilldawn.View.loseGameMenu;

public class TentacleMonster extends Enemy {
    private float lastDamageTime = 0;
    private static final float DAMAGE_COOLDOWN = 1.0f;

    public TentacleMonster(float x, float y, float speed, Animation animation) {
        super(x, y, speed, 25, animation); // HP = 25 for SimpleEnemy
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        float dx = playerX - x;
        float dy = playerY - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > 10f) {
            x += (dx / dist) * speed * delta;
            y += (dy / dist) * speed * delta;
            rect.move(x, y);
        } else {
            if (stateTime - lastDamageTime >= DAMAGE_COOLDOWN) {
                Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() - 1);
                lastDamageTime = stateTime;

                if (Game.getPlayer().getPlayerHealth() <= 0) {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new loseGameMenu());
                }
            }
        }

        stateTime += delta;
    }
}
