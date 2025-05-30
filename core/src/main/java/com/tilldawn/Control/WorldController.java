package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;
import com.tilldawn.Model.AudioManager;
import com.tilldawn.Model.DamagePopup;
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

    private Sprite lightSprite;
    private ShapeRenderer shapeRenderer= new ShapeRenderer();
    ProgressBar xpProgressBar;
    private boolean hasElderEntered = false;
    Shield shield;
    private static boolean isShieldActive = false;


    public WorldController(GameView view) {
        this.backgroundTexture = new Texture("background.png");
        this.weaponController = new WeaponController(view);
        this.view = view;
        font.setColor(Color.SKY);
        font.getData().setScale(1.5f);
        Texture lightTexture = new Texture(Gdx.files.internal("light.png"));
        lightTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        lightSprite = new Sprite(lightTexture);
        float lightRadius = 150f;
        lightSprite.setSize(lightRadius * 2, lightRadius * 2);
        lightSprite.setOriginCenter();
        initializeXPProgressBar(GameAssetManager.getGameAssetManager().getSkin());

        spawnInitialTrees();
    }

    public void update(Camera camera) {

        if (Game.isGamePaused() || Game.isPausePressed()){
            return;
        }
        float delta = Gdx.graphics.getDeltaTime();

        // Update player and weapons
        Game.getPlayer().update(delta);
        if(Game.getPlayer().isDead()){
            return;
        }


        if (Game.getPlayer().isSpeedBoostActive()) {
            Game.getPlayer().setSpeedBoostTimer(Game.getPlayer().getSpeedBoostTimer() + delta);

            // Reset the speed boost after the duration ends
            if (Game.getPlayer().getSpeedBoostTimer() >= 10f) {
                Game.getPlayer().setSpeedBoostActive(false);
                Game.getPlayer().setSpeedBoostTimer(0);
            }
        }

        if (Game.getPlayer().getWeapon().isPowerBoostActive()) {
            Game.getPlayer().getWeapon().setPowerBoostTimer(Game.getPlayer().getWeapon().getPowerBoostTimer() + delta);

            // Reset the speed boost after the duration ends
            if (Game.getPlayer().getWeapon().getPowerBoostTimer() >= 10f) {
                Game.getPlayer().getWeapon().setPowerBoostActive(false);
                Game.getPlayer().getWeapon().setPowerBoostTimer(0);
            }
        }

        // Track time
        totalGameTime += delta;
        tentacleSpawnTimer += delta;
        eyeBatSpawnTimer += delta;
        treeDamageTimer += delta;
        if(Game.isAutoReloadOn() && Game.getPlayer().getWeapon().getNumberOfShoots() == Game.getPlayer().getWeapon().getAmmo()){
            Game.setReloadOn(true);
        }
        if(Game.isReloadOn()){
            Game.setReloadGunTimer(Game.getReloadGunTimer() + delta);
        }

        if(Game.getReloadGunTimer() >= Game.getPlayer().getWeapon().getReloadTime() && Game.isReloadOn()){
            Game.getPlayer().getWeapon().setNumberOfShoots(0);
            Game.setReloadGunTimer(0);
            Game.setReloadOn(false);
        }

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
                    Game.getPlayer().takeDamage();
                    Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() - 1);
                    treeDamageTimer = 0f;
                    if (Game.getPlayer().getPlayerHealth() < 0) {
                        // Trigger death animation
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

        if (Game.getElapsedTimeInSeconds() >= Game.getSelectedGameTimeInMinutes() * 60f / 2f && !hasElderEntered) {
                spawnElder();
                hasElderEntered = true;
        }
        if (isShieldActive) {
            shield.setShieldRadius(shield.getShieldRadius()- (shield.getShieldShrinkRate() * delta));  // Shrink the shield based on delta time


            // If the shield shrinks completely, deactivate it
            if (shield.getShieldRadius() <= 0) {
                System.out.println("Shield Deactivated");
                isShieldActive = false;
            }

            // Check if the player collides with the shield
            float playerX = Game.getPlayer().getPosX();
            float playerY = Game.getPlayer().getPosY();

            float distance = (float) Math.sqrt(Math.pow(playerX - shield.getShildCenterx(), 2) + Math.pow(playerY - shield.getShildCentery(), 2));


            float tolerance = 5.0f; // Adjust this value if needed (for precision)
            System.out.println("Shield Shrinking: " + shield.getShieldRadius());
            System.out.println("playerx:" +Game.getPlayer().getPosX() + ", " + "playery:" +Game.getPlayer().getPosY()+"ahmaaagh");
            System.out.println(distance + ", " + "shield center y:" +shield.getShildCentery() + ", " + "shield center x:" +shield.getShildCenterx() + "olagh");

            if (Math.abs(distance - shield.getShieldRadius()) <= tolerance) {
                System.out.println("got hit");

                Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() - 1);
                if (Game.getPlayer().getPlayerHealth() < 0) {
                    // Trigger death animation if health is below 0
                    Game.getPlayer().setDead(true);
                    AudioManager.getInstance().playLoseSound();
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new loseGameMenu());
                }
            }
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

        updatePopUps();

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

    private void spawnElder() {
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

        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getElder_frames();
        float speed = 40 + rand.nextFloat() * 60;
        Game.getEnemies().add(new Elder(spawnX, spawnY, speed, animation));

        // Trigger the shield
        isShieldActive = true;
        shield = new Shield(Game.getPlayer().getPosX(), Game.getPlayer().getPosY());
    }



    private boolean isFarOffScreen(float x, float y) {
        float margin = 200;
        return x < -margin || x > worldWidth + margin ||
            y < -margin || y > worldHeight + margin;
    }

    public void render() {
        // First render all normal game objects
        Main.getBatch().setColor(1, 1, 1, 0.8f);
        Main.getBatch().draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        for (Tree tree : Game.getTrees()) {
            tree.render(Main.getBatch());
        }

        for (Enemy enemy : Game.getEnemies()) {
            enemy.render(Main.getBatch());
        }

        drawPlayerHP();
        drawElapsedTime();
        drawXPProgressBar();
        drawNumberOfKills();
        drawShotsRemained();

        for (Seed seed : Game.getSeeds()) {
            seed.render(Main.getBatch());
        }

        for(DamagePopup damagePopup : Game.getPlayer().getDamagePopups()) {
            damagePopup.render(Main.getBatch());
        }
        if(shield != null) {
            shield.render(Main.getBatch());
        }

        // End the normal rendering batch
        Main.getBatch().end();
        Main.getBatch().begin();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        // Use additive blending for light effect
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        // Position and draw light
        float px = Game.getPlayer().getPosX();
        float py = Game.getPlayer().getPosY();
        lightSprite.setPosition(px - lightSprite.getWidth()/2f + 10, py - lightSprite.getHeight()/2f + 20);
        lightSprite.setColor(1f, 1f, 1f, 0.1f);
        lightSprite.draw(Main.getBatch());

        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Main.getBatch().end();

        // Render UI/stage
        Main.getBatch().begin();
    }


    public void dispose() {
        backgroundTexture.dispose();
        GameAssetManager.getGameAssetManager().getTree_tex().dispose();
        GameAssetManager.getGameAssetManager().getEyeBatBulletTex().dispose();
        GameAssetManager.getGameAssetManager().getTentacleSeedTex().dispose();
        if(shield != null) {
            shield.dispose();
        }
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

    public void initializeXPProgressBar(Skin skin) {
        OrthographicCamera cam = GameView.getCamera();
        float camRight = cam.position.x + cam.viewportWidth / 2;
        float camTop = cam.position.y + cam.viewportHeight / 2;
        // Create the ProgressBar (a range between 0 and 1 for progress)
        ProgressBar.ProgressBarStyle style = skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class);
        xpProgressBar = new ProgressBar(0, 1, 0.01f, false, style);  // min, max, step size, vertical, style

        xpProgressBar.setPosition( camTop - 55,camRight - 16); // You can adjust this position based on camera view
        xpProgressBar.setSize(250, 18);  // Adjust size for XP bar
    }

    private void drawXPProgressBar() {
        OrthographicCamera cam = GameView.getCamera();
        float camRight = cam.position.x + cam.viewportWidth / 2;
        float camTop = cam.position.y + cam.viewportHeight / 2;

        // Calculate the XP progress
        float progress = (float) Game.getPlayer().getXp() / (Game.getPlayer().getLevel() * 20);
        progress = Math.min(progress, 1); // Ensure it does not exceed 100%

        xpProgressBar.setValue(progress); // Set the value of the XP progress bar

        float barWidth = 250;  // Width of the progress bar

        float x = camRight - barWidth - 20;
        float y = camTop - 80;

        xpProgressBar.setPosition(x, y); // Set position of the XP progress bar

        xpProgressBar.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        xpProgressBar.draw(Main.getBatch(), 1);
        drawProcessLevel(x-240 ,y+20);
    }

    private void drawProcessLevel(float x, float y) {

        String timeStr = String.format("Process Level:");
        font.getData().setScale(2.3f);
        layout.setText(font, timeStr);

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
        float y = camTop - 110;

        font.draw(Main.getBatch(), layout, x, y);
    }

    private void drawShotsRemained() {
        OrthographicCamera cam = GameView.getCamera();
        float camRight = cam.position.x + cam.viewportWidth / 2;
        float camTop = cam.position.y + cam.viewportHeight / 2;

        String timeStr = String.format("shots remained : %d", Game.getPlayer().getWeapon().getAmmo() - Game.getPlayer().getWeapon().getNumberOfShoots());
        font.getData().setScale(2.5f);
        layout.setText(font, timeStr);

        float textWidth = layout.width;

        float x = camRight - textWidth - 16; // 16px margin from right
        float y = camTop - 145;

        font.draw(Main.getBatch(), layout, x, y);
    }

    private void spawnInitialTrees() {
        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(11) + 50; i++) {
            float x = rand.nextFloat() * worldWidth;
            float y = rand.nextFloat() * worldHeight;
            Game.getTrees().add(new Tree(x, y, GameAssetManager.getGameAssetManager().getTree_tex()));
        }
    }

    private void updatePopUps(){
        Iterator<DamagePopup> iterator = Game.getPlayer().getDamagePopups().iterator();
        while (iterator.hasNext()) {
            DamagePopup popup = iterator.next();
            popup.update(Gdx.graphics.getDeltaTime());
            if (popup.isFinished()) {
                iterator.remove();
            }
        }

    }

    public static void setShieldActive(boolean shieldActive) {
        isShieldActive = shieldActive;
    }

    public static boolean isIsShieldActive() {
        return isShieldActive;
    }
}
