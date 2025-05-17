package com.tilldawn.Control;

import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.PreGameMenuView;
import com.tilldawn.View.RegisterMenuView;

public class RegisterMenuController {
    private RegisterMenuView view;

    public void setView(RegisterMenuView view) {
        this.view = view;
    }

    public void handleRegisterButton() {
        if (view == null) return;

        String password = view.getPasswordField().getText();
        if (!isPasswordValid(password)) {
            System.out.println("The written password was not strong enough!");
            return;
        }

        // Proceed if password is valid
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public boolean isPasswordValid(String password) {

        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*()_])[A-Za-z\\d@#$%&*()_]{8,}$");
    }
}
