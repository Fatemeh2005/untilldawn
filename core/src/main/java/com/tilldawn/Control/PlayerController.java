package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Main;
import com.tilldawn.Model.Game;
import com.tilldawn.Model.Player;

public class PlayerController {

    public void update() {
        handlePlayerInput();
        Game.getPlayer().getPlayerSprite().setPosition(Game.getPlayer().getPosX() , Game.getPlayer().getPosY());
        Game.getPlayer().getPlayerSprite().draw(Main.getBatch());
    }

public void handlePlayerInput() {
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        Game.getPlayer().setPosY(Game.getPlayer().getPosY() + Game.getPlayer().getSpeed()); // ⬆️ Move up (now correct)
        idleAnimation(Game.getPlayer().getAnimations());
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        Game.getPlayer().setPosY(Game.getPlayer().getPosY() - Game.getPlayer().getSpeed()); // ⬇️ Move down (now correct)
        idleAnimation(Game.getPlayer().getAnimations());
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        Game.getPlayer().setPosX(Game.getPlayer().getPosX() + Game.getPlayer().getSpeed()); // ➡️ Move right (correct)
        idleAnimation(Game.getPlayer().getAnimations());
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        Game.getPlayer().setPosX(Game.getPlayer().getPosX() - Game.getPlayer().getSpeed()); // ⬅️ Move left (correct)
        idleAnimation(Game.getPlayer().getAnimations());
        Game.getPlayer().getPlayerSprite().flip(true, false); // Flip sprite when moving left
    }
}

    public void render(SpriteBatch batch) {
        Game.getPlayer().getPlayerSprite().draw(batch);
    }


    public void idleAnimation(Animation<Texture> animation){

        Game.getPlayer().getPlayerSprite().setRegion(animation.getKeyFrame(Game.getPlayer().getTime()));

        if (!animation.isAnimationFinished(Game.getPlayer().getTime())) {
            Game.getPlayer().setTime(Game.getPlayer().getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            Game.getPlayer().setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }
}
