package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.AudioManager;
import com.tilldawn.Model.Game;

import java.util.function.BiFunction;

public class GameView implements Screen, InputProcessor {

    /* ─── tiny helper:  t(en, fr)  ─────────────────────────────────── */
    private final BiFunction<String,String,String> t =
        (en, fr) -> Game.isIsFrench() ? fr : en;

    private final GameController controller;
    private Stage stage;
    private static final OrthographicCamera camera = new OrthographicCamera();

    public GameView(GameController controller) {
        Gdx.input.setInputProcessor(this);
        this.controller = controller;
        this.controller.setView(this);
        this.stage = new Stage();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /* ───────────────────────── rendering, input, boilerplate ─────── */
    @Override public void render(float delta) {
        if (Main.isGrayscaleEnabled()) Main.getBatch().setShader(Main.getGrayShader());
        else                           Main.getBatch().setShader(null);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Main.getBatch().setProjectionMatrix(camera.combined);

        Main.getBatch().begin();
        controller.renderWorld();
        controller.updateGame(camera);
        Main.getBatch().end();

        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int w,int h){ stage.getViewport().update(w,h,true); }
    @Override public boolean keyDown(int i){ return false; }
    @Override public boolean keyUp(int i){ return false; }
    @Override public boolean keyTyped(char c){ return false; }
    @Override public boolean touchDown(int i,int i1,int i2,int i3){ return false; }
    @Override public boolean touchUp(int i,int i1,int i2,int i3){ return false; }
    @Override public boolean touchCancelled(int i,int i1,int i2,int i3){ return false; }
    @Override public boolean touchDragged(int i,int i1,int i2){ return false; }
    @Override public boolean mouseMoved(int x,int y){
        controller.getWeaponController().handleWeaponRotation(x,y); return false;
    }
    @Override public boolean scrolled(float v,float v1){ return false; }
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }

    public static OrthographicCamera getCamera(){ return camera; }
    public Stage getStage(){ return stage; }
    public void  setStage(Stage s){ this.stage=s; }

    /* ───────────────────────── Ability-choice popup ───────────────── */
    public void printAbilitiesMenu(Skin skin) {

        Game.setGamePaused(true);
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton vitality  = new TextButton(t.apply("Vitality",  "Vitalité"),  skin);
        TextButton damager   = new TextButton(t.apply("Damager",   "Dégâts +"),  skin);
        TextButton procrease = new TextButton(t.apply("Procrease", "Proj. +"),   skin);
        TextButton amocrease = new TextButton(t.apply("Amocrease", "Munitions +"), skin);
        TextButton speedy    = new TextButton(t.apply("Speedy",    "Rapide"),    skin);

        vitality.addListener(new ClickListener(){ @Override public void clicked(InputEvent e,float x,float y){
            Game.getPlayer().setLevelUp(false);
            Game.getPlayer().setPlayerHealth(Game.getPlayer().getPlayerHealth()+1);
            if(!Game.getPlayer().getAbilitiesGained().contains("Vitality"))
                Game.getPlayer().getAbilitiesGained().add("Vitality");
            removeAbilitiesMenu(); Game.setGamePaused(false);
        }});
        damager.addListener(new ClickListener(){ @Override public void clicked(InputEvent e,float x,float y){
            Game.getPlayer().setLevelUp(false);
            Game.getPlayer().getWeapon().setPowerBoostActive(true);
            if(!Game.getPlayer().getAbilitiesGained().contains("Damager"))
                Game.getPlayer().getAbilitiesGained().add("Damager");
            removeAbilitiesMenu(); Game.setGamePaused(false);
        }});
        procrease.addListener(new ClickListener(){ @Override public void clicked(InputEvent e,float x,float y){
            Game.getPlayer().setLevelUp(false);
            Game.getPlayer().getWeapon().setProjectile(
                Game.getPlayer().getWeapon().getProjectile()+1);
            if(!Game.getPlayer().getAbilitiesGained().contains("Procrease"))
                Game.getPlayer().getAbilitiesGained().add("Procrease");
            removeAbilitiesMenu(); Game.setGamePaused(false);
        }});
        amocrease.addListener(new ClickListener(){ @Override public void clicked(InputEvent e,float x,float y){
            Game.getPlayer().setLevelUp(false);
            Game.getPlayer().getWeapon().setAmmo(
                Game.getPlayer().getWeapon().getAmmo()+5);
            if(!Game.getPlayer().getAbilitiesGained().contains("Amocrease"))
                Game.getPlayer().getAbilitiesGained().add("Amocrease");
            removeAbilitiesMenu(); Game.setGamePaused(false);
        }});
        speedy.addListener(new ClickListener(){ @Override public void clicked(InputEvent e,float x,float y){
            Game.getPlayer().setLevelUp(false);
            Game.getPlayer().setSpeedBoostActive(true);
            if(!Game.getPlayer().getAbilitiesGained().contains("Speedy"))
                Game.getPlayer().getAbilitiesGained().add("Speedy");
            removeAbilitiesMenu(); Game.setGamePaused(false);
        }});

        table.add(vitality).fillX().pad(10);  table.row();
        table.add(damager).fillX().pad(10);   table.row();
        table.add(procrease).fillX().pad(10); table.row();
        table.add(amocrease).fillX().pad(10); table.row();
        table.add(speedy).fillX().pad(10);
    }
    private void removeAbilitiesMenu(){
        stage.clear(); Gdx.input.setInputProcessor(this);
    }

    /* ───────────────────────── Pause menu ─────────────────────────── */
    public void PauseMenu(Skin skin){
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table(); table.setFillParent(true); stage.addActor(table);

        TextButton resume  = new TextButton(t.apply("Resume",  "Reprendre"), skin);
        TextButton giveUp  = new TextButton(t.apply("Give Up", "Abandonner"), skin);
        TextButton blackW  = new TextButton(t.apply("make it black and white",
            "Noir et blanc"), skin);
        TextButton save    = new TextButton(t.apply("save and exit",
            "Sauvegarder et quitter"), skin);

        resume.addListener(new ClickListener(){ @Override public void clicked(InputEvent e,float x,float y){
            removePauseMenu(); Game.setPausePressed(false);
        }});
        giveUp.addListener(new ClickListener(){ @Override public void clicked(InputEvent e,float x,float y){
            Game.getPlayer().setDead(true);
            AudioManager.getInstance().playLoseSound();
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new loseGameMenu());
            Game.setPausePressed(false);
        }});
        blackW.addListener(new ChangeListener(){ @Override public void changed(ChangeEvent e, Actor a){
            Main.setGrayscaleEnabled(true); Game.setPausePressed(false);
        }});
        save.addListener(new ChangeListener(){ @Override public void changed(ChangeEvent e, Actor a){
            controller.saveAndExit(); Game.setPausePressed(false);
        }});

        String abilities = Game.getPlayer().getAbilitiesGained().isEmpty()
            ? t.apply("None", "Aucune")
            : String.join(", ", Game.getPlayer().getAbilitiesGained());

        Label abilitiesLabel = new Label(
            t.apply("Abilities Gained:\n", "Capacités acquises :\n") + abilities, skin);
        abilitiesLabel.setWrap(true);

        String cheatTextEn =
            "Cheat Codes:\n" +
                "H = Survive when about to die\n" +
                "L = Level up\n" +
                "Alt Left = Increase HP anytime\n" +
                "T = Pass one minute\n" +
                "X = Increase XP\n" +
                "Comma = +28 seconds";
        String cheatTextFr =
            "Codes de triche :\n" +
                "H = Survivre juste avant la mort\n" +
                "L = Monter de niveau\n" +
                "Alt gauche = Augmenter les PV à tout moment\n" +
                "T = Passer une minute\n" +
                "X = Augmenter l'XP\n" +
                "Virgule = +28 secondes";

        Label cheatsLabel = new Label(t.apply(cheatTextEn, cheatTextFr), skin);
        cheatsLabel.setWrap(true);

        table.add(resume).fillX().pad(10); table.row();
        table.add(giveUp).fillX().pad(10); table.row();
        table.add(blackW).fillX().pad(10); table.row();
        table.add(save).fillX().pad(10);   table.row();
        table.add(abilitiesLabel).width(400).padTop(20); table.row();
        table.add(cheatsLabel).width(400).padTop(10);
    }
    private void removePauseMenu(){
        stage.clear();
        Gdx.input.setInputProcessor(this);
    }
}
