package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;

public class PlayerController {
    private Player player;

    public PlayerController(Player player){
        this.player = player;
    }

    public void update(){
        player.getPlayerSprite().draw(Main.getBatch());

        handlePlayerInput();
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setPosY(player.getPosY() + player.getSpeed()); // ⬆️ Move up (now correct)
            idleAnimation(player.getAnimations());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setPosY(player.getPosY() - player.getSpeed()); // ⬇️ Move down (now correct)
            idleAnimation(player.getAnimations());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setPosX(player.getPosX() + player.getSpeed()); // ➡️ Move right (correct)
            idleAnimation(player.getAnimations());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setPosX(player.getPosX() - player.getSpeed()); // ⬅️ Move left (correct)
            idleAnimation(player.getAnimations());
            player.getPlayerSprite().flip(true, false); // Flip sprite when moving left
        }
    }



    public void idleAnimation(Animation<Texture> animation){

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
