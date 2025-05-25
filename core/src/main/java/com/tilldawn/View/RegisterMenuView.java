package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.RegisterMenuController;
import com.tilldawn.Model.Game;

public class RegisterMenuView implements Screen {
    private Stage stage;
    private final TextButton registerButton;
    private final TextButton goToLoginButton;
    private final Label gameTitle;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField answerSecurityQuestionField;
    private final Label errorLabel; // ✅ NEW
    public Table table;
    private final RegisterMenuController controller;

    public RegisterMenuView(RegisterMenuController controller, Skin skin) {
        this.controller = controller;
        this.registerButton = new TextButton("Register", skin);
        this.goToLoginButton = new TextButton("Go To Login", skin);
        this.gameTitle = new Label("Register", skin);
        this.usernameField = new TextField("", skin);
        this.usernameField.setMessageText("Enter Username");
        this.passwordField = new TextField("", skin);
        this.passwordField.setMessageText("Enter Password");
        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');
        this.answerSecurityQuestionField = new TextField("", skin);
        this.answerSecurityQuestionField.setMessageText("what is your favorite prime number?");

        this.errorLabel = new Label("", skin); // ✅ NEW
        this.errorLabel.setColor(1, 0, 0, 1);   // Red color
        this.errorLabel.setVisible(false);     // Start hidden

        this.table = new Table();

        controller.setView(this);
        setUpkeys();
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
        table.add(registerButton);
        table.row().pad(10, 0, 10, 0);
        table.add(goToLoginButton);
        table.row().pad(10, 0, 10, 0);
        table.add(errorLabel); // ✅ Show error label in layout

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleRegisterButton();
            }
        });
        getGoToLoginButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleRegisterButton();
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

    @Override
    public void resize(int i, int i1) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

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

    // ✅ Add method to show error
    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    // ✅ Optional: hide error manually if needed
    public void hideError() {
        errorLabel.setVisible(false);
    }

    public TextField getAnswerSecurityQuestionField() {
        return answerSecurityQuestionField;
    }

    public TextButton getGoToLoginButton() {
        return goToLoginButton;
    }

    private void setUpkeys(){
        Game.setKeyUp(Input.Keys.W);
        Game.setKeyDown(Input.Keys.S);
        Game.setKeyLeft(Input.Keys.A);
        Game.setKeyRight(Input.Keys.D);
        Game.setShoot(Input.Buttons.LEFT);
        Game.setReloadGun(Input.Keys.R);
    }
}
