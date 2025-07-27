package models.user;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String email;
    private String nickname;
    private Gender gender;
    private boolean isInAGame;
    private boolean stayLoggedIn;
    private String hash;
    private Integer securityQuestionID;
    private String securityAnswer;

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


    /**
     * for kryo only!
     */
    public User() {

    }

    public User(String username, String password, String email, String nickname, Gender gender, Integer securityQuestionID, String securityAnswer , boolean isInAGame) {
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.securityQuestionID = securityQuestionID;
        this.securityAnswer = securityAnswer;
        this.isInAGame = isInAGame;
        if (password != null)
            setPassword(password);
    }

    public void setPassword(String password) {
        this.hash = Hash.hashPassword(password);
    }

    public boolean verifyPassword(String inputPassword) {
        return this.hash.equals(Hash.hashPassword(inputPassword));
    }

    public boolean isInAgame() {
        return isInAGame;
    }

    public String getUsername() {
        return username;
    }

    public void setInAGame(boolean inAGame) {
        isInAGame = inAGame;
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

    public Integer getSecurityQuestionID() {
        return securityQuestionID;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
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

    public boolean validateSecurityAnswer(String answer) {
        return this.securityAnswer.equalsIgnoreCase(answer);
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public User copy() {
        return new User(username, null, email, nickname, gender, securityQuestionID, securityAnswer, isInAGame);
    }
}
