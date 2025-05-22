package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class AudioManager {
    private static AudioManager instance;
    private float musicVolume = 1f;
    private float sfxVolume = 1f;
    private int currentTrackIndex = 0;
    private final ArrayList<Music> musicTracks = new ArrayList<>();

    private AudioManager() {
        try {
            Music track1 = Gdx.audio.newMusic(Gdx.files.internal("daylight.mp3"));
            Music track2 = Gdx.audio.newMusic(Gdx.files.internal("tom_morello_2.mp3"));

            musicTracks.add(track1);
            musicTracks.add(track2);

            for (Music m : musicTracks) {
                m.setLooping(true);
            }

            if (!musicTracks.isEmpty()) {
                musicTracks.get(currentTrackIndex).setVolume(musicVolume);
                musicTracks.get(currentTrackIndex).play();
            }
        } catch (Exception e) {
            System.out.println("⚠️ Failed to load music files: " + e.getMessage());
        }
    }

    public static AudioManager getInstance() {
        if (instance == null) instance = new AudioManager();
        return instance;
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        if (!musicTracks.isEmpty()) {
            musicTracks.get(currentTrackIndex).setVolume(volume);
        }
    }

    public void setSfxVolume(float volume) {
        this.sfxVolume = volume;
    }

    public void playSound(Sound sound) {
        if (sound != null) {
            sound.play(sfxVolume);
        }
    }

    public void playNextTrack() {
        if (musicTracks.isEmpty()) return;

        musicTracks.get(currentTrackIndex).stop();
        currentTrackIndex = (currentTrackIndex + 1) % musicTracks.size();
        musicTracks.get(currentTrackIndex).setVolume(musicVolume);
        musicTracks.get(currentTrackIndex).play();
    }
}
