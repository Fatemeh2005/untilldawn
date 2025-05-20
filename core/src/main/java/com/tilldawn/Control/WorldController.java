package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Main;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.GameAssetManager;

import java.util.ArrayList;
import java.util.Random;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float enemySpawnTimer = 0;
    private OrthographicCamera camera;
    private float worldWidth = 2000;
    private float worldHeight = 2000;

    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture("background.png");
        this.playerController = playerController;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update() {

        float delta = Gdx.graphics.getDeltaTime();

        // Update camera
        camera.position.set(
            playerController.getPlayer().getPosX(),
            playerController.getPlayer().getPosY(),
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
        float playerX = playerController.getPlayer().getPosX();
        float playerY = playerController.getPlayer().getPosY();

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update(delta, playerX, playerY);

            // 💥 Damage player if enemy is close
            playerController.getPlayer().takeDamageIfInRange(enemy, 0.5f, 60f); // 60 pixels range, 0.5 damage

            if (isFarOffScreen(enemy.getX(), enemy.getY())) {
                enemies.remove(i);
                i--;
            }
        }

        // Update player
        playerController.getPlayer().update(delta);
    }


    private void spawnEnemy() {
        Random rand = new Random();
        float spawnX = rand.nextBoolean() ? -64 : Gdx.graphics.getWidth() + 64;
        float spawnY = rand.nextFloat() * Gdx.graphics.getHeight();
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getTentacle_frames();
        //Animation<Texture>animation = GameAssetManager.getGameAssetManager().getEyeBat_frames();
        //Animation<Texture>animation = GameAssetManager.getGameAssetManager().getElder_frames();
        float speed = 40 + rand.nextFloat() * 60;
        enemies.add(new Enemy(spawnX, spawnY, speed, animation));
    }

    private boolean isFarOffScreen(float x, float y) {
        float margin = 200;
        return x < -margin || x > worldWidth + margin ||
            y < -margin || y > worldHeight + margin;
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    public void dispose() {
        backgroundTexture.dispose();
    }
}
