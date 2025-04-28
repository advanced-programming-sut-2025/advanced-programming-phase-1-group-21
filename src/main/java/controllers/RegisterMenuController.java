package controllers;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.App;
import models.Menu;
import models.result.Result;
import models.result.errorTypes.AuthError;
import models.user.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static models.user.Gender.getGenderByName;

public class RegisterMenuController{


    public Result<Void> showCurrentMenu(){
        return new Result<>(null , null , "Register Menu");
    }

    public Result<User> register(String username, String password,String passwordConfirm, String nickname , String email, String gender) throws IOException {
        if(findUserByUsername(username) != null)
            return new Result<>(null , AuthError.USER_ALREADY_EXISTS , AuthError.USER_ALREADY_EXISTS.getMessage());

        if(!usernameValidation(username))
            return new Result<>(null , AuthError.INVALID_USERNAME , AuthError.INVALID_USERNAME.getMessage());

        if(!emailValidation(email))
            return new Result<>(null , AuthError.INVALID_EMAIL_FORMAT , AuthError.INVALID_EMAIL_FORMAT.getMessage());

        if(checkPassword(password).isError())
            return checkPassword(password);

        if(!password.equals(passwordConfirm))
            return new Result<>(null , AuthError.PASSWORD_CONFIRM_ERROR , AuthError.PASSWORD_CONFIRM_ERROR.getMessage());

        //TODO
        //Random Password (balad naboodam)

        User user = new User(username , password , email , nickname , getGenderByName(gender) , null , null);
        App.registeredUser = user;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User> users = readAllUsers(gson, "Users.json");
        users.add(user);
        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
            throw e;
        }
        return new Result<>(user , null , "User Registered");
    }

    public Result<Void> pickQuestion(String answer, String answerConfirm, String questionNumber) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(answer.equals(answerConfirm)) {
            App.registeredUser.setSecurityQuestionID(Integer.parseInt(questionNumber));
            App.registeredUser.setSecurityAnswer(answer);
            ArrayList<User> users = readAllUsers(gson, "Users.json");
            for(User user : users){
                if(user.getUsername().equals(App.registeredUser.getUsername())) {
                    user.setSecurityAnswer(answer);
                    user.setSecurityQuestionID(Integer.parseInt(questionNumber));
                }
            }
            try (FileWriter writer = new FileWriter("Users.json")) {
                gson.toJson(users, writer);
            } catch (IOException e) {
                System.err.println("error" + e.getMessage());
                throw e;
            }
            return new Result<>(null , null , "Answer successfully sets");
        }
        return new Result<>(null , null , "Answer not set");
    }

    private boolean usernameValidation(String username) {
        String usernameRegex = "^[a-zA-Z0-9-]{3,20}$";
        return Pattern.compile(usernameRegex).matcher(username).matches();
    }

    private boolean emailValidation(String email) {
        String emailGetter = "(?<username>\\S+)@(?<domain>\\S+)";
        String emailUsernameRegex = "(?!.*\\.\\..*)[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]";
        String domainRegex = "[a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]\\.[a-zA-Z]{2,}";
        String invalidCharactersRegex = "(?!.*\\?.*)(?!.*>.*)(?!.*<.*)(?!.*,.*)(?!.*\".*)(?!.*'.*)(?!.*;.*)(?!.*:.*)" +
                "(?!.*\\/.*)(?!.*\\|.*)(?!.*\\].*)(?!.*\\[.*)(?!.*\\}.*)(?!.*\\{.*)(?!.*\\+.*)(?!.*=.*)(?!.*\\).*)" +
                "(?!.*\\(.*)(?!.*\\*.*)(?!.*&.*)(?!.*\\^.*)(?!.*%.*)(?!.*\\$.*)(?!.*#.*)(?!.*\\!.*)\\S+";
        Matcher matcher;

        matcher = Pattern.compile(emailGetter).matcher(email);
        if(!matcher.matches())
            return false;
        if(!Pattern.compile(emailUsernameRegex).matcher(matcher.group("username")).matches())
            return false;
        if(!Pattern.compile(domainRegex).matcher(matcher.group("domain")).matches())
            return false;
        if(!Pattern.compile(invalidCharactersRegex).matcher(email).matches())
            return false;
        return true;

    }

    private Result<User> checkPassword(String password) {
        if(password.length() < 8)
            return new Result<>(null , AuthError.PASSWORD_LENGTH , AuthError.PASSWORD_LENGTH.getMessage());

        String specialCharacters = "(?!.*\\?.*)(?!.*>.*)(?!.*<.*)(?!.*,.*)(?!.*\".*)(?!.*'.*)(?!.*;.*)(?!.*:.*)(?!.*\\/.*)" +
                "(?!.*\\|.*)(?!.*\\].*)(?!.*\\[.*)(?!.*\\}.*)(?!.*\\{.*)(?!.*\\+.*)(?!.*=.*)(?!.*\\).*)(?!.*\\(.*)(?!.*\\*.*)" +
                "(?!.*&.*)(?!.*\\^.*)(?!.*%.*)(?!.*\\$.*)(?!.*#.*)(?!.*\\!.*)\\S+";

        if(Pattern.compile(specialCharacters).matcher(password).matches())
            return new Result<>(null , AuthError.PASSWORD_SPECIAL_CHARACTERS , AuthError.PASSWORD_SPECIAL_CHARACTERS.getMessage());

        String containAlphabet = "(?!.*[a-zA-Z].*)\\S+";
        if(Pattern.compile(containAlphabet).matcher(password).matches())
            return new Result<>(null , AuthError.PASSWORD_ALPHABET , AuthError.PASSWORD_ALPHABET.getMessage());

        String containNumber = "(?!.*[0-9].*)\\S+";
        if(Pattern.compile(containNumber).matcher(password).matches())
            return new Result<>(null , AuthError.PASSWORD_NUMBERS , AuthError.PASSWORD_NUMBERS.getMessage());

        return new Result<>(null , null , null);

    }

    private ArrayList<User> readAllUsers(Gson gson , String filePath){
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> existingData = gson.fromJson(reader, listType);
            return existingData != null ? existingData : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("error in reading file" + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Result<Void> goToLogin(){
        App.currentMenu = Menu.LoginMenu;
        return new Result<>(null , null , "Now you are in login menu");
    }

    private User findUserByUsername(String username) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User> users = readAllUsers(gson , "Users.json");
        for(User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }
}
