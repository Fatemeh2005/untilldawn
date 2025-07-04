package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Control.WorldController;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));;

    // Shana assets
    private final String Shana_idle0 = "Shana/Idle_0.png";
    private final String Shana_idle1 = "Shana/Idle_1.png";
    private final String Shana_idle2 = "Shana/Idle_2.png";
    private final String Shana_idle3 = "Shana/Idle_3.png";
    private final String Shana_idle4 = "Shana/Idle_4.png";
    private final Texture Shana_idle0_tex = new Texture(Shana_idle0);
    private final Texture Shana_idle1_tex = new Texture(Shana_idle1);
    private final Texture Shana_idle2_tex = new Texture(Shana_idle2);
    private final Texture Shana_idle3_tex = new Texture(Shana_idle3);
    private final Texture Shana_idle4_tex = new Texture(Shana_idle4);

    // Diamond assets
    private final String Diamond_idle0 = "Diamond/Idle_0.png";
    private final String Diamond_idle1 = "Diamond/Idle_1.png";
    private final String Diamond_idle2 = "Diamond/Idle_2.png";
    private final String Diamond_idle3 = "Diamond/Idle_3.png";
    private final String Diamond_idle4 = "Diamond/Idle_4.png";
    private final Texture Diamond_idle0_tex = new Texture(Diamond_idle0);
    private final Texture Diamond_idle1_tex = new Texture(Diamond_idle1);
    private final Texture Diamond_idle2_tex = new Texture(Diamond_idle2);
    private final Texture Diamond_idle3_tex = new Texture(Diamond_idle3);
    private final Texture Diamond_idle4_tex = new Texture(Diamond_idle4);

    // Dasher assets
    private final String Dasher_idle0 = "Dasher/Idle_0.png";
    private final String Dasher_idle1 = "Dasher/Idle_1.png";
    private final String Dasher_idle2 = "Dasher/Idle_2.png";
    private final String Dasher_idle3 = "Dasher/Idle_3.png";
    private final String Dasher_idle4 = "Dasher/Idle_4.png";
    private final Texture Dasher_idle0_tex = new Texture(Dasher_idle0);
    private final Texture Dasher_idle1_tex = new Texture(Dasher_idle1);
    private final Texture Dasher_idle2_tex = new Texture(Dasher_idle2);
    private final Texture Dasher_idle3_tex = new Texture(Dasher_idle3);
    private final Texture Dasher_idle4_tex = new Texture(Dasher_idle4);

    // Lilith assets
    private final String Lilith_idle0 = "Lilith/Idle_0.png";
    private final String Lilith_idle1 = "Lilith/Idle_1.png";
    private final String Lilith_idle2 = "Lilith/Idle_2.png";
    private final String Lilith_idle3 = "Lilith/Idle_3.png";
    private final String Lilith_idle4 = "Lilith/Idle_4.png";
    private final Texture Lilith_idle0_tex = new Texture(Lilith_idle0);
    private final Texture Lilith_idle1_tex = new Texture(Lilith_idle1);
    private final Texture Lilith_idle2_tex = new Texture(Lilith_idle2);
    private final Texture Lilith_idle3_tex = new Texture(Lilith_idle3);
    private final Texture Lilith_idle4_tex = new Texture(Lilith_idle4);

    // Scarlet assets
    private final String Scarlet_idle0 = "Scarlet/Idle_0.png";
    private final String Scarlet_idle1 = "Scarlet/Idle_1.png";
    private final String Scarlet_idle2 = "Scarlet/Idle_2.png";
    private final String Scarlet_idle3 = "Scarlet/Idle_3.png";
    private final String Scarlet_idle4 = "Scarlet/Idle_4.png";
    private final Texture Scarlet_idle0_tex = new Texture(Scarlet_idle0);
    private final Texture Scarlet_idle1_tex = new Texture(Scarlet_idle1);
    private final Texture Scarlet_idle2_tex = new Texture(Scarlet_idle2);
    private final Texture Scarlet_idle3_tex = new Texture(Scarlet_idle3);
    private final Texture Scarlet_idle4_tex = new Texture(Scarlet_idle4);


    //tentacle monster
    private final String tentacle0 = "tentacleMonster/BrainMonster_0.png";
    private final String tentacle1 = "tentacleMonster/BrainMonster_1.png";
    private final String tentacle2 = "tentacleMonster/BrainMonster_2.png";
    private final String tentacle3 = "tentacleMonster/BrainMonster_3.png";
    private final Texture tentacle0_tex = new Texture(tentacle0);
    private final Texture tentacle1_tex = new Texture(tentacle1);
    private final Texture tentacle2_tex = new Texture(tentacle2);
    private final Texture tentacle3_tex = new Texture(tentacle3);

    //eyeBat monster
    private final String eyeBat0 = "eyeBat/T_EyeBat_0.png";
    private final String eyeBat1 = "eyeBat/T_EyeBat_1.png";
    private final String eyeBat2 = "eyeBat/T_EyeBat_2.png";
    private final String eyeBat3 = "eyeBat/T_EyeBat_3.png";
    private final Texture eyeBat0_tex = new Texture(eyeBat0);
    private final Texture eyeBat1_tex = new Texture(eyeBat1);
    private final Texture eyeBat2_tex = new Texture(eyeBat2);
    private final Texture eyeBat3_tex = new Texture(eyeBat3);

    //elder monster
    private final String elder0 = "Elder/0.png";
    private final String elder1 = "Elder/1.png";
    private final String elder2 = "Elder/2.png";
    private final String elder3 = "Elder/3.png";
    private final String elder4 = "Elder/4.png";
    private final Texture elder0_tex = new Texture(elder0);
    private final Texture elder1_tex = new Texture(elder1);
    private final Texture elder2_tex = new Texture(elder2);
    private final Texture elder3_tex = new Texture(elder3);
    private final Texture elder4_tex = new Texture(elder4);

    //tree
    private final String tree = "T_TreeMonsterWalking.png";
    private final Texture tree_tex = new Texture(tree);

    // Animations
    private final Animation<Texture> Shana_idle_frames = new Animation<>(0.1f, Shana_idle0_tex, Shana_idle1_tex, Shana_idle2_tex, Shana_idle3_tex, Shana_idle4_tex);
    private final Animation<Texture> Diamond_idle_frames = new Animation<>(0.1f, Diamond_idle0_tex, Diamond_idle1_tex, Diamond_idle2_tex, Diamond_idle3_tex, Diamond_idle4_tex);
    private final Animation<Texture> Dasher_idle_frames = new Animation<>(0.1f, Dasher_idle0_tex, Dasher_idle1_tex, Dasher_idle2_tex, Dasher_idle3_tex, Dasher_idle4_tex);
    private final Animation<Texture> Lilith_idle_frames = new Animation<>(0.1f, Lilith_idle0_tex, Lilith_idle1_tex, Lilith_idle2_tex, Lilith_idle3_tex, Lilith_idle4_tex);
    private final Animation<Texture> Scarlet_idle_frames = new Animation<>(0.1f, Scarlet_idle0_tex, Scarlet_idle1_tex, Scarlet_idle2_tex, Scarlet_idle3_tex, Scarlet_idle4_tex);
    private final Animation<Texture> eyeBat_frames= new Animation<>(0.1f, eyeBat0_tex, eyeBat1_tex, eyeBat2_tex, eyeBat3_tex);
    private final Animation <Texture> tentacle_frames = new Animation<>(0.1f,tentacle0_tex, tentacle1_tex, tentacle2_tex, tentacle3_tex);
    private final Animation<Texture> elder_frames = new Animation<>(0.1f, elder0_tex, elder1_tex, elder2_tex, elder3_tex, elder4_tex);

    private final String smg = "smg/SMGStill.png";
    private final String Revolver = "smg/Revolver.png";
    private final String shotGun = "smg/shotGun.png";
    private final Texture smgTexture = new Texture(smg);
    private final Texture revolverTexture = new Texture(Revolver);
    private final Texture shotGunTexture = new Texture(shotGun);

    private final String bullet = "bullet.png";

    //eye bat projectile
    private final String eyeBatBullet = "eyeBat/eyeBatBullet.png";
    private final Texture eyeBatBulletTex = new Texture(eyeBatBullet);

    private final Texture eyeBatSeedTex = new Texture("Seeds/EyeMonster_Em.png");

    private final Texture TentacleSeedTex = new Texture("Seeds/BrainMonster_Em.png");

    private final Texture playerDeath1Texture = new Texture("deathAnimation/ExplosionFX_0.png");
    private final Texture playerDeath2Texture = new Texture("deathAnimation/ExplosionFX_1.png");
    private final Texture playerDeath3Texture = new Texture("deathAnimation/ExplosionFX_2.png");
    private final Texture playerDeath4Texture = new Texture("deathAnimation/ExplosionFX_3.png");
    private final Texture playerDeath5Texture = new Texture("deathAnimation/ExplosionFX_4.png");
    private final Texture playerDeath6Texture = new Texture("deathAnimation/ExplosionFX_5.png");

    private final Animation<Texture>playerDeathAnimation = new Animation<>(0.1f, playerDeath1Texture,
        playerDeath2Texture, playerDeath3Texture, playerDeath4Texture, playerDeath5Texture, playerDeath6Texture);

    private final Texture avatar1 = new Texture("Avatars/avatar1.png");
    private final Texture avatar2 = new Texture("Avatars/avatar2.png");
    private final Texture avatar3 = new Texture("Avatars/avatar3.png");
    private final Texture avatar4 = new Texture("Avatars/avatar4.png");
    private final Texture avatar5 = new Texture("Avatars/avatar5.png");


    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
        if (skin == null) {
            skin = new Skin();
        }

        BitmapFont font = WorldController.generateFont("Michroma1.ttf", 10);
        if (font == null) {
            throw new RuntimeException("Font could not be loaded: Michroma.ttf");
        }

        skin.add("font", font, BitmapFont.class);
        skin.getFont("font").getData().markupEnabled = true;

        return skin;
    }



    public Animation<Texture> getShana_idle_animation() {
        return Shana_idle_frames;
    }

    public String getShana_idle0(){
        return Shana_idle0;
    }

    public Texture getSmgTexture(){
        return smgTexture;
    }

    public String getSmg(){
        return smg;
    }

    public String getBullet(){
        return bullet;
    }

    public Animation<Texture> getDiamond_idle_frames() {
        return Diamond_idle_frames;
    }

    public String getDiamond_idle0() {
        return Diamond_idle0;
    }

    public Animation<Texture> getDasher_idle_frames() {
        return Dasher_idle_frames;
    }

    public Animation<Texture> getScarlet_idle_frames() {
        return Scarlet_idle_frames;
    }

    public Animation<Texture> getLilith_idle_frames() {
        return Lilith_idle_frames;
    }

    public Animation<Texture> getShana_idle_frames() {
        return Shana_idle_frames;
    }

    public String getLilith_idle0() {
        return Lilith_idle0;
    }

    public String getDasher_idle0() {
        return Dasher_idle0;
    }

    public String getScarlet_idle0() {
        return Scarlet_idle0;
    }

    public Animation<Texture> getEyeBat_frames() {
        return eyeBat_frames;
    }

    public Animation<Texture> getTentacle_frames() {
        return tentacle_frames;
    }

    public Animation<Texture> getElder_frames() {
        return elder_frames;
    }

    public Texture getRevolverTexture() {
        return revolverTexture;
    }

    public Texture getShotGunTexture() {
        return shotGunTexture;
    }

    public Texture getShana_idle0_tex() {
        return Shana_idle0_tex;
    }

    public Texture getDiamond_idle0_tex() {
        return Diamond_idle0_tex;
    }

    public Texture getDasher_idle0_tex() {
        return Dasher_idle0_tex;
    }

    public Texture getLilith_idle0_tex() {
        return Lilith_idle0_tex;
    }

    public Texture getLilith_idle1_tex() {
        return Lilith_idle1_tex;
    }

    public Texture getScarlet_idle0_tex() {
        return Scarlet_idle0_tex;
    }

    public Texture getTree_tex() {
        return tree_tex;
    }

    public String getRevolver() {
        return Revolver;
    }

    public String getShotGun() {
        return shotGun;
    }

    public Texture getEyeBatBulletTex() {
        return eyeBatBulletTex;
    }

    public Texture getTentacleSeedTex() {
        return TentacleSeedTex;
    }

    public Texture getEyeBatSeedTex() {
        return eyeBatSeedTex;
    }

    public Animation<Texture> getPlayerDeathAnimation() {
        return playerDeathAnimation;
    }

    public Texture getAvatar1() {
        return avatar1;
    }

    public Texture getAvatar2() {
        return avatar2;
    }

    public Texture getAvatar3() {
        return avatar3;
    }

    public Texture getAvatar4() {
        return avatar4;
    }

    public Texture getAvatar5() {
        return avatar5;
    }
}
