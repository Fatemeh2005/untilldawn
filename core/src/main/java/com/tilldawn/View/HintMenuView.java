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
import java.util.function.BiFunction;

public class HintMenuView implements Screen {

    private final Skin skin;
    private Stage stage;
    private final MainMenuController controller;

    /* tiny helper: t(en, fr) */
    private final BiFunction<String, String, String> t =
        (en, fr) -> Game.isIsFrench() ? fr : en;

    public HintMenuView(Skin skin, MainMenuController controller) {
        this.skin       = skin;
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

        /* ─── Title ────────────────────────────────────────────────── */
        Label title = new Label(t.apply("Hints & Help", "Astuces et aide"), skin, "title");
        root.add(title).padBottom(15);
        root.row();

        /* ─── Hero tips ───────────────────────────────────────────── */
        root.add(new Label(t.apply("Hero tips", "Conseils héros"), skin, "subtitle"))
            .padBottom(8);
        root.row();

        String[] heroHints = {
            t.apply("• Shana: HP:4   Speed:4",
                "• Shana : PV : 4   Vitesse : 4"),
            t.apply("• Diamond: HP:7   Speed:1",
                "• Diamond : PV : 7   Vitesse : 1"),
            t.apply("• Scarlet: HP:5   Speed:3",
                "• Scarlet : PV : 5   Vitesse : 3")
        };
        for (String h : heroHints) {
            root.add(new Label(h, skin)).padBottom(4);
            root.row();
        }

        /* ─── Current key bindings ───────────────────────────────── */
        root.add(new Label(t.apply("Current key bindings",
                "Touches actuelles"), skin, "subtitle"))
            .padTop(12).padBottom(8);
        root.row();

        getCurrentKeyBindings().forEach((action, key) -> {
            root.add(new Label(action + " : " + key, skin)).padBottom(3);
            root.row();
        });

        /* ─── Cheat codes ────────────────────────────────────────── */
        root.add(new Label(t.apply("Cheat codes", "Codes de triche"),
                skin, "subtitle"))
            .padTop(12).padBottom(8);
        root.row();

        Map<String, String> cheats = Map.of(
            "H",  t.apply("makes you survive right when you are about to die",
                "vous sauve juste avant la mort"),
            "L",  t.apply("for level up",
                "niveau supérieur"),
            "Alt left", t.apply("increase HP any time",
                "augmente les PV à tout moment"),
            "T",  t.apply("one minute passes",
                "fait passer une minute"),
            "X",  t.apply("increases XP",
                "augmente l'XP"),
            "Comma", t.apply("increases time 28 seconds",
                "ajoute 28 secondes")
        );
        cheats.forEach((code, effect) -> {
            root.add(new Label(code + " – " + effect, skin)).padBottom(3);
            root.row();
        });

        /* ─── Ability descriptions ──────────────────────────────── */
        root.add(new Label(t.apply("Ability descriptions",
                "Descriptions des capacités"),
                skin, "subtitle"))
            .padTop(12).padBottom(8);
        root.row();

        Map<String, String> abilities = Map.of(
            "VITALITY",
            t.apply("Increase max HP by your current HP (double total HP)",
                "Augmente les PV max de vos PV actuels (double les PV totaux)"),
            "DAMAGER",
            t.apply("Increase weapon damage by 25% for 10 seconds",
                "Augmente les dégâts de l'arme de 25 % pendant 10 s"),
            "PROCREASE",
            t.apply("Increase projectile speed of the weapon by 1 unit",
                "Augmente la vitesse des projectiles de 1 unité"),
            "AMOCREASE",
            t.apply("Increase weapon's maximum ammo capacity by 5",
                "Augmente la capacité maximale de munitions de 5"),
            "SPEEDY",
            t.apply("Double movement speed for 10 seconds",
                "Double la vitesse de déplacement pendant 10 s")
        );
        abilities.forEach((name, desc) -> {
            root.add(new Label(name + " – " + desc, skin)).padBottom(3);
            root.row();
        });

        /* ─── Back button ────────────────────────────────────────── */
        TextButton back = new TextButton(t.apply("Back", "Retour"), skin);
        root.add(back).padTop(20);
        back.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(controller, skin));
            }
        });

        /* ─── Scroll container ───────────────────────────────────── */
        ScrollPane scrollPane = new ScrollPane(root, skin);
        scrollPane.setFadeScrollBars(false);

        Table container = new Table();
        container.setFillParent(true);
        container.add(scrollPane).expand().fill();

        stage.addActor(container);
    }

    /* ─── Screen boilerplate ───────────────────────────────────────── */
    @Override public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        stage.draw();
    }
    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void pause()   {}
    @Override public void resume()  {}
    @Override public void hide()    {}
    @Override public void dispose() { stage.dispose(); }

    /* ─── helpers ─────────────────────────────────────────────────── */
    private Map<String, String> getCurrentKeyBindings() {
        String shoot = (Game.getShoot() == Input.Buttons.LEFT)
            ? t.apply("left mouse click", "clic souris gauche")
            : t.apply("enter", "entrée");

        return Map.of(
            t.apply("Move Up",    "Haut"),    Input.Keys.toString(Game.getKeyUp()),
            t.apply("Move Down",  "Bas"),     Input.Keys.toString(Game.getKeyDown()),
            t.apply("Move Left",  "Gauche"),  Input.Keys.toString(Game.getKeyLeft()),
            t.apply("Move Right", "Droite"),  Input.Keys.toString(Game.getKeyRight()),
            t.apply("Reload",     "Recharger"), Input.Keys.toString(Game.getReloadGun()),
            t.apply("Shoot",      "Tirer"), shoot
        );
    }
}
