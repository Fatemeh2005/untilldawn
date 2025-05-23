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
import com.tilldawn.Model.Weapon.WeaponTypes;

import javax.swing.event.ChangeEvent;

public class PreGameMenuView implements Screen {

    private Stage stage;
    private final Label gameTitle;
    private final TextButton playButton;
    private final SelectBox selectHero;
    private final SelectBox selectGuns;
    private final SelectBox<String> selectTime;
    public Table table;
    private PreGameMenuController controller;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.gameTitle = new Label("Pregame Menu", skin);
        this.selectHero = new SelectBox<>(skin);
        this.selectGuns = new SelectBox<>(skin);
        this.selectTime = new SelectBox<>(skin);
        this.playButton = new TextButton("Play", skin);
        this.table = new Table();
        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Array<String> hero = new Array<>();

        hero.add("Shana");
        hero.add("Diamond");
        hero.add("Scarlet");
        hero.add("Lilith");
        hero.add("Dasher");

        Array<String> guns = new Array<>();

        guns.add("Revolver");
        guns.add("Shot Gun");
        guns.add("SMGs Dual");

        selectGuns.setItems(guns);
        selectHero.setItems(hero);

        Array<String> times = new Array<>();
        times.add("2 minutes");
        times.add("5 minutes");
        times.add("10 minutes");
        times.add("20 minutes");
        selectTime.setItems(times);


        table.setFillParent(true);
        table.center();
        table.add(gameTitle);
        table.row().pad(10, 0 , 10 , 0);
        table.add(selectHero);
        table.row().pad(10, 0 , 10 , 0);
        table.add(selectGuns);
        table.row().pad(10, 0 , 10 , 0);
        table.add(selectTime);
        table.row().pad(10, 0 , 10 , 0);
        table.add(playButton);
        table.row().pad(10, 0 , 10 , 0);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String selectedTimeString = selectTime.getSelected(); // e.g., "5 minutes"
                int minutes = Integer.parseInt(selectedTimeString.split(" ")[0]);
                Game.setSelectedGameTimeInMinutes(minutes);
                controller.handlePlayButton();
            }
        });


        selectGuns.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String selectedWeapon = (String) selectGuns.getSelected();
                controller.handleWeaponChangeButton(WeaponTypes.findWeaponTypeByName(selectedWeapon));
            }
        });

        selectHero.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedHero = selectHero.getSelected().toString();
                System.out.println("Selected Hero: " + selectedHero);
                controller.handlePlayerChangeButton(selectedHero);
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
    public void resize(int width, int height) {

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

}
