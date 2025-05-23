package com.tilldawn.Control;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.PlayerTypes;
import com.tilldawn.Model.Pregame;
import com.tilldawn.Model.Weapon.Weapon;
import com.tilldawn.Model.Weapon.WeaponTypes;
import com.tilldawn.View.GameView;
import com.tilldawn.View.PreGameMenuView;

public class PreGameMenuController {
    private PreGameMenuView view;
    private int selectedGameTimeInMinutes;


    public void setView(PreGameMenuView view) {
        this.view = view;
    }

    public void handlePlayButton() {
        if (view != null) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new GameView(new GameController()));
        }
    }
    public void handleWeaponChangeButton(WeaponTypes type) {
        if (view != null) {
            if(type != null) {
                Game.getPlayer().setWeapon(new Weapon(type));
            }
        }
    }

    public void handlePlayerChangeButton(String type) {
        if (view != null) {
            PlayerTypes player = null;
            switch (type) {
                case "Shana": player = PlayerTypes.SHANA; break;
                case "Diamond": player = PlayerTypes.DIAMOND; break;
                case "Scarlet": player = PlayerTypes.SCARLET; break;
                case "Lilith": player = PlayerTypes.LILITH; break;
                case "Dasher": player = PlayerTypes.DASHER; break;
            }

            if (player != null) {
                Game.getPlayer().setPlayerTexture(player.getAvatarTexture());

                Sprite sprite = new Sprite(Game.getPlayer().getPlayerTexture());
                sprite.setPosition(Game.getPlayer().getPosX(), Game.getPlayer().getPosY()); // Sync sprite position
                sprite.setSize(Game.getPlayer().getPlayerTexture().getWidth() * 3, Game.getPlayer().getPlayerTexture().getHeight() * 3);
                Game.getPlayer().setPlayerSprite(sprite);

                Game.getPlayer().setPlayerType(player);
                Game.getPlayer().setAnimations(player.getAnimation()); // Update animation properly
            }
        }
    }

    public int getSelectedGameTimeInMinutes() {
        return selectedGameTimeInMinutes;
    }
}
