package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.LoginMenuController;
import com.tilldawn.Control.ProfileMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.PlayerTypes;
import com.tilldawn.Model.User;

public class ProfileMenuView extends ScreenAdapter {

    private final Skin skin;
    private final Stage stage;
    private final ProfileMenuController controller;

    private TextField newUsernameField;
    private TextField oldPasswordField;
    private TextField newPasswordField;
    private Label errorLabel;
    private Label currentUserLabel;
    TextButton saveButton ;
    TextButton backButton ;

    public ProfileMenuView(ProfileMenuController controller, Skin skin, User user) {
        if (user == null || user.getUsername() == null)
            throw new IllegalArgumentException("User passed to ProfileMenuView is null or invalid");

        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        controller.setView(this);
        buildUI(user);
        controller.loadUser(user);
    }

    private void buildUI(User user) {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label titleLabel = new Label("Edit Profile", skin, "title");
        titleLabel.setAlignment(Align.center);

        currentUserLabel = new Label("Current Username: " + user.getUsername(), skin);

        newUsernameField = new TextField("", skin);
        newUsernameField.setMessageText("New Username");

        oldPasswordField = new TextField("", skin);
        oldPasswordField.setMessageText("Old Password");
        oldPasswordField.setPasswordMode(true);
        oldPasswordField.setPasswordCharacter('*');

        newPasswordField = new TextField("", skin);
        newPasswordField.setMessageText("New Password");
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');

        saveButton = new TextButton("Save Changes", skin);
        backButton = new TextButton("Back to Login", skin);

        errorLabel = new Label("", skin);
        errorLabel.setWrap(true);
        errorLabel.setAlignment(Align.center);

        saveButton.addListener(event -> {
            controller.handleSaveChanges();
            return true;
        });

        backButton.addListener(event -> {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new LoginMenuView(
                new LoginMenuController(),
                GameAssetManager.getGameAssetManager().getSkin(),
                user.getPlayerTypes()));
            return true;
        });

        table.add(titleLabel).colspan(2).padBottom(30);
        table.row();
        table.add(currentUserLabel).colspan(2).padBottom(20);
        table.row();
        table.add(new Label("New Username", skin)).left().padRight(10);
        table.add(newUsernameField).width(250).padBottom(15);
        table.row();
        table.add(new Label("Old Password", skin)).left().padRight(10);
        table.add(oldPasswordField).width(250).padBottom(15);
        table.row();
        table.add(new Label("New Password", skin)).left().padRight(10);
        table.add(newPasswordField).width(250).padBottom(25);
        table.row();
        table.add(saveButton).colspan(2).padBottom(10);
        table.row();
        table.add(backButton).colspan(2).padBottom(20);
        table.row();
        table.add(errorLabel).colspan(2).width(300);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public TextField getNewUsernameField() {
        return newUsernameField;
    }

    public TextField getOldPasswordField() {
        return oldPasswordField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public void showError(String msg) {
        errorLabel.setText("[RED]" + msg);
    }

    public void showSuccess(String msg) {
        errorLabel.setText("[GREEN]" + msg);
    }

    public void setCurrentUsername(String name) {
        currentUserLabel.setText("Current Username: " + name);
    }

    public TextButton getSaveButton() {
        return saveButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }
}
