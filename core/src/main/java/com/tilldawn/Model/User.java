package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String answerOfSecurity;
    private String avatarImageAddress;
    private int score;

    public User(String username, String password, String answerOfSecurity, String avatarImageAddress) {
        this.score = 0;
        this.username = username;
        this.password = password;
        this.answerOfSecurity = answerOfSecurity;
        this.avatarImageAddress = avatarImageAddress;
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

    public String getAvatarImageAddress() {
        return avatarImageAddress;
    }

    public void setAvatarImageAddress(String avatarImageAddress) {
        this.avatarImageAddress = avatarImageAddress;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
