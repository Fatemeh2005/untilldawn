package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


import java.util.Random;

public class AudioManager {
    private static AudioManager instance;
    private Music[] tracks;
    private Music currentMusic;
    private int currentTrackIndex;
    private boolean isMuted = false;
    private float musicVolume;
    private float sfxVolume;
    private Sound shootSound;
    private Sound winSound;
    private Sound loseSound;
    private Sound levelUpSound;

    private AudioManager() {
        musicVolume = getStoredVolume("musicVolume", 1f);
        sfxVolume = getStoredVolume("sfxVolume", 1f);
        shootSound = Gdx.audio.newSound(Gdx.files.internal("audio/single_shot.wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("audio/You Win (2).wav"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("audio/You Lose (4).wav"));
        levelUpSound = Gdx.audio.newSound(Gdx.files.internal("audio/Special & Powerup (8).wav"));

        tracks = new Music[2];
        for (int i = 0; i < tracks.length; i++) {
            tracks[i] = Gdx.audio.newMusic(Gdx.files.internal("audio/" + (i + 1) + ".mp3"));
            tracks[i].setLooping(true);
        }

        currentTrackIndex = new Random().nextInt(tracks.length);
        currentMusic = tracks[currentTrackIndex];
        currentMusic.setVolume(isMuted ? 0f : musicVolume);
        currentMusic.play();

    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    private float getStoredVolume(String key, float defaultValue) {
        return Gdx.app.getPreferences("GameSettings").getFloat(key, defaultValue);
    }

    public void switchTrack(int index) {
        if (index < 0 || index >= tracks.length || index == currentTrackIndex) return;

        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        tracks[index] = Gdx.audio.newMusic(Gdx.files.internal("audio/" + (index + 1) + ".mp3"));
        tracks[index].setLooping(true);
        tracks[index].setVolume(isMuted ? 0f : musicVolume);
        tracks[index].play();

        currentTrackIndex = index;
        currentMusic = tracks[currentTrackIndex];
        // Gdx.app.log("AudioManager", "Switched to track: " + currentTrackIndex);
    }

    public void playNextTrack() {
        int nextIndex = (currentTrackIndex + 1) % tracks.length;
        switchTrack(nextIndex);
    }

    public void stopMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
        }
    }

    public void resumeMusic() {
        if (!isMuted && currentMusic != null && !currentMusic.isPlaying()) {
            currentMusic.play();
        }
    }

    public void toggleMute() {
        isMuted = !isMuted;
        if (currentMusic != null) {
            if (isMuted) {
                currentMusic.pause();
            } else {
                currentMusic.play();
            }
        }
    }

    public boolean isMuted() {
        return isMuted;
    }

    public int getCurrentTrackIndex() {
        return currentTrackIndex;
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        if (!isMuted && currentMusic != null) {
            currentMusic.setVolume(volume);
        }
    }

    public void playShootSound() {
        if (!isMuted && shootSound != null) {
            shootSound.play(sfxVolume);
        }
    }

    public void playWinSound() {
        if (!isMuted && winSound != null) {
            winSound.play(sfxVolume);
        }
    }

    public void playLoseSound() {
        if (!isMuted && loseSound != null) {
            loseSound.play(sfxVolume);
        }
    }

    public void playLevelUpSound() {
        if (!isMuted && levelUpSound != null) {
            levelUpSound.play(sfxVolume);
        }
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setSfxVolume(float volume) {
        this.sfxVolume = volume;
    }

    public float getSfxVolume() {
        return sfxVolume;
    }
}
