package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.PreGameMenuController;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.Weapon.Weapon;
import com.tilldawn.Model.Weapon.WeaponTypes;

import javax.swing.event.ChangeEvent;
import java.util.function.BiFunction;

public class PreGameMenuView implements Screen {

    private Stage stage;
    private final Label           gameTitle;
    private final TextButton      playButton;
    private final SelectBox<String> selectHero;
    private final SelectBox<String> selectGuns;
    private final SelectBox<String> selectTime;
    public  final Table           table;
    private final PreGameMenuController controller;

    /* ─── tiny helper: t(en, fr) returns the correct language string ─── */
    private final BiFunction<String, String, String> t =
        (en, fr) -> Game.isIsFrench() ? fr : en;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.gameTitle   = new Label(t.apply("Pregame Menu", "Menu de préparation"), skin);
        this.selectHero  = new SelectBox<>(skin);
        this.selectGuns  = new SelectBox<>(skin);
        this.selectTime  = new SelectBox<>(skin);
        this.playButton  = new TextButton(t.apply("Play", "Jouer"), skin);
        this.table       = new Table();
        this.controller  = controller;

        controller.setView(this);   // let the controller know about its view
    }

    @Override public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Array<String> hero = new Array<>();
        hero.addAll("Shana", "Diamond", "Scarlet", "Lilith", "Dasher");
        selectHero.setItems(hero);

        Array<String> guns = new Array<>();
        guns.add(t.apply("Revolver",            "Revolver"));
        guns.add(t.apply("Shot Gun",            "Fusil à pompe"));
        guns.add(t.apply("SMGs Dual",           "Mitraillettes doubles"));
        selectGuns.setItems(guns);

        Array<String> times = new Array<>();
        times.add(t.apply("1 minute",  "1 minute"));
        times.add(t.apply("2 minutes", "2 minutes"));
        times.add(t.apply("5 minutes", "5 minutes"));
        times.add(t.apply("10 minutes","10 minutes"));
        times.add(t.apply("20 minutes","20 minutes"));
        selectTime.setItems(times);

        table.setFillParent(true);
        table.center();
        table.add(gameTitle);
        table.row().pad(10, 0, 10, 0);
        table.add(selectHero);
        table.row().pad(10, 0, 10, 0);
        table.add(selectGuns);
        table.row().pad(10, 0, 10, 0);
        table.add(selectTime);
        table.row().pad(10, 0, 10, 0);
        table.add(playButton);
        stage.addActor(table);

        /* -------------------------- Listeners ----------------------- */
        playButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                String selectedTimeString = selectTime.getSelected();   // "5 minute(s)"
                int minutes = Integer.parseInt(selectedTimeString.split(" ")[0]);
                Game.setSelectedGameTimeInMinutes(minutes);
                controller.handlePlayButton();
            }
        });

        selectGuns.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                String selectedGun = selectGuns.getSelected();
                Game.getPlayer().setWeapon(
                    new Weapon(WeaponTypes.findWeaponTypeByName(selectedGun)));
            }
        });

        selectHero.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                controller.handlePlayerChangeButton(selectHero.getSelected());
            }
        });
    }

    /* ─── Screen boilerplate ──────────────────────────────────────── */
    @Override public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
    @Override public void resize(int w, int h) { }
    @Override public void pause()             { }
    @Override public void resume()            { }
    @Override public void hide()              { }
    @Override public void dispose()           { }
}
