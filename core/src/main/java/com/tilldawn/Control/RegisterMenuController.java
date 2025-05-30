package com.tilldawn.Control;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.PlayerTypes;
import com.tilldawn.Model.SqlSave.UserDAO;
import com.tilldawn.Model.User;
import com.tilldawn.View.*;
import com.tilldawn.View.RegisterMenuView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RegisterMenuController {
    private RegisterMenuView view;
    private PlayerTypes name = PlayerTypes.DASHER;

    public void setView(RegisterMenuView view) {
        this.view = view;
    }

    public void handleRegisterButton() {
        if (view != null) {
            String username = view.getUsernameField().getText();
            String password = view.getPasswordField().getText();
            String answerOfSecurity = view.getAnswerSecurityQuestionField().getText();
            if (view.getPlayButton().isChecked()) {

                if (username.equals("") || password.equals("") || answerOfSecurity.equals("")) {
                    view.showError("do not leave any field empty");
                } else if (!isPasswordValid(password)) {
                    view.showError("password format is invalid!");  // ❗️ Display validation error
                } else if (isUsernameTaken(username)) {
                    view.showError("this user has already registered!");
                }//register successfully
                else {
                    Animation<Texture> animations = GameAssetManager.getGameAssetManager().getDiamond_idle_frames();
                    Random random = new Random();
                    //make it 5
                    int randomNumber = random.nextInt(5);

                    switch (randomNumber) {
                        case 0:
                            name = PlayerTypes.DIAMOND;
                            break;
                        case 1:
                            name = PlayerTypes.SHANA;
                            break;
                        case 2:
                            name = PlayerTypes.DASHER;
                            break;
                        case 3:
                            name = PlayerTypes.SCARLET;
                            break;
                        case 4:
                            name = PlayerTypes.LILITH;
                            break;

                    }
                    User user = new User(username, password, answerOfSecurity, name);
                    saveUserToJson(user);
                    UserDAO saveUser = new UserDAO();
                    saveUser.saveUser(user);

                    view.hideError();
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new LoginMenuView(
                        new LoginMenuController(),
                        GameAssetManager.getGameAssetManager().getSkin(), name));
                }
            }
            if (view.getGoToLoginButton().isChecked()) {
                view.hideError();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenuView(
                    new LoginMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin(), name));
            }
        }
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*()_]).{8,}$");
    }

    private void saveUserToJson(User user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("users/" + user.getUsername() + ".json")) {
            gson.toJson(user, writer);
        } catch (IOException e) {
            System.out.println("Failed to save user: " + e.getMessage());
        }
    }

    private boolean isUsernameTaken(String username) {
        File usersDir = new File("users");
        if (!usersDir.exists()) {
            return false; // No users exist yet
        }
        File userFile = new File(usersDir, username + ".json");
        return userFile.exists(); // Returns true if the file exists
    }
}
