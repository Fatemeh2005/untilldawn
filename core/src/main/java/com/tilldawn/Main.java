package com.tilldawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.RegisterMenuController;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.RegisterMenuView;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private static ShaderProgram grayShader;
    private static boolean grayscaleEnabled = false;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();

       // ShaderProgram.pedantic = false;
        grayShader = new ShaderProgram(
            Gdx.files.internal("gray.vert"),
            Gdx.files.internal("gray.frag")
        );

        if (!grayShader.isCompiled()) {
            Gdx.app.error("ShaderError", grayShader.getLog());
        }
        main.setScreen(new RegisterMenuView(new RegisterMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        grayShader.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public static ShaderProgram getGrayShader() {
        return grayShader;
    }

    public static void setGrayShader(ShaderProgram grayShader) {
        Main.grayShader = grayShader;
    }

    public static boolean isGrayscaleEnabled() {
        return grayscaleEnabled;
    }

    public static void setGrayscaleEnabled(boolean grayscaleEnabled) {
        Main.grayscaleEnabled = grayscaleEnabled;
    }
}
