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
import com.tilldawn.View.GameView;

import java.util.ArrayList;

public class WeaponController {
    private OrthographicCamera camera;
    public WeaponController(GameView view) {
        this.camera = view.getCamera();
    }

    public void update(){
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
    // Convert screen coordinates to world coordinates
    Vector3 mouseWorld = new Vector3(mouseX, mouseY, 0);

    camera.unproject(mouseWorld);

    // Get weapon position
    Sprite weaponSprite = Game.getPlayer().getWeapon().getSprite();
    float spawnX = weaponSprite.getX() + weaponSprite.getWidth() / 2;
    float spawnY = weaponSprite.getY() + weaponSprite.getHeight() / 2;

    // Create bullet at weapon position
    Bullet bullet = new Bullet((int) spawnX, (int) spawnY);

    // Calculate direction toward mouse (in world coords now!)
    Vector2 direction = new Vector2(
        mouseWorld.x - spawnX,
        mouseWorld.y - spawnY
    ).nor();

    bullet.setDirection(direction);

    Game.getPlayer().getWeapon().getBullets().add(bullet);
    // Play shooting sound
    AudioManager.getInstance().playShootSound();
    Game.getPlayer().getWeapon().setAmmo(Game.getPlayer().getWeapon().getAmmo() - 1);
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
                if(enemy instanceof TentacleMonster){
                    seedTexture = GameAssetManager.getGameAssetManager().getTentacleSeedTex();
                }
                if (sprite.getBoundingRectangle().overlaps(enemy.getRect().getRectangle())) {
                    enemy.takeDamage(Game.getPlayer().getWeapon().getWeaponTypes().getDamage());
                    if (enemy.isDead()) {
                        enemies.remove(i);
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
