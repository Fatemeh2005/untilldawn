    package com.tilldawn.View;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.InputProcessor;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.tilldawn.Control.GameController;
    import com.tilldawn.Main;

    public class GameView implements Screen, InputProcessor {
        private GameController controller;
        private Stage stage;
        private OrthographicCamera camera;

        public GameView(GameController controller) {
            Gdx.input.setInputProcessor(this);
            this.controller = controller;
            this.controller.setView(this);
            this.stage = new Stage();
            this.camera = new OrthographicCamera();
            this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        @Override
        public void render(float delta) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            Main.getBatch().setProjectionMatrix(camera.combined);

            Main.getBatch().begin();

            controller.renderWorld();

            controller.updateGame(camera);
            Main.getBatch().end();


            stage.act(delta);
            stage.draw();

        }

        @Override
        public void resize(int width, int height) {
            stage.getViewport().update(width, height, true);
        }

        @Override
        public boolean keyDown(int i) {
            return false;
        }

        @Override
        public boolean keyUp(int i) {
            return false;
        }

        @Override
        public boolean keyTyped(char c) {
            return false;
        }

        @Override
        public boolean touchUp(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchCancelled(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchDragged(int i, int i1, int i2) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            controller.getWeaponController().handleWeaponRotation(screenX, screenY);
            return false;
        }

        @Override
        public boolean scrolled(float v, float v1) {
            return false;
        }

        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            controller.getWeaponController().handleWeaponShoot(screenX, screenY);
            return false;
        }

        // Other required Screen methods...
        @Override public void show() {}
        @Override public void pause() {}
        @Override public void resume() {}
        @Override public void hide() {}
        @Override public void dispose() {}

        public OrthographicCamera getCamera() {
            return camera;
        }
    }
