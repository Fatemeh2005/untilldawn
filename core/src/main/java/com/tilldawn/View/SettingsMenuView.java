package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.SettingsController;
import com.tilldawn.Model.GameAssetManager;

public class SettingsMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private SettingsController controller;

    public SettingsMenuView() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin =skin = GameAssetManager.getGameAssetManager().getSkin();
        controller = new SettingsController();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label musicLabel = new Label("Music Volume", skin);
        final Slider musicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        musicSlider.setValue(controller.getMusicVolume());

        musicSlider.addListener(e -> {
            controller.setMusicVolume(musicSlider.getValue());
            return false;
        });

        Label sfxLabel = new Label("SFX Volume", skin);
        final Slider sfxSlider = new Slider(0f, 1f, 0.1f, false, skin);
        sfxSlider.setValue(controller.getSfxVolume());

        sfxSlider.addListener(e -> {
            controller.setSfxVolume(sfxSlider.getValue());
            return false;
        });

        TextButton changeMusicBtn = new TextButton("Next Music", skin);
        changeMusicBtn.addListener(e -> {
            controller.playNextMusic();
            return false;
        });

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(e -> {
            // برگرد به منوی اصلی
            controller.goBackToMainMenu();
            return false;
        });

        table.add(musicLabel).pad(10);
        table.row();
        table.add(musicSlider).width(300).pad(10);
        table.row();
        table.add(sfxLabel).pad(10);
        table.row();
        table.add(sfxSlider).width(300).pad(10);
        table.row();
        table.add(changeMusicBtn).pad(10);
        table.row();
        table.add(backButton).pad(10);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
