package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.SettingsController;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;

import java.util.function.BiFunction;

public class SettingsMenuView implements Screen {

    private final BiFunction<String, String, String> t =
        (en, fr) -> Game.isIsFrench() ? fr : en;

    private final Stage stage;
    private final Skin skin;
    private final SettingsController controller;

    public SettingsMenuView() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = GameAssetManager.getGameAssetManager().getSkin();
        controller = new SettingsController();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.defaults().pad(5);

        Label musicLabel = new Label(t.apply("Music Volume", "Volume musique"), skin);
        Slider musicSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicSlider.setValue(controller.getMusicVolume());
        musicSlider.addListener(event -> {
            controller.setMusicVolume(musicSlider.getValue());
            return false;
        });

        Label sfxLabel = new Label(t.apply("SFX Volume", "Volume effets"), skin);
        Slider sfxSlider = new Slider(0f, 1f, 0.01f, false, skin);
        sfxSlider.setValue(controller.getSfxVolume());
        sfxSlider.addListener(event -> {
            controller.setSfxVolume(sfxSlider.getValue());
            return false;
        });

        TextButton nextMusicButton = new TextButton(t.apply("Next Music", "Musique suivante"), skin);
        nextMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                controller.playNextMusic();
                e.stop();
            }
        });

        TextButton blackWhite = new TextButton(t.apply("make it black and white", "Noir et blanc"), skin);
        blackWhite.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                Main.setGrayscaleEnabled(true);
            }
        });

        SelectBox<String> selectUpKey = new SelectBox<>(skin);
        SelectBox<String> selectDownKey = new SelectBox<>(skin);
        SelectBox<String> selectRightKey = new SelectBox<>(skin);
        SelectBox<String> selectLeftKey = new SelectBox<>(skin);
        SelectBox<String> selectShootKey = new SelectBox<>(skin);
        SelectBox<String> selectReloadKey = new SelectBox<>(skin);
        SelectBox<String> selectLanguage = new SelectBox<>(skin);

        Array<String> up = new Array<>(new String[]{
            "W", t.apply("up arrow", "flèche haut")});
        Array<String> down = new Array<>(new String[]{
            "S", t.apply("down arrow", "flèche bas")});
        Array<String> right = new Array<>(new String[]{
            "D", t.apply("right arrow", "flèche droite")});
        Array<String> left = new Array<>(new String[]{
            "A", t.apply("left arrow", "flèche gauche")});
        Array<String> shoot = new Array<>(new String[]{
            t.apply("left mouse click", "clic souris gauche"),
            t.apply("enter", "entrée")});
        Array<String> reload = new Array<>(new String[]{
            "R", t.apply("back space", "retour arrière")});
        Array<String> language = new Array<>(new String[]{
            "English", "French"
        });

        selectUpKey.setItems(up);
        selectDownKey.setItems(down);
        selectRightKey.setItems(right);
        selectLeftKey.setItems(left);
        selectShootKey.setItems(shoot);
        selectReloadKey.setItems(reload);
        selectLanguage.setItems(language);

        /* Listeners assign chosen keys to Game static fields */
        selectUpKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                Game.setKeyUp(selectUpKey.getSelected().equals("W") ? Input.Keys.W : Input.Keys.UP);
            }
        });
        selectDownKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                Game.setKeyDown(selectDownKey.getSelected().equals("S") ? Input.Keys.S : Input.Keys.DOWN);
            }
        });
        selectRightKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                Game.setKeyRight(selectRightKey.getSelected().equals("D") ? Input.Keys.D : Input.Keys.RIGHT);
            }
        });
        selectLeftKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                Game.setKeyLeft(selectLeftKey.getSelected().equals("A") ? Input.Keys.A : Input.Keys.LEFT);
            }
        });
        selectShootKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                Game.setShoot(selectShootKey.getSelected().startsWith("left") || selectShootKey.getSelected().startsWith("clic")
                    ? Input.Buttons.LEFT : Input.Keys.ENTER);
            }
        });
        selectReloadKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                Game.setReloadGun(selectReloadKey.getSelected().equals("R") ? Input.Keys.R : Input.Keys.BACKSPACE);
            }
        });

//        TextButton autoReloadButton = new TextButton(t.apply("enable auto reload",
//            "Activer rechargement auto"), skin);
//        autoReloadButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent e, float x, float y) {
//                Game.setAutoReloadOn(true);
//                e.stop();
//            }
//        });

        final TextButton autoReloadButton = new TextButton(
            Game.isAutoReloadOn() ?
                t.apply("Disable Auto Reload", "Désactiver rechargement auto") :
                t.apply("Enable Auto Reload", "Activer rechargement auto"),
            skin);

        autoReloadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                boolean current = Game.isAutoReloadOn();
                Game.setAutoReloadOn(!current);
                autoReloadButton.setText(
                    !current ?
                        t.apply("Disable Auto Reload", "Désactiver rechargement auto") :
                        t.apply("Enable Auto Reload", "Activer rechargement auto"));
                e.stop();
            }
        });


        TextButton backButton = new TextButton(t.apply("Back", "Retour"), skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                controller.goBackToMainMenu();
                e.stop();
            }
        });
        selectLanguage.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                boolean french = selectLanguage.getSelected()
                    .equalsIgnoreCase("French");
                Game.setIsFrench(french);
                System.out.println("now lang = "
                    + (french ? "French" : "English"));
                Main.getMain().setScreen(new SettingsMenuView());
            }
        });

        table.add(musicLabel).left().padRight(10);
        table.add(musicSlider).width(250).row();
        table.add(sfxLabel).left().padRight(10);
        table.add(sfxSlider).width(250).row();
        table.add(nextMusicButton).colspan(2).padTop(8).row();
        Table keys = new Table();
        keys.defaults().pad(3);
        keys.add(selectUpKey).width(240);
        keys.add(selectDownKey).width(240).row();
        keys.add(selectLeftKey).width(240);
        keys.add(selectRightKey).width(240).row();
        keys.add(selectShootKey).width(240);
        keys.add(selectReloadKey).width(240);
        table.add(keys).colspan(2).padTop(12).row();
        table.add(autoReloadButton).width(500).padTop(5).row();
        table.add(blackWhite).width(500).padTop(5).row();
        table.add(selectLanguage).width(500).padTop(5).row();
        table.add(backButton).padTop(15).row();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int w, int h) {
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
        skin.dispose();
    }
}
