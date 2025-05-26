package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tilldawn.Main;
import com.tilldawn.Model.AudioManager;
import com.tilldawn.Model.Enemies.Seed;
import com.tilldawn.Model.Enemies.TentacleMonster;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Weapon.Bullet;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.Weapon.Weapon;
import com.tilldawn.View.GameView;

import java.util.ArrayList;

public class WeaponController {
    private OrthographicCamera camera;

    public WeaponController(GameView view) {
        this.camera = view.getCamera();
    }

    public void update() {
        Game.getPlayer().getWeapon().getSprite().setX(Game.getPlayer().getPosX() + 30);
        Game.getPlayer().getWeapon().getSprite().setY(Game.getPlayer().getPosY() + 30);
        Game.getPlayer().getWeapon().getSprite().draw(Main.getBatch());
        updateBullets();
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = Game.getPlayer().getWeapon().getSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int mouseX, int mouseY) {

        if (Game.getPlayer().getWeapon().getNumberOfShoots() >= Game.getPlayer().getWeapon().getAmmo()) {
            return;
        }

        Vector3 mouseWorld = new Vector3(mouseX, mouseY, 0);
        camera.unproject(mouseWorld);

        Sprite weaponSprite = Game.getPlayer().getWeapon().getSprite();
        float spawnX = weaponSprite.getX() + weaponSprite.getWidth() / 2;
        float spawnY = weaponSprite.getY() + weaponSprite.getHeight() / 2;

        Weapon weapon = Game.getPlayer().getWeapon();
        int projectileCount = weapon.getProjectile();
        System.out.println(projectileCount);

        // Base direction toward mouse
        Vector2 baseDirection = new Vector2(mouseWorld.x - spawnX, mouseWorld.y - spawnY).nor();

        float spreadAngle = 10f; // degrees between each bullet (adjust as needed)
        float baseAngle = baseDirection.angleDeg();

        if (projectileCount == 1) {
            Bullet bullet = new Bullet((int) spawnX, (int) spawnY);
            bullet.setDirection(baseDirection);
            weapon.getBullets().add(bullet);
        }
        else {

            for (int i = 0; i < projectileCount; i++) {

                float angleOffset = (-(projectileCount - 1) / 2f + i) * spreadAngle;
                float finalAngle = baseAngle + angleOffset;

                Vector2 shootDirection = new Vector2(1, 0).setAngleDeg(finalAngle).nor();

                Bullet bullet = new Bullet((int) spawnX, (int) spawnY);
                bullet.setDirection(shootDirection);

                weapon.getBullets().add(bullet);
            }
        }

            AudioManager.getInstance().playShootSound();
            weapon.setNumberOfShoots(weapon.getNumberOfShoots() + 1);

    }



    public void updateBullets() {
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Enemy> enemies = Game.getEnemies();

        for (Bullet bullet : Game.getPlayer().getWeapon().getBullets()) {
            Sprite sprite = bullet.getSprite();
            sprite.draw(Main.getBatch());

            // Use stored direction, not recompute
            Vector2 direction = bullet.getDirection();
            sprite.setX(sprite.getX() + direction.x * 5);
            sprite.setY(sprite.getY() + direction.y * 5);

            // Bullet collision with enemies
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                Texture seedTexture = GameAssetManager.getGameAssetManager().getEyeBatSeedTex();
                if (enemy instanceof TentacleMonster) {
                    seedTexture = GameAssetManager.getGameAssetManager().getTentacleSeedTex();
                }
                if (sprite.getBoundingRectangle().overlaps(enemy.getRect().getRectangle())) {
                    int damage = Game.getPlayer().getWeapon().isPowerBoostActive()? Game.getPlayer().getWeapon().getDamage()*2 : Game.getPlayer().getWeapon().getDamage();
                    enemy.takeDamage(damage);
                    if (enemy.isDying()) {
                        Game.getSeeds().add(new Seed(enemy.getX(), enemy.getY(), seedTexture));
                        Game.getPlayer().setNumberOfKillsInGame(Game.getPlayer().getNumberOfKillsInGame() + 1);

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
