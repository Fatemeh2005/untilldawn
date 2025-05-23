package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String answerOfSecurity;
    private PlayerTypes playerTypes;
    private int score;
    private int numberOfKills;
    private int mostTimeSurvived;

    public User(String username, String password, String answerOfSecurity, PlayerTypes playerTypes) {
        this.score = 0;
        this.username = username;
        this.password = password;
        this.answerOfSecurity = answerOfSecurity;
        this.playerTypes = playerTypes;
        this.numberOfKills = 0;
        this.mostTimeSurvived = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAnswerOfSecurity() {
        return answerOfSecurity;
    }

    public void setAnswerOfSecurity(String answerOfSecurity) {
        this.answerOfSecurity = answerOfSecurity;
    }

    public PlayerTypes getPlayerTypes() {
        return playerTypes;
    }

    public void setPlayerTypes(PlayerTypes playerTypes) {
        this.playerTypes = playerTypes;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfKills() {
        return numberOfKills;
    }

    public void setNumberOfKills(int numberOfKills) {
        this.numberOfKills = numberOfKills;
    }

    public int getMostTimeSurvived() {
        return mostTimeSurvived;
    }

    public void setMostTimeSurvived(int mostTimeSurvived) {
        this.mostTimeSurvived = mostTimeSurvived;
    }
}
