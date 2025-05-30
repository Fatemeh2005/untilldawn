package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.Gson;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.User;

import java.util.*;
import java.util.List;


public class ScoreBoardView implements Screen {
    private final Skin skin;                 // may lack SelectBox / custom label styles
    private final MainMenuController controller;

    private Stage stage;
    private SelectBox<String> sortSelect;
    private Table dataTable;

    private final List<User> users = new ArrayList<>();

    public ScoreBoardView(Skin skin, MainMenuController controller) {
        this.skin = skin;
        this.controller = controller;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        loadUsers();

        sortSelect = buildSortSelect();
        dataTable  = new Table();
        // ensure the table has a skin so header shortcuts work
        dataTable.setSkin(skin);

        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(4);
        stage.addActor(root);

        root.add(makeLabel("Score Board", "title")).colspan(2);
        root.row();

        root.add(makeLabel("Sort by:", null));
        root.add(sortSelect).left();
        root.row();

        ScrollPane scroll = new ScrollPane(dataTable, skin);
        scroll.setFadeScrollBars(false);
        root.add(scroll).colspan(2).expand().fill();
        root.row();

        TextButton back = new TextButton("Back", skin);
        back.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(controller, skin));
            }
        });
        root.add(back).colspan(2).padTop(10);

        refreshTable();
    }

    /* ---------------- helpers ------------------------ */
    private SelectBox<String> buildSortSelect() {
        Skin selectSkin = skin.has("default", SelectBox.SelectBoxStyle.class)
            ? skin
            : new Skin(Gdx.files.internal("uiskin.json"));

        SelectBox<String> box = new SelectBox<>(selectSkin);
        box.setItems("Username", "Score", "Kills", "Time survived");
        box.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) { refreshTable(); }
        });
        return box;
    }

    private Label makeLabel(String text, String style) {
        if (style != null && skin.has(style, Label.LabelStyle.class)) {
            return new Label(text, skin, style);
        }
        return new Label(text, skin);
    }

    private void loadUsers() {
        users.clear();
        Gson gson = new Gson();
        FileHandle dir = Gdx.files.local("users");
        if (!dir.exists()) return;
        for (FileHandle file : dir.list("json")) {
            try {
                User u = gson.fromJson(file.readString(), User.class);
                if (u != null) users.add(u);
            } catch (Exception ignored) { }
        }
    }

    private void refreshTable() {
        String mode = sortSelect.getSelected();
        Comparator<User> cmp;
        if ("Score".equals(mode)) {
            cmp = Comparator.comparingInt(User::getScore).reversed();
        } else if ("Kills".equals(mode)) {
            cmp = Comparator.comparingInt(User::getNumberOfKills).reversed();
        } else if ("Time survived".equals(mode)) {
            cmp = Comparator.comparingInt(User::getMostTimeSurvived).reversed();
        } else {
            cmp = Comparator.comparing(User::getUsername, String.CASE_INSENSITIVE_ORDER);
        }
        users.sort(cmp);

        dataTable.clear();
        dataTable.top();

        dataTable.add(makeLabel("#", "default-bold")).padRight(10);
        dataTable.add(makeLabel("Username", "default-bold")).padRight(10);
        dataTable.add(makeLabel("Score", "default-bold")).padRight(10);
        dataTable.add(makeLabel("Kills", "default-bold")).padRight(10);
        dataTable.add(makeLabel("Time", "default-bold"));
        dataTable.row();

        int rank = 1;
        for (User u : users) {
            Label rankLabel     = new Label(Integer.toString(rank), skin);
            Label usernameLabel;
            if( Game.getCurrentUser() != null && u.getUsername().equals(Game.getCurrentUser().getUsername())) {
                 usernameLabel = new Label("->" +u.getUsername(), skin);
            } else {
                 usernameLabel = new Label(u.getUsername(), skin);
            }

            Label scoreLabel    = new Label(Integer.toString(u.getScore()), skin);
            Label killsLabel    = new Label(Integer.toString(u.getNumberOfKills()), skin);
            Label timeLabel     = new Label(formatTime(u.getMostTimeSurvived()), skin);

            // Highlight top 3 users with a custom style or color
            if (rank == 1) {
                setAllColors( 1.0f, 0.84f, 0.0f,rankLabel, usernameLabel, scoreLabel, killsLabel, timeLabel); // Gold
            } else if (rank == 2) {
                setAllColors( 0.75f, 0.75f, 0.75f,rankLabel, usernameLabel, scoreLabel, killsLabel, timeLabel); // Silver
            } else if (rank == 3) {
                setAllColors( 0.80f, 0.5f, 0.2f, rankLabel, usernameLabel, scoreLabel, killsLabel, timeLabel); // Bronze
            }


            dataTable.add(rankLabel);
            dataTable.add(usernameLabel);
            dataTable.add(scoreLabel);
            dataTable.add(killsLabel);
            dataTable.add(timeLabel);
            dataTable.row();
            rank++;
        }
    }
    private void setAllColors(float r, float g, float b, Label... labels) {
        for (Label label : labels) {
            label.setColor(r, g, b, 1.0f);
        }
    }


    private String formatTime(int seconds) {
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    @Override public void render(float delta) { ScreenUtils.clear(0,0,0,1); stage.act(delta); stage.draw(); }
    @Override public void resize(int w,int h){ stage.getViewport().update(w,h,true);}
    @Override public void pause(){}
    @Override public void resume(){}
    @Override public void hide(){}
    @Override public void dispose(){ stage.dispose(); }
}
