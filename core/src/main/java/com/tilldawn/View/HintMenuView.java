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
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;

import java.util.Map;


public class HintMenuView implements Screen {
    private final Skin skin;
    private Stage stage;
    private final MainMenuController controller;

    public HintMenuView(Skin skin, MainMenuController controller) {
        this.skin = skin;
        this.controller = controller;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.pad(20);
        root.top().left();
        root.defaults().left();

        Label title = new Label("Hints & Help", skin, "title");
        root.add(title).padBottom(15);
        root.row();
        root.add(new Label("Hero tips", skin, "subtitle")).padBottom(8);
        root.row();
        String[] heroHints = {
            "• Shana: HP:4   Speed:4",
            "• Diamond: HP:7   Speed:1",
            "• Scarlet: HP:5   Speed:3",
        };
        for (String h : heroHints) {
            root.add(new Label(h, skin)).padBottom(4);
            root.row();
        }
        root.add(new Label("Current key bindings", skin, "subtitle")).padTop(12).padBottom(8);
        root.row();
        java.util.Map<String, String> keyMap = getCurrentKeyBindings();
        keyMap.forEach((action, key) -> {
            root.add(new Label(action + " : " + key, skin)).padBottom(3);
            root.row();
        });
        root.add(new Label("Cheat codes", skin, "subtitle")).padTop(12).padBottom(8);
        root.row();
        java.util.Map<String, String> cheats = java.util.Map.of(
            "H", "makes you survive right when you are about to die",
            "L", "for level up",
            "Alt left", "increase HP any time",
            "T", "one minute passes",
            "X", "increases XP",
            "Comma", "increases time 28 seconds"
        );
        cheats.forEach((code, effect) -> {
            root.add(new Label(code + " – " + effect, skin)).padBottom(3);
            root.row();
        });

        root.add(new Label("Ability descriptions", skin, "subtitle")).padTop(12).padBottom(8);
        root.row();
        java.util.Map<String, String> abilities = java.util.Map.of(
            "VITALITY", "Increase max HP by your current HP (double total HP)",
            "DAMAGER", "Increase weapon damage by 25% for 10 seconds",
            "PROCREASE", "Increase projectile speed of the weapon by 1 unit",
            "AMOCREASE", "Increase weapon's maximum ammo capacity by 5",
            "SPEEDY", "Double movement speed for 10 seconds"
        );
        abilities.forEach((name, desc) -> {
            root.add(new Label(name + " – " + desc, skin)).padBottom(3);
            root.row();
        });

        TextButton back = new TextButton("Back", skin);
        root.add(back).padTop(20);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(controller, skin));
            }
        });
        ScrollPane scrollPane = new ScrollPane(root, skin);
        scrollPane.setFadeScrollBars(false);
        Table container = new Table();
        container.setFillParent(true);
        container.add(scrollPane).expand().fill();

        stage.addActor(container);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    private java.util.Map<String, String> getCurrentKeyBindings() {
        String shoot;
        if (Game.getShoot() == Input.Buttons.LEFT) {
            shoot = "left mouse click";
        } else {
            shoot = "enter";
        }
        return Map.of(
            "Move Up", Input.Keys.toString(Game.getKeyUp()),
            "Move Down", Input.Keys.toString(Game.getKeyDown()),
            "Move Left", Input.Keys.toString(Game.getKeyLeft()),
            "Move Right", Input.Keys.toString(Game.getKeyRight()),
            "Reload", Input.Keys.toString(Game.getReloadGun()),
            "Shoot", shoot
        );
    }
}
