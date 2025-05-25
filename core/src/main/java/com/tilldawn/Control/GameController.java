    package com.tilldawn.Control;

    import com.badlogic.gdx.graphics.Camera;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.Animation;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.tilldawn.Model.Game;
    import com.tilldawn.Model.Player;
    import com.tilldawn.Model.PlayerTypes;
    import com.tilldawn.Model.Weapon.Weapon;
    import com.tilldawn.View.GameView;

    public class GameController {
        private PlayerController playerController;
        private WorldController worldController;
        private WeaponController weaponController;
        private GameView view;

        public void setView(GameView view) {
            this.view = view;
            this.playerController = new PlayerController(this);
            this.worldController = new WorldController(view);
            this.weaponController = new WeaponController(view);
        }

        public void updateGame(Camera camera) {
            if (view != null) {
                worldController.update(camera);
                playerController.update();
                weaponController.update();
            }
        }

        public void renderWorld() {
            worldController.render();
            //playerController.render(batch);
            //weaponController.update();
        }

        public PlayerController getPlayerController() {
            return playerController;
        }

        public WeaponController getWeaponController() {
            return weaponController;
        }

        public WorldController getWorldController() {
            return worldController;
        }
    }
