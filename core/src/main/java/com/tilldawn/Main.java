package com.tilldawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
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
        setCustomCursor();

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

    private void setCustomCursor() {
        try {
            Pixmap pixmap = new Pixmap(Gdx.files.internal("cursor.png")); // put image in assets/
            int xHotspot = 0; // Adjust this if needed
            int yHotspot = 0;
            Cursor customCursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
            Gdx.graphics.setCursor(customCursor);
            pixmap.dispose(); // safe to dispose Pixmap after creating Cursor
        } catch (Exception e) {
            Gdx.app.error("CursorError", "Failed to load custom cursor", e);
        }
    }

}
