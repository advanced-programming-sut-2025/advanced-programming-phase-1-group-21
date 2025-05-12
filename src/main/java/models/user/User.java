package models.user;

import models.Item.Recipe;
import models.game.Inventory;
import models.result.Result;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private Gender gender;
    private boolean isInAGame;
    private Hash hash;
    private Integer securityQuestionID;
    private String securityAnswer;
    private Inventory myInventory;
    private ArrayList<Recipe> myRecipes;

    public static final List<String> SECURITY_QUESTIONS = List.of(
            "What is your mother's maiden name?",
            "What was the name of your first pet?",
            "What city were you born in?",
            "What is your favorite book?"
    );

    public void setSecurityQuestionID(Integer securityQuestionID) {
        this.securityQuestionID = securityQuestionID;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public User(String username, String password, String email, String nickname, Gender gender, Integer securityQuestionID, String securityAnswer , boolean isInAGame) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.securityQuestionID = securityQuestionID;
        this.securityAnswer = securityAnswer;
        this.isInAGame = isInAGame;
    }

    public boolean isInAGame() {
        return isInAGame;
    }

    public String getUsername() {
        return username;
    }

    public void setInAGame(boolean inAGame) {
        isInAGame = inAGame;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public String getNickname() {
        return nickname;
    }

    public Hash getHash() {
        return hash;
    }

    public Integer getSecurityQuestionID() {
        return securityQuestionID;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setHash(Hash hash) {
        this.hash = hash;
    }

    public boolean validateSecurityAnswer(String answer) {
        return this.securityAnswer.equalsIgnoreCase(answer);
    }

    public static String generateRandomPassword(int length) {
        return "RANDOM";
    }
}