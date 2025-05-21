package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tilldawn.Main;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.*;
import com.tilldawn.Model.Weapon.*;
import com.tilldawn.Model.Player;
import com.tilldawn.View.GameView;

import java.util.ArrayList;

public class WeaponController {

    private OrthographicCamera camera;

    public WeaponController(OrthographicCamera camera) {
        this.camera = camera;
    }

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

    public void handleWeaponShoot(int screenX, int screenY) {
        // Convert mouse position to world coordinates
        Vector3 worldMouse = camera.unproject(new Vector3(screenX, screenY, 0));

        // Get player's actual world position
        float playerX = Game.getPlayer().getPosX();
        float playerY = Game.getPlayer().getPosY();

        // Create a bullet from the player's world position to the world mouse position
        Bullet bullet = new Bullet(playerX, playerY, worldMouse.x, worldMouse.y);

        Game.getPlayer().getWeapon().getBullets().add(bullet);
        Game.getPlayer().getWeapon().setAmmo(Game.getPlayer().getWeapon().getAmmo() - 1);
    }




    public void updateBullets() {
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Enemy> enemies = Game.getEnemies();

        for (Bullet bullet : Game.getPlayer().getWeapon().getBullets()) {
            Sprite sprite = bullet.getSprite();
            sprite.draw(Main.getBatch());

            Vector2 dir = bullet.getDirection();
            sprite.setX(sprite.getX() + dir.x * 5);
            sprite.setY(sprite.getY() + dir.y * 5);

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
