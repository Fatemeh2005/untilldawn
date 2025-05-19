package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Main;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.GameAssetManager;

import java.util.ArrayList;
import java.util.Random;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private float backgroundX = 0;
    private float backgroundY = 0;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float enemySpawnTimer = 0;


    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture("background.png");
        this.playerController = playerController;
    }

    public void update() {
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        // Draw background
        backgroundX = playerController.getPlayer().getPosX();
        backgroundY = playerController.getPlayer().getPosY();
        Main.getBatch().draw(backgroundTexture, backgroundX, backgroundY);

        // Spawn enemies every 3 seconds
        enemySpawnTimer += delta;
        if (enemySpawnTimer >= 3f) {
            spawnEnemy();
            enemySpawnTimer = 0;
        }

        float playerX = playerController.getPlayer().getPosX();
        float playerY = playerController.getPlayer().getPosY();

        for (Enemy enemy : enemies) {
            enemy.update(delta, playerX, playerY);
            enemy.render(Main.getBatch());
        }
    }

    //TODO:felan faghat tentacle
    private void spawnEnemy() {
        Random rand = new Random();

        float spawnX = rand.nextBoolean() ? -64 : Gdx.graphics.getWidth() + 64;
        float spawnY = rand.nextFloat() * Gdx.graphics.getHeight();

        Animation<Texture>animation = GameAssetManager.getGameAssetManager().getTentacle_frames();

        float speed = 40 + rand.nextFloat() * 60; // 40–100

        enemies.add(new Enemy(spawnX, spawnY, speed, animation));
    }


}
