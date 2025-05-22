package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.tilldawn.Main;
import com.tilldawn.Model.AudioManager;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.MainMenuView;

public class SettingsController {
    private final Preferences prefs = Gdx.app.getPreferences("GameSettings");

    public float getMusicVolume() {
        return prefs.getFloat("musicVolume", 1f);
    }

    public void setMusicVolume(float volume) {
        prefs.putFloat("musicVolume", volume);
        prefs.flush();
        AudioManager.getInstance().setMusicVolume(volume);
    }

    public float getSfxVolume() {
        return prefs.getFloat("sfxVolume", 1f);
    }

    public void setSfxVolume(float volume) {
        prefs.putFloat("sfxVolume", volume);
        prefs.flush();
        AudioManager.getInstance().setSfxVolume(volume);
    }

    public void playNextMusic() {
        AudioManager.getInstance().playNextTrack();
    }

    public void goBackToMainMenu() {
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
