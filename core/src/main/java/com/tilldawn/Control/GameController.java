    package com.tilldawn.Control;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.Animation;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.tilldawn.Model.Player;
    import com.tilldawn.Model.PlayerTypes;
    import com.tilldawn.Model.Weapon;
    import com.tilldawn.View.GameView;

    public class GameController {
        private PlayerController playerController;
        private WorldController worldController;
        private WeaponController weaponController;
        private GameView view;

        public void setView(GameView view, PlayerTypes type, Animation<Texture> animation) {
            this.view = view;
            Player player = new Player(animation, type);
            this.playerController = new PlayerController(player);
            this.worldController = new WorldController(playerController);
            this.weaponController = new WeaponController(new Weapon());
        }

        public void updateGame() {
            if (view != null) {
                worldController.update();
                playerController.update();
                weaponController.update();
            }
        }

        public void renderWorld(SpriteBatch batch) {
            worldController.render(batch);
            playerController.render(batch);
            weaponController.update();
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
