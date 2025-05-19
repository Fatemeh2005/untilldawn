package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Model.PlayerTypes;
import com.tilldawn.Model.User;

public class MainMenuView implements Screen {
    private Stage stage;
    private final TextButton SettingsMenuButton;
    private final TextButton ProfileMenuButton;
    private final TextButton pregameMenuButton;
    private final TextButton scoreBoardMenuButton;
    private final TextButton hintBoardMenuButton;
    private final TextButton resumeSavedGameButton;
    private final Label gameTitle;
    public Table table;
    private final MainMenuController controller;
    private PlayerTypes Avatar;
    private Animation<Texture> animations;
    private User currentUser;
    private Image avatarImage;
    private Label usernameLabel;
    private Label scoreLabel;

    public MainMenuView(MainMenuController controller, Skin skin, PlayerTypes Avatar, Animation<Texture> animations, User currentUser) {
        this.controller = controller;
        this.SettingsMenuButton = new TextButton("settings", skin);
        this.ProfileMenuButton = new TextButton("Go To profile", skin);
        this.pregameMenuButton = new TextButton("Go To pregame", skin);
        this.resumeSavedGameButton = new TextButton("resume saved game", skin);
        this.scoreBoardMenuButton = new TextButton("Go To scoreboard menu", skin);
        this.hintBoardMenuButton = new TextButton("Go To hint", skin);
        this.gameTitle = new Label("Main menu", skin);
        this.table = new Table();
        this.animations = animations;
        this.currentUser = currentUser;
        this.Avatar = Avatar;

        if(currentUser != null) {
        Texture avatarTexture = new Texture(Gdx.files.internal(currentUser.getAvatarImageAddress()));
        this.avatarImage = new Image(avatarTexture);
        this.usernameLabel = new Label("User: " + currentUser.getUsername(), skin);
        this.scoreLabel = new Label("Score: " + currentUser.getScore(), skin);
        }
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        table.add(gameTitle);
        table.row().pad(10, 0, 10, 0);
        table.add(avatarImage).width(100).height(100);
        table.row().pad(5, 0, 5, 0);
        table.add(usernameLabel);
        table.row().pad(5, 0, 15, 0);
        table.add(scoreLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(SettingsMenuButton).width(600);
        table.row().pad(10, 0, 10, 0);
        table.add(ProfileMenuButton).width(600);
        table.row().pad(10, 0, 10, 0);
        table.add(pregameMenuButton).width(600);
        table.row().pad(10, 0, 10, 0);
        table.add(scoreBoardMenuButton).width(600);
        table.row().pad(10, 0, 10, 0);
        table.add(hintBoardMenuButton).width(600);
        table.row().pad(10, 0, 10, 0);
        table.add(resumeSavedGameButton).width(600);
        table.row().pad(10, 0, 10, 0);

        SettingsMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //  controller.goToRegisterMenu();
            }
        });

        ProfileMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToProfileMenu(currentUser);
            }
        });

        pregameMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToPregameMenu(Avatar, animations);
            }
        });

        scoreBoardMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //    controller.goToScoreboardMenu();
            }
        });

        hintBoardMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //    controller.goToHintBoard();
            }
        });

        resumeSavedGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //    controller.resumeSavedGame();
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
}
