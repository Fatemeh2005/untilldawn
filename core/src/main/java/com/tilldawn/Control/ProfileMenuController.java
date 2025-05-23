package com.tilldawn.Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.PlayerTypes;
import com.tilldawn.Model.User;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.ProfileMenuView;

import java.io.*;

public class ProfileMenuController {

    private ProfileMenuView view;
    private User currentUser;

    public void setView(ProfileMenuView view) {
        this.view = view;
    }

    public void loadUser(User user) {
        if (user == null || user.getUsername() == null) {
            view.showError("Invalid user.");
            return;
        }

        File file = new File("users/" + user.getUsername() + ".json");
        if (!file.exists()) {
            view.showError("User file not found.");
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            currentUser = gson.fromJson(reader, User.class);
            view.setCurrentUsername(currentUser.getUsername());
        } catch (IOException e) {
            view.showError("Error loading user: " + e.getMessage());
        }
    }

    public void handleSaveChanges() {
        if (view == null || currentUser == null) return;

        String newUsername = view.getNewUsernameField().getText().trim();
        String oldPassword = view.getOldPasswordField().getText();
        String newPassword = view.getNewPasswordField().getText();

        boolean usernameChanged = !newUsername.equals(currentUser.getUsername()) && !newUsername.isEmpty();
        if (view.getSaveButton().isChecked()) {
            if (usernameChanged && isUsernameTaken(newUsername)) {
                view.showError("Username already taken.");
                return;
            }

            if (!newPassword.isEmpty()) {
                if (!currentUser.getPassword().equals(oldPassword)) {
                    view.showError("Incorrect old password.");
                    return;
                }

                if (!isPasswordValid(newPassword)) {
                    view.showError("Password must be at least 8 characters, include an uppercase letter, a digit, and a symbol.");
                    return;
                }

                currentUser.setPassword(newPassword);
            }

            // Rename file if needed
            if (usernameChanged) {
                File oldFile = new File("users/" + currentUser.getUsername() + ".json");
                File newFile = new File("users/" + newUsername + ".json");
                currentUser.setUsername(newUsername); // update before renaming or saving
                if (!oldFile.renameTo(newFile)) {
                    view.showError("Failed to rename user file.");
                    return;
                }
            }

            if (saveUserToJson(currentUser)) {
                view.showSuccess("Changes saved successfully!");

                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenuView(
                    new LoginMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin(),
                    currentUser.getPlayerTypes()));
            } else {
                view.showError("Failed to save changes.");
            }
        }
    }

    private boolean saveUserToJson(User user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("users/" + user.getUsername() + ".json")) {
            gson.toJson(user, writer);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to save user: " + e.getMessage());
            return false;
        }
    }


    private boolean isUsernameTaken(String username) {
        return new File("users/" + username + ".json").exists();
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*()_]).{8,}$");
    }

}
