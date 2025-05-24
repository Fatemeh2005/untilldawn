package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;

public class GameView implements Screen, InputProcessor {
    private GameController controller;
    private Stage stage;
    private static OrthographicCamera camera = new OrthographicCamera();

    public GameView(GameController controller) {
        Gdx.input.setInputProcessor(this);
        this.controller = controller;
        this.controller.setView(this);
        this.stage = new Stage();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Main.getBatch().setProjectionMatrix(camera.combined);

        Main.getBatch().begin();

        controller.renderWorld();

        controller.updateGame(camera);
        Main.getBatch().end();


        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    // Other required Screen methods...
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public static OrthographicCamera getCamera() {
        return camera;
    }

    public void printAbilitiesMenu(Skin skin) {
        // Clear any previous UI elements before creating new ones
        stage.clear();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        Table table = new Table();
        table.setFillParent(true); // Make the table fill the entire screen
        stage.addActor(table); // Add the table to the stage

        // Create buttons for each ability
        TextButton vitality = new TextButton("Vitality", skin);
        TextButton damager = new TextButton("Damager", skin);
        TextButton procrease = new TextButton("Procrease", skin);
        TextButton amocrease = new TextButton("Amocrease", skin);
        TextButton speedy = new TextButton("Speedy", skin);

        // Set up click listeners for each button
        vitality.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Vitality button clicked");
                Game.getPlayer().setLevelUp(false); // Disable level-up state
                // Example: Increase player's max health
              //  Game.getPlayer().setPlayerHealth(Game.getPlayer().getMaxHp());
                removeAbilitiesMenu(); // Remove menu after selection
            }
        });

        damager.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Damager button clicked");
                Game.getPlayer().setLevelUp(false); // Disable level-up state
                // Example: Increase damage multiplier
               // Game.getPlayer().setDamageMultiplier(1.5f);
                // Reset damage multiplier after 10 seconds (for example)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                  //      Game.getPlayer().setDamageMultiplier(1f);
                        this.cancel(); // Stop the timer after resetting
                    }
                }, 10, 2); // Runs after 10 seconds, repeats every 2 seconds
                removeAbilitiesMenu(); // Remove menu after selection
            }
        });

        procrease.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Procrease button clicked");
                Game.getPlayer().setLevelUp(false); // Disable level-up state
                // Example: Increase projectile size or count
             //   Game.getPlayer().setProjectileAddition(3);
                removeAbilitiesMenu(); // Remove menu after selection
            }
        });

        amocrease.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Amocrease button clicked");
                Game.getPlayer().setLevelUp(false); // Disable level-up state
                // Example: Increase max ammo
               // Game.getPlayer().setMaxAmmoAddition(5);
                removeAbilitiesMenu(); // Remove menu after selection
            }
        });

        speedy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Speedy button clicked");
                Game.getPlayer().setLevelUp(false); // Disable level-up state
                // Example: Increase speed multiplier
              //  Game.getPlayer().setSpeedMultiplier(2f);
                // Reset speed multiplier after 10 seconds (for example)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                   //     Game.getPlayer().setSpeedMultiplier(1f);
                        this.cancel(); // Stop the timer after resetting
                    }
                }, 10, 2); // Runs after 10 seconds, repeats every 2 seconds
                removeAbilitiesMenu(); // Remove menu after selection
            }
        });

        // Add buttons to the table (arrange them vertically)
        table.add(vitality).colspan(2).fillX().pad(10); // Make the buttons fill horizontally
        table.row(); // Move to the next row
        table.add(damager).colspan(2).fillX().pad(10);
        table.row();
        table.add(procrease).colspan(2).fillX().pad(10);
        table.row();
        table.add(amocrease).colspan(2).fillX().pad(10);
        table.row();
        table.add(speedy).colspan(2).fillX().pad(10);
    }

    // Method to remove the abilities menu and return input to the game view
    private void removeAbilitiesMenu() {
        stage.clear();  // Clear the UI stage
        Gdx.input.setInputProcessor(this);  // Return control back to the main game view input processor
    }


}
