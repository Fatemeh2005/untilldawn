package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;

import java.util.ArrayList;
import java.util.Random;

public class WorldController {
    private WeaponController weaponController;
    private Texture backgroundTexture;
    private float enemySpawnTimer = 0;
    private float worldWidth = 2000;
    private float worldHeight = 2000;

    public WorldController( ) {
        this.backgroundTexture = new Texture("background.png");
        this.weaponController = new WeaponController();

    }

    public void update(Camera camera) {

        float delta = Gdx.graphics.getDeltaTime();

        // Update camera
        camera.position.set(
            Game.getPlayer().getPosX(),
            Game.getPlayer().getPosY(),
            0
        );
        camera.update();

        // Spawn enemies
        enemySpawnTimer += delta;
        if (enemySpawnTimer >= 3f) {
            spawnEnemy();
            enemySpawnTimer = 0;
        }

        // Update enemies
        float playerX = Game.getPlayer().getPosX();
        float playerY = Game.getPlayer().getPosY();

        for (int i = 0; i < Game.getEnemies().size(); i++) {
            Enemy enemy = Game.getEnemies().get(i);
            enemy.update(delta, playerX, playerY);

            // 💥 Damage player if enemy is close
            Game.getPlayer().takeDamageIfInRange(enemy, 0.5f, 60f); // 60 pixels range, 0.5 damage

            if (isFarOffScreen(enemy.getX(), enemy.getY())) {
                Game.getEnemies().remove(i);
                i--;
            }
        }

        // Update player
        Game.getPlayer().update(delta);
        weaponController.update();
    }


    private void spawnEnemy() {
        Random rand = new Random();
        float spawnX = rand.nextBoolean() ? -64 : Gdx.graphics.getWidth() + 64;
        float spawnY = rand.nextFloat() * Gdx.graphics.getHeight();
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getTentacle_frames();
        //Animation<Texture>animation = GameAssetManager.getGameAssetManager().getEyeBat_frames();
        //Animation<Texture>animation = GameAssetManager.getGameAssetManager().getElder_frames();
        float speed = 40 + rand.nextFloat() * 60;
        Game.getEnemies().add(new Enemy(spawnX, spawnY, speed, animation));
    }

    private boolean isFarOffScreen(float x, float y) {
        float margin = 200;
        return x < -margin || x > worldWidth + margin ||
            y < -margin || y > worldHeight + margin;
    }

    public void render() {
        Main.getBatch().draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        for (Enemy enemy : Game.getEnemies()) {
            enemy.render(Main.getBatch());
        }
    }

    public void dispose() {
        backgroundTexture.dispose();
    }
}
