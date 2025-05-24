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
import com.tilldawn.Model.AudioManager;
import com.tilldawn.Model.Enemies.*;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.GameView;
import com.tilldawn.View.WinGameMenu;
import com.tilldawn.View.loseGameMenu;

import java.util.Iterator;
import java.util.Random;

public class WorldController {
    private WeaponController weaponController;
    private Texture backgroundTexture;
    private float tentacleSpawnTimer = 0;
    private float eyeBatSpawnTimer = 0;
    private float worldWidth = 20000;
    private float worldHeight = 20000;
    private float totalGameTime = 0;
    private boolean victoryHandled = false;
    private Texture heart = new Texture("assets/heart.png");
    GameView view;

    private BitmapFont font = new BitmapFont(); // Default font
    private GlyphLayout layout = new GlyphLayout(); // To calculate text width

    private float treeDamageTimer = 0f;


    public WorldController(GameView view) {
        this.backgroundTexture = new Texture("background.png");
        this.weaponController = new WeaponController(view);
        this.view = view;
        font.setColor(Color.SKY);
        font.getData().setScale(1.5f);

        spawnInitialTrees();
    }

    public void update(Camera camera) {

        if (Game.isGamePaused()){
            return;
        }
        float delta = Gdx.graphics.getDeltaTime();

        // Update player and weapons
        Game.getPlayer().update(delta);
        if(Game.getPlayer().isDead()){
            return;
        }

        // Track time
        totalGameTime += delta;
        tentacleSpawnTimer += delta;
        eyeBatSpawnTimer += delta;
        treeDamageTimer += delta;

        // Update camera
        camera.position.set(
            Game.getPlayer().getPosX(),
            Game.getPlayer().getPosY(),
            0
        );
        camera.update();

        // Spawn i/30 enemies every 3 seconds
        if (tentacleSpawnTimer >= 3f) {
            int enemiesToSpawn = (int) (totalGameTime / 30f);
            for (int i = 0; i < enemiesToSpawn; i++) {
                spawnTentacle();
            }
            tentacleSpawnTimer = 0;
        }

        Iterator<Seed> iterator = Game.getSeeds().iterator();
        while (iterator.hasNext()) {
            Seed seed = iterator.next();
            if (Game.getPlayer().getRect().collidesWith(seed.getCollisionRect())) {
                Game.getPlayer().setXp(Game.getPlayer().getXp() + 3);
                iterator.remove();  // Safe removal using the iterator
            }
        }

        if (treeDamageTimer >= 1f) {
            for (Tree tree : Game.getTrees()) {
                if (Game.getPlayer().getRect().collidesWith(tree.getRect())) {
                    Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() - 1);
                    treeDamageTimer = 0f;
                    if (Game.getPlayer().getPlayerHealth() < 0) {
                        Game.getPlayer().setDeathAnimation();  // Trigger death animation
                        Game.getPlayer().setDead(true);
                        AudioManager.getInstance().playLoseSound();
                        Main.getMain().getScreen().dispose();
                        Main.getMain().setScreen(new loseGameMenu());
                    }
                    // Optional: Add knockback or other effects
                }
            }
        }
        if (eyeBatSpawnTimer >= 10f && Game.getElapsedTimeInSeconds() >= Game.getSelectedGameTimeInMinutes() * 60f / 4f) {
            int enemiesToSpawn = (int) ((4 * Game.getElapsedTimeInSeconds() - Game.getSelectedGameTimeInMinutes() * 60f + 30f) / 30f);
            for (int i = 0; i < enemiesToSpawn; i++) {
                spawnEyeBat();
            }
            eyeBatSpawnTimer = 0;
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


        weaponController.update();

        Game.setElapsedTimeInSeconds(Game.getElapsedTimeInSeconds() + delta);
        checkVictoryCondition();
        if (Game.getPlayer().isLevelUp()) {
            AudioManager.getInstance().playLevelUpSound();
            Game.setGamePaused(true);  // Pause the game
           view.printAbilitiesMenu(GameAssetManager.getGameAssetManager().getSkin());
        }
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

    private void spawnTentacle() {
        Random rand = new Random();

        // Camera boundaries
        Camera cam = GameView.getCamera();
        float camLeft = cam.position.x - cam.viewportWidth / 2f;
        float camRight = cam.position.x + cam.viewportWidth / 2f;
        float camBottom = cam.position.y - cam.viewportHeight / 2f;
        float camTop = cam.position.y + cam.viewportHeight / 2f;

        float spawnX = 0, spawnY = 0;

        // Choose a random direction to spawn from: 0 = left, 1 = right, 2 = top, 3 = bottom
        int dir = rand.nextInt(4);

        switch (dir) {
            case 0: // Left
                spawnX = camLeft - 100;
                spawnY = camBottom + rand.nextFloat() * cam.viewportHeight;
                break;
            case 1: // Right
                spawnX = camRight + 100;
                spawnY = camBottom + rand.nextFloat() * cam.viewportHeight;
                break;
            case 2: // Top
                spawnX = camLeft + rand.nextFloat() * cam.viewportWidth;
                spawnY = camTop + 100;
                break;
            case 3: // Bottom
                spawnX = camLeft + rand.nextFloat() * cam.viewportWidth;
                spawnY = camBottom - 100;
                break;
        }

        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getTentacle_frames();
        float speed = 40 + rand.nextFloat() * 60;
        Game.getEnemies().add(new TentacleMonster(spawnX, spawnY, speed, animation));
    }

    private void spawnEyeBat() {
        Random rand = new Random();

        // Camera boundaries
        Camera cam = GameView.getCamera();
        float camLeft = cam.position.x - cam.viewportWidth / 2f;
        float camRight = cam.position.x + cam.viewportWidth / 2f;
        float camBottom = cam.position.y - cam.viewportHeight / 2f;
        float camTop = cam.position.y + cam.viewportHeight / 2f;

        float spawnX = 0, spawnY = 0;

        // Choose a random direction to spawn from: 0 = left, 1 = right, 2 = top, 3 = bottom
        int dir = rand.nextInt(4);

        switch (dir) {
            case 0: // Left
                spawnX = camLeft - 100;
                spawnY = camBottom + rand.nextFloat() * cam.viewportHeight;
                break;
            case 1: // Right
                spawnX = camRight + 100;
                spawnY = camBottom + rand.nextFloat() * cam.viewportHeight;
                break;
            case 2: // Top
                spawnX = camLeft + rand.nextFloat() * cam.viewportWidth;
                spawnY = camTop + 100;
                break;
            case 3: // Bottom
                spawnX = camLeft + rand.nextFloat() * cam.viewportWidth;
                spawnY = camBottom - 100;
                break;
        }

        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getEyeBat_frames();
        float speed = 40 + rand.nextFloat() * 60;
        Game.getEnemies().add(new EyeBat(spawnX, spawnY, speed, animation));
    }


    private boolean isFarOffScreen(float x, float y) {
        float margin = 200;
        return x < -margin || x > worldWidth + margin ||
            y < -margin || y > worldHeight + margin;
    }

    public void render() {
        Main.getBatch().draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        // Draw trees first (background objects)
        for (Tree tree : Game.getTrees()) {
            tree.render(Main.getBatch());
        }

        for (Enemy enemy : Game.getEnemies()) {
            enemy.render(Main.getBatch());
        }
        drawPlayerHP(); //draw hearts
        drawElapsedTime();      //time (top-right)
        drawLevel();      // level
        drawNumberOfKills();
        // Render seeds
        for (Seed seed : Game.getSeeds()) {
            seed.render(Main.getBatch());
        }
    }

    public void dispose() {
        backgroundTexture.dispose();
        GameAssetManager.getGameAssetManager().getTree_tex().dispose();
        GameAssetManager.getGameAssetManager().getEyeBatBulletTex().dispose();
        GameAssetManager.getGameAssetManager().getTentacleSeedTex().dispose();
    }

    private void drawPlayerHP() {
        int hp = Game.getPlayer().getPlayerHealth();
        int heartSize = 32;
        int spacing = 8;

        OrthographicCamera cam = GameView.getCamera();
        float camLeft = cam.position.x - cam.viewportWidth / 2;
        float camTop = cam.position.y + cam.viewportHeight / 2;

        float startX = camLeft + 16; // small margin from left
        float startY = camTop - 16 - heartSize; // small margin from top

        for (int i = 0; i < hp; i++) {
            Main.getBatch().draw(heart, startX + i * (heartSize + spacing), startY, heartSize, heartSize);
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
        font.getData().setScale(2.5f);
        layout.setText(font, timeStr);

        float textWidth = layout.width;

        float x = camRight - textWidth - 16; // 16px margin from right
        float y = camTop - 16; // 16px from top

        font.draw(Main.getBatch(), layout, x, y);
    }

    private void drawLevel() {
        OrthographicCamera cam = GameView.getCamera();
        float camRight = cam.position.x + cam.viewportWidth / 2;
        float camTop = cam.position.y + cam.viewportHeight / 2;

        String timeStr = String.format("Level : %d", Game.getPlayer().getLevel());
        font.getData().setScale(2.5f);
        layout.setText(font, timeStr);

        float textWidth = layout.width;

        float x = camRight - textWidth - 16; // 16px margin from right
        float y = camTop - 55;

        font.draw(Main.getBatch(), layout, x, y);
    }

    private void drawNumberOfKills() {
        OrthographicCamera cam = GameView.getCamera();
        float camRight = cam.position.x + cam.viewportWidth / 2;
        float camTop = cam.position.y + cam.viewportHeight / 2;

        String timeStr = String.format("Kills : %d", Game.getPlayer().getNumberOfKillsInGame());
        font.getData().setScale(2.5f);
        layout.setText(font, timeStr);

        float textWidth = layout.width;

        float x = camRight - textWidth - 16; // 16px margin from right
        float y = camTop - 95;

        font.draw(Main.getBatch(), layout, x, y);
    }

    private void spawnInitialTrees() {
        Random rand = new Random();
        //always between 25 and 35 trees
        for (int i = 0; i < rand.nextInt(11) + 25; i++) {
            float x = rand.nextFloat() * worldWidth;
            float y = rand.nextFloat() * worldHeight;
            Game.getTrees().add(new Tree(x, y, GameAssetManager.getGameAssetManager().getTree_tex()));
        }
    }

}
