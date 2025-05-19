package com.tilldawn.Control.EnemyController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;

public class EnemySpawner {
//    private float elapsedTime = 0f;
//    private float spawnTimer = 0f;
//
//    private final Array<Enemy> activeEnemies = new Array<>();
//    private final GameAssetManager assetManager;
//    private final Player player;
//
//    public EnemySpawner(GameAssetManager assetManager, Player player) {
//        this.assetManager = assetManager;
//        this.player = player;
//    }
//
//    public void update(float deltaTime) {
//        elapsedTime += deltaTime;
//        spawnTimer += deltaTime;
//
//        int i = (int) elapsedTime;
//
//        // Every 3 seconds
//        if (spawnTimer >= 3f) {
//            spawnTimer = 0f;
//            int numberToSpawn = i / 30;
//            for (int j = 0; j < numberToSpawn; j++) {
//                spawnRandomEnemy();
//            }
//        }
//
//        // Update enemies
//        for (Enemy enemy : activeEnemies) {
//            enemy.moveTowards(player.getPosX(), player.getPosY(), deltaTime);
//        }
//    }
//
//    private void spawnRandomEnemy() {
//        Enemy enemy = new Enemy(assetManager.getDiamond_idle_frames()); // Choose random animation later
//        float spawnX = MathUtils.random(0, Gdx.graphics.getWidth());
//        float spawnY = MathUtils.random(0, Gdx.graphics.getHeight());
//
//        enemy.setPosition(spawnX, spawnY);
//        activeEnemies.add(enemy);
//    }
//
//    public Array<Enemy> getActiveEnemies() {
//        return activeEnemies;
//    }
}
