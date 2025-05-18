package com.tilldawn.Control;

import com.google.gson.Gson;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.PreGameMenuView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

            if(view.getPlayAsGuestButton().isChecked()){
                view.hideError();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()));
            }

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
                view.hideError();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()));

            }
        }
    }

    public void handleGuestLogin() {
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
