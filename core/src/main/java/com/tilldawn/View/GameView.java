package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Control.GameController;
import com.tilldawn.Control.WorldController;
import com.tilldawn.Main;
import com.tilldawn.Model.AudioManager;
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
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
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
    public boolean touchDown(int i, int i1, int i2, int i3) {
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

        Game.setGamePaused(true);
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
                Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth() + 1);
                if (!Game.getPlayer().getAbilitiesGained().contains("Vitality")) {
                    Game.getPlayer().getAbilitiesGained().add("Vitality");
                }
                removeAbilitiesMenu();
                Game.setGamePaused(false); // Resume game
            }
        });

        damager.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Damager button clicked");
                Game.getPlayer().setLevelUp(false); // Disable level-up state
                Game.getPlayer().getWeapon().setPowerBoostActive(true);
                if(!Game.getPlayer().getAbilitiesGained().contains("Damager")) {
                    Game.getPlayer().getAbilitiesGained().add("Damager");
                }

                removeAbilitiesMenu();
                Game.setGamePaused(false); // Resume game
            }
        });

        procrease.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Procrease button clicked");
                Game.getPlayer().setLevelUp(false); // Disable level-up state
                // Example: Increase projectile size or count
                Game.getPlayer().getWeapon().setProjectile(Game.getPlayer().getWeapon().getProjectile() + 1);
                if(!Game.getPlayer().getAbilitiesGained().contains("Procrease")) {
                    Game.getPlayer().getAbilitiesGained().add("Procrease");
                }
                removeAbilitiesMenu();
                Game.setGamePaused(false); // Resume game
            }
        });

        amocrease.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Amocrease button clicked");
                Game.getPlayer().setLevelUp(false); // Disable level-up state
                // Example: Increase max ammo
                Game.getPlayer().getWeapon().setAmmo(Game.getPlayer().getWeapon().getAmmo() + 5);
                if(!Game.getPlayer().getAbilitiesGained().contains("Amocrease")) {
                    Game.getPlayer().getAbilitiesGained().add("Amocrease");
                }
                removeAbilitiesMenu();
                Game.setGamePaused(false); // Resume game
            }
        });

        speedy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Speedy button clicked");
                Game.getPlayer().setLevelUp(false); // Disable level-up state
                Game.getPlayer().setSpeedBoostActive(true);
                if(!Game.getPlayer().getAbilitiesGained().contains("Speedy")) {
                    Game.getPlayer().getAbilitiesGained().add("Speedy");
                }
                removeAbilitiesMenu();
                Game.setGamePaused(false); // Resume game
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

    public void PauseMenu(Skin skin) {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton resume = new TextButton("Resume", skin);
        TextButton giveUp = new TextButton("Give Up", skin);

        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removePauseMenu();
                Game.setPausePressed(false);
            }
        });

        giveUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getPlayer().setDead(true);
                AudioManager.getInstance().playLoseSound();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new loseGameMenu());
                Game.setGamePaused(false);
            }
        });

        // 🎯 Get abilities gained
        String abilities = Game.getPlayer().getAbilitiesGained().isEmpty()
            ? "None"
            : String.join(", ", Game.getPlayer().getAbilitiesGained());

        Label abilitiesLabel = new Label("Abilities Gained:\n" + abilities, skin);
        abilitiesLabel.setWrap(true);

        // 🎯 Add cheat codes info
        Label cheatsLabel = new Label(
            "Cheat Codes:\n" +
                "H = Survive when about to die\n" +
                "L = Level up\n" +
                "Alt Left = Increase HP anytime\n" +
                "T = Pass one minute\n" +
                "X = Increase XP\n" +
                "Comma = +28 seconds", skin);
        cheatsLabel.setWrap(true);

        // 🎯 Add UI elements to the table
        table.add(resume).colspan(2).fillX().pad(10);
        table.row();
        table.add(giveUp).colspan(2).fillX().pad(10);
        table.row();
        table.add(abilitiesLabel).colspan(2).width(400).padTop(20);
        table.row();
        table.add(cheatsLabel).colspan(2).width(400).padTop(10);
        table.row();
    }


    private void removePauseMenu() {
        stage.clear();  // Clear the UI stage
        Gdx.input.setInputProcessor(this);  // Return control back to the main game view input processor
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
