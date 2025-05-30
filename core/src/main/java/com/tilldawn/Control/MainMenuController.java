package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.*;

import java.util.ArrayList;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void goToPregameMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGameMenuView(
            new PreGameMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public  void goToProfileMenu(User name) {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView(
            new ProfileMenuController(),
            GameAssetManager.getGameAssetManager().getSkin(),
            name));
    }
    public void goToHintBoard(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new HintMenuView(GameAssetManager.getGameAssetManager().getSkin(), new MainMenuController()));
    }

    public void goToScoreboardMenu(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ScoreBoardView(GameAssetManager.getGameAssetManager().getSkin(), new MainMenuController()));
    }

    public void exitFromAccount(){
        Game.setCurrentUser(null);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new RegisterMenuView(new RegisterMenuController() ,GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void resumeSavedGame(){
        Game.loadPlayerData("savedGames/"+"save"+Game.getCurrentUser().getUsername()+".txt");
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GameView(new GameController()));
    }

}
