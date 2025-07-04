package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.tilldawn.Main;
import com.tilldawn.Model.Enemies.Elder;
import com.tilldawn.Model.Enemies.Enemy;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.GameView;

public class PlayerController {

    // Constants for background dimensions
    private final float WORLD_WIDTH = 20000;
    private final float WORLD_HEIGHT = 20000;

    private int autoAimTargetX = 0;
    private int autoAimTargetY = 0;

    // Also define your player's sprite dimensions
    private final float PLAYER_WIDTH = Game.getPlayer().getPlayerSprite().getWidth();
    private final float PLAYER_HEIGHT = Game.getPlayer().getPlayerSprite().getHeight();
    GameController controller;

    private float lastShootTime = 0f;  // Time of the last shot
    private float shootCooldown = 0.2f;  // Cooldown time between shots (in seconds)

    public PlayerController(GameController controller) {
        this.controller = controller;
    }

    public void update(GameView view) {
        // If player is dead, do not allow any further movement or idle animation

        handlePlayerInput(view);  // Handle player input for movement
        Game.getPlayer().getPlayerSprite().setPosition(Game.getPlayer().getPosX(), Game.getPlayer().getPosY());
        Game.getPlayer().getPlayerSprite().draw(Main.getBatch());
    }

    public void handlePlayerInput(GameView view) {
        if (Game.getPlayer().isDead()) {
            return;  // Skip handling input if the player is dead
        }
        int speed = Game.getPlayer().isSpeedBoostActive() ? Game.getPlayer().getSpeed() * 2 : Game.getPlayer().getSpeed();
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        int newX = Game.getPlayer().getPosX();
        int newY = Game.getPlayer().getPosY();
        if (Gdx.input.isKeyPressed(Game.getKeyUp())) {
            newY += speed;
            idleAnimation(Game.getPlayer().getAnimations());
        }
        if (Gdx.input.isKeyPressed(Game.getKeyDown())) {
            newY -= speed;
            idleAnimation(Game.getPlayer().getAnimations());
        }
        if (Gdx.input.isKeyPressed(Game.getKeyRight())) {
            newX += speed;
            idleAnimation(Game.getPlayer().getAnimations());
        }
        if (Gdx.input.isKeyPressed(Game.getKeyLeft())) {
            newX -= speed;
            idleAnimation(Game.getPlayer().getAnimations());
            Game.getPlayer().getPlayerSprite().flip(true, false);
        }
        if (Gdx.input.isKeyPressed(Game.getReloadGun())) {
            Game.setReloadOn(true);
            return;
        }
        //turn auto aim on ot off with space
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            System.out.println("done");
            Game.setAutoAimOn(!Game.isAutoAimOn());
            return;
        }
        if ((Gdx.input.isButtonPressed(Game.getShoot()) || Gdx.input.isKeyJustPressed(Game.getShoot())) &&
            (TimeUtils.nanoTime() - lastShootTime) / 1_000_000_000f > shootCooldown) {
            if(Game.isAutoAimOn()) {
                setAutoAim();
                controller.getWeaponController().handleWeaponShoot(autoAimTargetX, autoAimTargetY);
            } else {
                controller.getWeaponController().handleWeaponShoot(mouseX, mouseY);
            }
            lastShootTime = TimeUtils.nanoTime();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.H) && Game.getPlayer().getPlayerHealth() == 0) {
            Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() + 1);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) && !Game.isPausePressed()) {
            Game.setPausePressed(true);
            view.PauseMenu(GameAssetManager.getGameAssetManager().getSkin());
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            Game.setElapsedTimeInSeconds(Game.getElapsedTimeInSeconds() + 60f);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.COMMA)) {
            Game.setElapsedTimeInSeconds(Game.getElapsedTimeInSeconds() + 28f);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
            Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() + 5);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            Game.getPlayer().setLevel(Game.getPlayer().getLevel() + 1);
            Game.getPlayer().setLevelUp(true);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            Game.getPlayer().setXp(Game.getPlayer().getXp() + 3);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            bossFight();
            return;
        }
        newX = (int) MathUtils.clamp(newX, 0, WORLD_WIDTH - PLAYER_WIDTH);
        newY = (int) MathUtils.clamp(newY, 0, WORLD_HEIGHT - PLAYER_HEIGHT);

        Game.getPlayer().setPosX(newX);
        Game.getPlayer().setPosY(newY);
    }

    public void render(SpriteBatch batch) {
        // Render player sprite if not dead
        if (!Game.getPlayer().isDead()) {
            Game.getPlayer().getPlayerSprite().draw(batch);
        }
    }

    public void idleAnimation(Animation<Texture> animation) {
        // Only show idle animation if the player is alive

        Game.getPlayer().getPlayerSprite().setRegion(animation.getKeyFrame(Game.getPlayer().getTime()));

        if (!animation.isAnimationFinished(Game.getPlayer().getTime())) {
            Game.getPlayer().setTime(Game.getPlayer().getTime() + Gdx.graphics.getDeltaTime());
        } else {
            Game.getPlayer().setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void bossFight() {
        for(Enemy enemy : Game.getEnemies()) {
            if(enemy instanceof Elder){
                return;
            }
        }
        WorldController.spawnElder();
    }

    private void setAutoAim() {
        float minDistance = Float.MAX_VALUE;
        Enemy closestEnemy = null;

        float playerX = Game.getPlayer().getPosX();
        float playerY = Game.getPlayer().getPosY();

        for (Enemy enemy : Game.getEnemies()) {
            float dx = enemy.getX() - playerX;
            float dy = enemy.getY() - playerY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance < minDistance) {
                minDistance = distance;
                closestEnemy = enemy;
            }
        }

        if (closestEnemy != null) {
            float enemyCenterX = closestEnemy.getX() + closestEnemy.getRect().getWidth() / 2;
            float enemyCenterY = closestEnemy.getY() + closestEnemy.getRect().getHeight() / 2;

            Vector3 enemyWorldCenter = new Vector3(enemyCenterX, enemyCenterY, 0);
            Vector3 screenCoords = GameView.getCamera().project(enemyWorldCenter);

            autoAimTargetX = (int) screenCoords.x;
            autoAimTargetY = (int) screenCoords.y;
            Gdx.input.setCursorPosition((int) closestEnemy.getX(), (int)closestEnemy.getY());

            // Optional debug
            System.out.println("Auto-aim target screen: " + autoAimTargetX + ", " + autoAimTargetY);
        }
        else {
            autoAimTargetX = -1;
            autoAimTargetY = -1;
        }
    }

}
