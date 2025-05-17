package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.RegisterMenuController;
import com.tilldawn.Main;

import javax.swing.*;

public class RegisterMenuView implements Screen {
    private Stage stage;
    private final TextButton registerButton;
    private final Label gameTitle;
    private final TextField usernameField;
    private final TextField passwordField;
    public Table table;
    private final RegisterMenuController controller;

    public RegisterMenuView(RegisterMenuController controller, Skin skin) {
        this.controller = controller;
        this.registerButton = new TextButton("Register", skin);
        this.gameTitle = new Label("Register", skin);
        this.usernameField = new TextField("", skin);
        this.usernameField.setMessageText("Enter Username");
        this.passwordField = new TextField("", skin);
        this.passwordField.setMessageText("Enter Password");
        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');
        this.table = new Table();

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        table.add(gameTitle);
        table.row().pad(10, 0, 10, 0);
        table.add(usernameField).width(600);
        table.row().pad(10, 0, 10, 0);
        table.add(passwordField).width(600);
        table.row().pad(10, 0, 10, 0);
        table.add(registerButton);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleRegisterButton();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Button getPlayButton() {
        return registerButton;
    }


}
