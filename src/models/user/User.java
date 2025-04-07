package models.user;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private Gender gender;
    private Hash hash;
    private String securityQuestion;
    private String securityAnswer;

    public static final List<String> SECURITY_QUESTIONS = List.of(
        "What is your mother's maiden name?",
        "What was the name of your first pet?",
        "What city were you born in?",
        "What is your favorite book?"
    );

    public User(String username, String password, String email, String nickname, Gender gender, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.hash = new Hash(password);
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public String getUsername() {
        return username;
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

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public boolean validateSecurityAnswer(String answer) {
        return this.securityAnswer.equalsIgnoreCase(answer);
    }

    public String toJson() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static User fromJson(String json) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public static boolean isUsernameValid(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static boolean isPasswordValid(String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static boolean isEmailValid(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static boolean isNicknameValid(String nickname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static boolean isGenderValid(String gender) {
        return Gender.getGenderByName(gender) != null;
    }

    public static String generateRandomPassword(int length) {
        return "RANDOM";
    }
}
