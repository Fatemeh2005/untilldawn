package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.PlayerTypes;
import com.tilldawn.Model.Pregame;
import com.tilldawn.Model.User;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.PreGameMenuView;
import com.tilldawn.View.ProfileMenuView;
import com.tilldawn.View.RegisterMenuView;

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

}
