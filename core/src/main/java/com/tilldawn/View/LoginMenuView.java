package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.LoginMenuController;
import com.tilldawn.Model.PlayerTypes;

public class LoginMenuView implements Screen {
    private Stage stage;
    private final TextButton loginButton;
    private final TextButton playAsGuestButton;
    private final TextButton forgotPasswordButton;
    private final Label gameTitle;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField answerSecurityQuestionField;
    private final Label errorLabel;
    public Table table;
    private final LoginMenuController controller;
    private PlayerTypes avatar;

    public LoginMenuView(LoginMenuController controller, Skin skin, PlayerTypes avatar) {
        this.controller = controller;
        this.loginButton = new TextButton("Login", skin);
        this.playAsGuestButton = new TextButton("Play as Guest", skin);
        this.forgotPasswordButton = new TextButton("Forgot Password", skin);
        forgotPasswordButton.setVisible(true); // Enable it for use

        this.gameTitle = new Label("Login", skin);
        this.usernameField = new TextField("", skin);
        this.usernameField.setMessageText("Enter Username");
        this.passwordField = new TextField("", skin);
        this.passwordField.setMessageText("Enter Password");
        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');
        this.answerSecurityQuestionField = new TextField("", skin);
        this.answerSecurityQuestionField.setMessageText("What is your favorite prime number?");
        this.errorLabel = new Label("", skin);
        this.errorLabel.setColor(1, 0, 0, 1);
        this.errorLabel.setVisible(false);
        this.table = new Table();
        this.avatar = avatar;

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
        table.add(answerSecurityQuestionField).width(600);
        table.row().pad(10, 0, 10, 0);
        table.add(loginButton);
        table.row().pad(10, 0, 10, 0);
        table.add(forgotPasswordButton);
        table.row().pad(10, 0, 10, 0);
        table.add(playAsGuestButton);
        table.row().pad(10, 0, 10, 0);
        table.add(errorLabel);

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLoginButton();
            }
        });

        playAsGuestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleGuestLogin();
            }
        });

        forgotPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleForgotPassword();
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int i, int i1) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

    // Getters
    public TextField getUsernameField() { return usernameField; }
    public TextField getPasswordField() { return passwordField; }
    public TextField getAnswerSecurityQuestionField() { return answerSecurityQuestionField; }
    public Button getLoginButton() { return loginButton; }
    public TextButton getPlayAsGuestButton() { return playAsGuestButton; }
    public TextButton getForgotPasswordButton() { return forgotPasswordButton; }

    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public void hideError() {
        errorLabel.setVisible(false);
    }

    public void showForgotPasswordButton() {
        forgotPasswordButton.setVisible(true);
    }

    public void hideForgotPasswordButton() {
        forgotPasswordButton.setVisible(false);
    }
}
