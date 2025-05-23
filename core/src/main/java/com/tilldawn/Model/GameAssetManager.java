package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

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


    private GameAssetManager(){

    }

    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
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

    public String getShana_idle1() {
        return Shana_idle1;
    }

    public String getShana_idle2() {
        return Shana_idle2;
    }

    public String getShana_idle3() {
        return Shana_idle3;
    }

    public String getShana_idle4() {
        return Shana_idle4;
    }

    public Texture getShana_idle0_tex() {
        return Shana_idle0_tex;
    }

    public Texture getShana_idle1_tex() {
        return Shana_idle1_tex;
    }

    public Texture getShana_idle2_tex() {
        return Shana_idle2_tex;
    }

    public Texture getShana_idle3_tex() {
        return Shana_idle3_tex;
    }

    public Texture getShana_idle4_tex() {
        return Shana_idle4_tex;
    }

    public String getDiamond_idle1() {
        return Diamond_idle1;
    }

    public String getDiamond_idle2() {
        return Diamond_idle2;
    }

    public String getDiamond_idle3() {
        return Diamond_idle3;
    }

    public String getDiamond_idle4() {
        return Diamond_idle4;
    }

    public Texture getDiamond_idle0_tex() {
        return Diamond_idle0_tex;
    }

    public Texture getDiamond_idle1_tex() {
        return Diamond_idle1_tex;
    }

    public Texture getDiamond_idle2_tex() {
        return Diamond_idle2_tex;
    }

    public Texture getDiamond_idle3_tex() {
        return Diamond_idle3_tex;
    }

    public Texture getDiamond_idle4_tex() {
        return Diamond_idle4_tex;
    }

    public String getDasher_idle1() {
        return Dasher_idle1;
    }

    public String getDasher_idle2() {
        return Dasher_idle2;
    }

    public String getDasher_idle3() {
        return Dasher_idle3;
    }

    public String getDasher_idle4() {
        return Dasher_idle4;
    }

    public Texture getDasher_idle0_tex() {
        return Dasher_idle0_tex;
    }

    public Texture getDasher_idle1_tex() {
        return Dasher_idle1_tex;
    }

    public Texture getDasher_idle2_tex() {
        return Dasher_idle2_tex;
    }

    public Texture getDasher_idle3_tex() {
        return Dasher_idle3_tex;
    }

    public Texture getDasher_idle4_tex() {
        return Dasher_idle4_tex;
    }

    public String getLilith_idle1() {
        return Lilith_idle1;
    }

    public String getLilith_idle2() {
        return Lilith_idle2;
    }

    public String getLilith_idle3() {
        return Lilith_idle3;
    }

    public String getLilith_idle4() {
        return Lilith_idle4;
    }

    public Texture getLilith_idle0_tex() {
        return Lilith_idle0_tex;
    }

    public Texture getLilith_idle1_tex() {
        return Lilith_idle1_tex;
    }

    public Texture getLilith_idle2_tex() {
        return Lilith_idle2_tex;
    }

    public Texture getLilith_idle3_tex() {
        return Lilith_idle3_tex;
    }

    public Texture getLilith_idle4_tex() {
        return Lilith_idle4_tex;
    }

    public String getScarlet_idle1() {
        return Scarlet_idle1;
    }

    public String getScarlet_idle2() {
        return Scarlet_idle2;
    }

    public String getScarlet_idle3() {
        return Scarlet_idle3;
    }

    public String getScarlet_idle4() {
        return Scarlet_idle4;
    }

    public Texture getScarlet_idle0_tex() {
        return Scarlet_idle0_tex;
    }

    public Texture getScarlet_idle1_tex() {
        return Scarlet_idle1_tex;
    }

    public Texture getScarlet_idle2_tex() {
        return Scarlet_idle2_tex;
    }

    public Texture getScarlet_idle3_tex() {
        return Scarlet_idle3_tex;
    }

    public Texture getScarlet_idle4_tex() {
        return Scarlet_idle4_tex;
    }

    public String getTentacle0() {
        return tentacle0;
    }

    public String getTentacle1() {
        return tentacle1;
    }

    public String getTentacle2() {
        return tentacle2;
    }

    public String getTentacle3() {
        return tentacle3;
    }

    public Texture getTentacle0_tex() {
        return tentacle0_tex;
    }

    public Texture getTentacle1_tex() {
        return tentacle1_tex;
    }

    public Texture getTentacle2_tex() {
        return tentacle2_tex;
    }

    public Texture getTentacle3_tex() {
        return tentacle3_tex;
    }

    public String getEyeBat0() {
        return eyeBat0;
    }

    public String getEyeBat1() {
        return eyeBat1;
    }

    public String getEyeBat2() {
        return eyeBat2;
    }

    public String getEyeBat3() {
        return eyeBat3;
    }

    public Texture getEyeBat0_tex() {
        return eyeBat0_tex;
    }

    public Texture getEyeBat1_tex() {
        return eyeBat1_tex;
    }

    public Texture getEyeBat2_tex() {
        return eyeBat2_tex;
    }

    public Texture getEyeBat3_tex() {
        return eyeBat3_tex;
    }

    public String getElder0() {
        return elder0;
    }

    public String getElder1() {
        return elder1;
    }

    public String getElder2() {
        return elder2;
    }

    public String getElder3() {
        return elder3;
    }

    public String getElder4() {
        return elder4;
    }

    public Texture getElder0_tex() {
        return elder0_tex;
    }

    public Texture getElder1_tex() {
        return elder1_tex;
    }

    public Texture getElder2_tex() {
        return elder2_tex;
    }

    public Texture getElder3_tex() {
        return elder3_tex;
    }

    public Texture getElder4_tex() {
        return elder4_tex;
    }

    public String getTree() {
        return tree;
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
}
