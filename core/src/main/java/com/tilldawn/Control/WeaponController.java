package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.Weapon.*;
import com.tilldawn.Model.Player;

import java.util.ArrayList;

public class WeaponController {

    public void update(){
        Game.getPlayer().getWeapon().getSmgSprite().setX(Game.getPlayer().getPosX() + 30);
        Game.getPlayer().getWeapon().getSmgSprite().setY(Game.getPlayer().getPosY() + 30);
        Game.getPlayer().getWeapon().getSmgSprite().draw(Main.getBatch());
        updateBullets();
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = Game.getPlayer().getWeapon().getSmgSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y){
        Game.getPlayer().getWeapon().getBullets().add(new Bullet(x, y));
        Game.getPlayer().getWeapon().setAmmo(Game.getPlayer().getWeapon().getAmmo() - 1);
    }


    public void updateBullets() {
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Enemy> enemies = Game.getEnemies();

        for (Bullet bullet : Game.getPlayer().getWeapon().getBullets()) {
            Sprite sprite = bullet.getSprite();
            sprite.draw(Main.getBatch());

            bullet.getSprite().draw(Main.getBatch());
            Vector2 direction = new Vector2(
                Gdx.graphics.getWidth()/2f - bullet.getX(),
                Gdx.graphics.getHeight()/2f - bullet.getY()
            ).nor();

            bullet.getSprite().setX(bullet.getSprite().getX() - direction.x * 5);
            bullet.getSprite().setY(bullet.getSprite().getY() + direction.y * 5);

            // Bullet collision with enemies
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                if (sprite.getBoundingRectangle().overlaps(enemy.getRect().getRectangle())) {
                    enemy.takeDamage(25); // Deal 25 damage

                    if (enemy.isDead()) {
                        enemies.remove(i);
                        i--;
                    }

                    bulletsToRemove.add(bullet);
                    break;
                }
            }
        }

        Game.getPlayer().getWeapon().getBullets().removeAll(bulletsToRemove);
    }

}
