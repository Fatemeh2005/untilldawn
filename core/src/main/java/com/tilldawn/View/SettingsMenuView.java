package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.SettingsController;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;

public class SettingsMenuView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final SettingsController controller;

    public SettingsMenuView() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = GameAssetManager.getGameAssetManager().getSkin();
        controller = new SettingsController();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label musicLabel = new Label("Music Volume", skin);
        final Slider musicSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicSlider.setValue(controller.getMusicVolume());
        musicSlider.addListener(event -> {
            controller.setMusicVolume(musicSlider.getValue());
            return false;
        });

        Label sfxLabel = new Label("SFX Volume", skin);
        final Slider sfxSlider = new Slider(0f, 1f, 0.01f, false, skin);
        sfxSlider.setValue(controller.getSfxVolume());
        sfxSlider.addListener(event -> {
            controller.setSfxVolume(sfxSlider.getValue());
            return false;
        });

        TextButton nextMusicButton = new TextButton("Next Music", skin);
        nextMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.playNextMusic();
                event.stop(); // stop event propagation!
            }
        });

        TextButton blackWhite = new TextButton("make it black and white", skin);
        blackWhite.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Main.setGrayscaleEnabled(true);
            }
        });

        SelectBox<String> selectUpKey = new SelectBox<>(skin);
        Array<String> up = new Array<>();
        up.add("W");
        up.add("up arrow");
        selectUpKey.setItems(up);

        SelectBox<String> selectDownKey = new SelectBox<>(skin);
        Array<String> down = new Array<>();
        down.add("S");
        down.add("down arrow");
        selectDownKey.setItems(down);

        SelectBox<String> selectRightKey = new SelectBox<>(skin);
        Array<String> right = new Array<>();
        right.add("D");
        right.add("right arrow");
        selectRightKey.setItems(right);

        SelectBox<String> selectLeftKey = new SelectBox<>(skin);
        Array<String> left = new Array<>();
        left.add("A");
        left.add("left arrow");
        selectLeftKey.setItems(left);

        SelectBox<String> selectShootKey = new SelectBox<>(skin);
        Array<String> shoot = new Array<>();
        shoot.add("left mouse click");
        shoot.add("enter");
        selectShootKey.setItems(shoot);

        SelectBox<String> selectReloadKey = new SelectBox<>(skin);
        Array<String> reload = new Array<>();
        reload.add("R");
        reload.add("back space");
        selectReloadKey.setItems(reload);

        // For the "Up" key selection
        selectUpKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = selectUpKey.getSelected();
                switch (selected) {
                    case "W":
                        Game.setKeyUp(Input.Keys.W);
                        break;
                    case "up arrow":
                        Game.setKeyUp(Input.Keys.UP);
                        break;
                }
            }
        });

// For the "Down" key selection
        selectDownKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = selectDownKey.getSelected();
                switch (selected) {
                    case "S":
                        Game.setKeyDown(Input.Keys.S);
                        break;
                    case "down arrow":
                        Game.setKeyDown(Input.Keys.DOWN);
                        break;
                }
            }
        });

// For the "Right" key selection
        selectRightKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = selectRightKey.getSelected();
                switch (selected) {
                    case "D":
                        Game.setKeyRight(Input.Keys.D);
                        break;
                    case "right arrow":
                        Game.setKeyRight(Input.Keys.RIGHT);
                        break;
                }
            }
        });

// For the "Left" key selection
        selectLeftKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = selectLeftKey.getSelected();
                switch (selected) {
                    case "A":
                        Game.setKeyLeft(Input.Keys.A);
                        break;
                    case "left arrow":
                        Game.setKeyLeft(Input.Keys.LEFT);
                        break;
                }
            }
        });

// For the "Shoot" key selection
        selectShootKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = selectShootKey.getSelected();
                switch (selected) {
                    case "left mouse click":
                        Game.setShoot(Input.Buttons.LEFT);
                        break;
                    case "enter":
                        Game.setShoot(Input.Keys.ENTER);
                        break;
                }
            }
        });

// For the "Reload" key selection
        selectReloadKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = selectReloadKey.getSelected();
                switch (selected) {
                    case "R":
                        Game.setReloadGun(Input.Keys.R);
                        break;
                    case "back space":
                        Game.setReloadGun(Input.Keys.BACKSPACE);
                        break;
                }
            }
        });


        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goBackToMainMenu();
                event.stop(); // optional: prevent propagation
            }
        });

        TextButton autoReloadButton = new TextButton("enable auto reload", skin);
        autoReloadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.setAutoReloadOn(true);
                event.stop(); // stop event propagation!
            }
        });

        table.defaults().pad(5);

        table.add(musicLabel).left().padRight(10);
        table.add(musicSlider).width(250).row();

        table.add(sfxLabel).left().padRight(10);
        table.add(sfxSlider).width(250).row();

        table.add(nextMusicButton).colspan(2).padTop(8).row();

        table.add(selectUpKey).width(250).padTop(12).row();
        table.add(selectDownKey).width(250).row();
        table.add(selectRightKey).width(250).row();
        table.add(selectLeftKey).width(250).row();
        table.add(selectShootKey).width(250).row();
        table.add(selectReloadKey).width(250).padBottom(8).row();

// Options & Back Button
        table.add(autoReloadButton).width(500).padTop(8).row();
        table.add(blackWhite).width(500).padTop(8).row();
        table.add(backButton).padTop(15).row();
    }


    @Override public void show() {
        Gdx.input.setInputProcessor(stage); // Re-set input each time screen is shown
    }
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
