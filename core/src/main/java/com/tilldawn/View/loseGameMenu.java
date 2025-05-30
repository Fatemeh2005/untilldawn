package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.PlayerTypes;
import com.tilldawn.Model.User;

import java.io.FileWriter;
import java.io.IOException;

public class loseGameMenu implements Screen {
    private Stage stage;

    private final TextButton backToMainButton;

    private final Label loseTitle;

    private final Label userNameLabel;

    private final Label surviveTitle;

    private final Label numberOfKills;

    private final Label scoreFromGameLabel;

    public Table table;

    private String userName;

    private int surviveTime;

    public loseGameMenu() {
        this.backToMainButton = new TextButton("go back to main menu", GameAssetManager.getGameAssetManager().getSkin());
        this.loseTitle = new Label("You are such a Loser!", GameAssetManager.getGameAssetManager().getSkin());
        this.table = new Table();
        this.surviveTime = (int) Game.getElapsedTimeInSeconds();
        if(Game.getCurrentUser() == null){
            userName = "You are a guest!";
        } else {
            userName = Game.getCurrentUser().getUsername();
        }
        this.userNameLabel = new Label("User name: "+ userName, GameAssetManager.getGameAssetManager().getSkin());
        this.surviveTitle = new Label("Survive time: "+surviveTime+" seconds", GameAssetManager.getGameAssetManager().getSkin());
        this.numberOfKills = new Label("number of Kills in this game : " + Game.getPlayer().getNumberOfKillsInGame(),
            GameAssetManager.getGameAssetManager().getSkin());
        this.scoreFromGameLabel = new Label("score gained from the game : "+  surviveTime * Game.getPlayer().getNumberOfKillsInGame(),
            GameAssetManager.getGameAssetManager().getSkin());
        if(Game.getCurrentUser() != null){
            User currentUser = Game.getCurrentUser();
            currentUser.setNumberOfKills(Game.getPlayer().getNumberOfKillsInGame() + currentUser.getNumberOfKills());
            currentUser.setScore(currentUser.getScore() + surviveTime * Game.getPlayer().getNumberOfKillsInGame());
            if(surviveTime > currentUser.getMostTimeSurvived()) {
                currentUser.setMostTimeSurvived(surviveTime);
            }
            saveUserToJson(Game.getCurrentUser());
        }
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        table.add(loseTitle);
        table.row().pad(10, 0, 10, 0);
        table.add(userNameLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(surviveTitle);
        table.row().pad(10, 0, 10, 0);
        table.add(numberOfKills);
        table.row().pad(10, 0, 10, 0);
        table.add(scoreFromGameLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(backToMainButton).width(600);
        table.row().pad(10, 0, 10, 0);


        backToMainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO:start new game properly move to a new class
                Game.getEnemies().clear();
                Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerType().getHealth());
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1); // Dark background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSurviveTime() {
        return surviveTime;
    }

    public void setSurviveTime(int surviveTime) {
        this.surviveTime = surviveTime;
    }
    private boolean saveUserToJson(User user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("users/" + user.getUsername() + ".json")) {
            gson.toJson(user, writer);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to save user: " + e.getMessage());
            return false;
        }
    }
}
