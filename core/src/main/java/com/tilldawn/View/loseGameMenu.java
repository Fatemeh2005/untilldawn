package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.BiFunction;

public class loseGameMenu implements Screen {

    /* helper: t(en, fr) */
    private final BiFunction<String,String,String> t =
        (en, fr) -> Game.isIsFrench() ? fr : en;

    private Stage stage;
    private final TextButton backToMainButton;
    private final Label titleLabel;
    private final Label userNameLabel;
    private final Label surviveLabel;
    private final Label killsLabel;
    private final Label scoreLabel;
    public  final Table table;

    private final String userName;
    private final int surviveTime;

    public loseGameMenu() {

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        this.surviveTime = (int) Game.getElapsedTimeInSeconds();
        this.userName = (Game.getCurrentUser() == null)
            ? t.apply("You are a guest!", "Vous êtes invité !")
            : Game.getCurrentUser().getUsername();

        this.backToMainButton = new TextButton(
            t.apply("go back to main menu", "retour au menu principal"), skin);

        this.titleLabel = new Label(
            t.apply("You are such a Loser!", "Quelle défaite !"), skin);

        this.userNameLabel = new Label(
            t.apply("User name: ", "Nom d’utilisateur : ") + userName, skin);

        this.surviveLabel = new Label(
            t.apply("Survive time: ", "Temps de survie : ") +
                surviveTime + t.apply(" seconds", " secondes"), skin);

        this.killsLabel = new Label(
            t.apply("number of Kills in this game : ",
                "nombre d’ennemis éliminés : ") +
                Game.getPlayer().getNumberOfKillsInGame(), skin);

        int scoreGained = surviveTime * Game.getPlayer().getNumberOfKillsInGame();
        this.scoreLabel = new Label(
            t.apply("score gained from the game : ",
                "score obtenu dans la partie : ") + scoreGained, skin);

        this.table = new Table();

        /* update & save user stats */
        if (Game.getCurrentUser() != null) {
            User u = Game.getCurrentUser();
            u.setNumberOfKills(u.getNumberOfKills() + Game.getPlayer().getNumberOfKillsInGame());
            u.setScore(u.getScore() + scoreGained);
            if (surviveTime > u.getMostTimeSurvived()) u.setMostTimeSurvived(surviveTime);
            saveUserToJson(u);
        }
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        table.add(titleLabel);
        table.row().pad(10, 0, 10, 0);          // ← keep this exact padding
        table.add(userNameLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(surviveLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(killsLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(scoreLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(backToMainButton).width(600);
        table.row().pad(10, 0, 10, 0);

        backToMainButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e,float x,float y){
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


    @Override public void render(float d){
        Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(d); stage.draw();
    }
    @Override public void resize(int w,int h){}
    @Override public void pause(){}
    @Override public void resume(){}
    @Override public void hide(){}
    @Override public void dispose(){}

    /* save helper */
    private boolean saveUserToJson(User u){
        try(FileWriter w=new FileWriter("users/"+u.getUsername()+".json")){
            new GsonBuilder().setPrettyPrinting().create().toJson(u,w); return true;
        }catch(IOException e){ System.out.println("Failed to save: "+e); return false;}
    }
}
