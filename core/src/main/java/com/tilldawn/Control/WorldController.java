package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.tilldawn.Main;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.GameView;
import com.tilldawn.View.WinGameMenu;
import com.tilldawn.View.loseGameMenu;

import java.util.Random;

public class WorldController {
    private WeaponController weaponController;
    private Texture backgroundTexture;
    private float enemySpawnTimer = 0;
    private float worldWidth = 20000;
    private float worldHeight = 20000;
    private float totalGameTime = 0;
    private boolean victoryHandled = false;
    private Texture heart = new Texture("assets/heart.png");

    private BitmapFont font = new BitmapFont(); // Default font
    private GlyphLayout layout = new GlyphLayout(); // To calculate text width



    public WorldController(GameView view) {
        this.backgroundTexture = new Texture("background.png");
        this.weaponController = new WeaponController(view);
        font.setColor(Color.WHITE);
        font.getData().setScale(1.5f);

    }

    public void update(Camera camera) {
        float delta = Gdx.graphics.getDeltaTime();

        // Track time
        totalGameTime += delta;
        enemySpawnTimer += delta;

        // Update camera
        camera.position.set(
            Game.getPlayer().getPosX(),
            Game.getPlayer().getPosY(),
            0
        );
        camera.update();

        // Spawn i/30 enemies every 3 seconds
        if (enemySpawnTimer >= 3f) {
            int enemiesToSpawn = (int)(totalGameTime / 30f);
            for (int i = 0; i < enemiesToSpawn; i++) {
                spawnEnemy();
            }
            enemySpawnTimer = 0;
        }

        // Update enemies
        float playerX = Game.getPlayer().getPosX();
        float playerY = Game.getPlayer().getPosY();

        for (int i = 0; i < Game.getEnemies().size(); i++) {
            Enemy enemy = Game.getEnemies().get(i);
            enemy.update(delta, playerX, playerY);
            Game.getPlayer().takeDamageIfInRange(enemy, 0.5f, 60f);

            if (isFarOffScreen(enemy.getX(), enemy.getY())) {
                Game.getEnemies().remove(i);
                i--;
            }
        }

        // Update player and weapons
        Game.getPlayer().update(delta);
        weaponController.update();

        Game.setElapsedTimeInSeconds(Game.getElapsedTimeInSeconds() + delta);
        checkVictoryCondition();
    }

    private void checkVictoryCondition() {
        if (victoryHandled) return; // prevent multiple triggers

        float elapsed = Game.getElapsedTimeInSeconds();
        float required = Game.getSelectedGameTimeInMinutes() * 60f;

        if (elapsed >= required) {
            victoryHandled = true;
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new WinGameMenu());

        }
    }

    //Animation<Texture>animation = GameAssetManager.getGameAssetManager().getEyeBat_frames();
    //Animation<Texture>animation = GameAssetManager.getGameAssetManager().getElder_frames();
    private void spawnEnemy() {
        Random rand = new Random();
        float playerX = Game.getPlayer().getPosX();
        float playerY = Game.getPlayer().getPosY();

        // Distance from player for spawning
        float minDistance = 500f;
        float maxDistance = 800f;

        // Get camera
        Camera cam = GameView.getCamera(); // Make sure this is passed or accessible
        float camLeft = cam.position.x - cam.viewportWidth / 2f;
        float camRight = cam.position.x + cam.viewportWidth / 2f;
        float camBottom = cam.position.y - cam.viewportHeight / 2f;
        float camTop = cam.position.y + cam.viewportHeight / 2f;

        float spawnX, spawnY;

        for (int tries = 0; tries < 20; tries++) {
            float angle = rand.nextFloat() * 360f;
            float radians = (float) Math.toRadians(angle);
            float distance = minDistance + rand.nextFloat() * (maxDistance - minDistance);

            spawnX = playerX + (float) Math.cos(radians) * distance;
            spawnY = playerY + (float) Math.sin(radians) * distance;

            // Make sure it's truly offscreen (entirely outside both axes)
            boolean offscreenX = spawnX < camLeft - 50 || spawnX > camRight + 50;
            boolean offscreenY = spawnY < camBottom - 50 || spawnY > camTop + 50;

            if (offscreenX || offscreenY) {
                // ✅ Valid spawn
                Animation<Texture> animation = GameAssetManager.getGameAssetManager().getTentacle_frames();
                float speed = 40 + rand.nextFloat() * 60;
                Game.getEnemies().add(new Enemy(spawnX, spawnY, speed, animation));
                return;
            }
        }

        // Fallback (very rare)
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getTentacle_frames();
        float fallbackX = playerX + maxDistance;
        float fallbackY = playerY;
        float speed = 50f;
        Game.getEnemies().add(new Enemy(fallbackX, fallbackY, speed, animation));
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
        drawPlayerHP(); // ❤️ draw hearts
        drawElapsedTime();      // ⏱️ time (top-right)
    }

    public void dispose() {
        backgroundTexture.dispose();
    }

    private void drawPlayerHP() {
        int hp = Game.getPlayer().getPlayerHealth();
        int maxHp = 5;
        int heartSize = 32;
        int spacing = 8;

        OrthographicCamera cam = GameView.getCamera();
        float camLeft = cam.position.x - cam.viewportWidth / 2;
        float camTop = cam.position.y + cam.viewportHeight / 2;

        float startX = camLeft + 16; // small margin from left
        float startY = camTop - 16 - heartSize; // small margin from top

        for (int i = 0; i < maxHp; i++) {
            if (i < hp) {
                Main.getBatch().draw(heart, startX + i * (heartSize + spacing), startY, heartSize, heartSize);
            }
        }
    }


    private void drawElapsedTime() {
        OrthographicCamera cam = GameView.getCamera();
        float camRight = cam.position.x + cam.viewportWidth / 2;
        float camTop = cam.position.y + cam.viewportHeight / 2;

        int elapsedSeconds = (int) Game.getElapsedTimeInSeconds();
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        seconds = 60 - seconds;
        minutes = Game.getSelectedGameTimeInMinutes() - minutes - 1;

        String timeStr = String.format("%02d:%02d", minutes, seconds);
        layout.setText(font, timeStr);

        float textWidth = layout.width;
        float textHeight = layout.height;

        float x = camRight - textWidth - 16; // 16px margin from right
        float y = camTop - 16; // 16px from top

        font.draw(Main.getBatch(), layout, x, y);
    }

}
