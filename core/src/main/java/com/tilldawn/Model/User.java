package com.tilldawn.Model;

public class User {
    private String username;
    private String password;
    private String answerOfSecurity;

    public User(String username, String password, String answerOfSecurity) {
        this.username = username;
        this.password = password;
        this.answerOfSecurity = answerOfSecurity;
    }

    // getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getAnswerOfSecurity() { return answerOfSecurity; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAnswerOfSecurity(String answerOfSecurity) {
        this.answerOfSecurity = answerOfSecurity;
    }
}
