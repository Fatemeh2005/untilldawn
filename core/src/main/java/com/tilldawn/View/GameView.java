package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.PlayerTypes;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class GameView implements Screen, InputProcessor {
    private GameController controller;
    private Stage stage;

    public GameView(GameController controller, Skin skin, PlayerTypes type, Animation<Texture> animations) {
        this.controller = controller;
        this.controller.setView(this, type, animations);
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Main.getBatch().begin();
        controller.updateGame();

        controller.renderWorld(Main.getBatch());
        Main.getBatch().end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        controller.getWorldController().resize(width, height);
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
}
