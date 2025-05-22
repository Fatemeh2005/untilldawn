package com.tilldawn.Control;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.google.gson.Gson;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.PreGameMenuView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class LoginMenuController {
    private LoginMenuView view;

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public void handleLoginButton() {
        if (view != null) {
            String username = view.getUsernameField().getText();
            String password = view.getPasswordField().getText();
            String answerOfSecurity = view.getAnswerSecurityQuestionField().getText();

            if (username.equals("") || password.equals("") || answerOfSecurity.equals("")) {
                view.showError("do not leave any field empty");
                return;
            }

            User user = loadUserFromJson(username);
            if (user == null) {
                view.showError("user does not exist!");
            } else if (!user.getPassword().equals(password)) {
                view.showError("incorrect password!");
            } else if (!user.getAnswerOfSecurity().equals(answerOfSecurity)) {
                view.showError("security answer is incorrect!");
            } else {
                Animation<Texture>animations = GameAssetManager.getGameAssetManager().getDiamond_idle_frames();
                switch (user.getAvatarImageAddress()) {
                    case "Diamond/Idle_0.png":
                        animations = GameAssetManager.getGameAssetManager().getDiamond_idle_frames();
                        break;
                    case "Shana/Idle_0.png":

                        animations = GameAssetManager.getGameAssetManager().getShana_idle_frames();
                        break;
                    case "Dasher/Idle_0.png":

                        animations = GameAssetManager.getGameAssetManager().getDasher_idle_frames();
                        break;
                    case "Scarlet/Idle_0.png":

                        animations = GameAssetManager.getGameAssetManager().getScarlet_idle_frames();
                        break;
                    case "Lilith/Idle_0.png":

                        animations = GameAssetManager.getGameAssetManager().getLilith_idle_frames();
                        break;

                }
                Player newPlayer = new Player(animations, PlayerTypes.findPlayerTypeWithAddress(user.getAvatarImageAddress()));
                Game.setPlayer(newPlayer);
                view.hideError();

                Main.getMain().getScreen().dispose();
                Game.setCurrentUser(user);
                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()));

            }
        }
    }

    public void handleGuestLogin() {
        String name = "";
        Animation<Texture> animations = GameAssetManager.getGameAssetManager().getDiamond_idle_frames();
        Random random = new Random();
        //make it 5
        int randomNumber = random.nextInt(5);

        switch (randomNumber) {
            case 0:
                name = GameAssetManager.getGameAssetManager().getDiamond_idle0();
                animations = GameAssetManager.getGameAssetManager().getDiamond_idle_frames();
                break;
            case 1:
                name = GameAssetManager.getGameAssetManager().getShana_idle0();
                animations = GameAssetManager.getGameAssetManager().getShana_idle_frames();
                break;
            case 2:
                name = GameAssetManager.getGameAssetManager().getDasher_idle0();
                animations = GameAssetManager.getGameAssetManager().getDasher_idle_frames();
                break;
            case 3:
                name = GameAssetManager.getGameAssetManager().getScarlet_idle0();
                animations = GameAssetManager.getGameAssetManager().getScarlet_idle_frames();
                break;
            case 4:
                name = GameAssetManager.getGameAssetManager().getLilith_idle0();
                animations = GameAssetManager.getGameAssetManager().getLilith_idle_frames();
                break;

        }
        //TODO:null passed here!!
        Player newPlayer = new Player(animations, PlayerTypes.findPlayerTypeWithAddress(name));
        Game.setPlayer(newPlayer);
        view.hideError();
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(
            new MainMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void handleForgotPassword() {
        if (view != null) {
            String username = view.getUsernameField().getText();
            String answer = view.getAnswerSecurityQuestionField().getText();
            String newPassword = view.getPasswordField().getText();

            if (username.equals("") || answer.equals("") || newPassword.equals("")) {
                view.showError("fill in username, answer and new password");
                return;
            }

            User user = loadUserFromJson(username);
            if (user == null) {
                view.showError("user not found!");
            } else if (!user.getAnswerOfSecurity().equals(answer)) {
                view.showError("security answer incorrect!");
            } else {
                user.setPassword(newPassword);
                saveUserToJson(user);
                view.hideError();
                view.showError("password reset successfully");
            }
        }
    }

    private User loadUserFromJson(String username) {
        File userFile = new File("users/" + username + ".json");
        if (!userFile.exists()) return null;

        try (FileReader reader = new FileReader(userFile)) {
            return new Gson().fromJson(reader, User.class);
        } catch (IOException e) {
            System.out.println("Failed to load user: " + e.getMessage());
            return null;
        }
    }

    private void saveUserToJson(User user) {
        File userFile = new File("users/" + user.getUsername() + ".json");
        try (FileWriter writer = new FileWriter(userFile)) {
            new Gson().toJson(user, writer);
        } catch (IOException e) {
            System.out.println("Failed to save user: " + e.getMessage());
        }
    }
}
