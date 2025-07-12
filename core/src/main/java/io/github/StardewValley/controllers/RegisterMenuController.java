package io.github.StardewValley.controllers;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.StardewValley.models.App;
import io.github.StardewValley.views.menu.CLI.LoginMenuView;
import io.github.StardewValley.models.result.Result;
import io.github.StardewValley.models.result.errorTypes.AuthError;
import io.github.StardewValley.models.result.errorTypes.UserError;
import io.github.StardewValley.models.user.Gender;
import io.github.StardewValley.models.user.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.StardewValley.models.user.Gender.getGenderByName;

public class RegisterMenuController{
    public Result<String> showCurrentMenu(){
        return Result.success("Register Menu");
    }

    public Result<Void> register(String username, String password, String passwordConfirm, String nickname , String email, Gender gender) {
        if(findUserByUsername(username) != null)
            return Result.failure(AuthError.USER_ALREADY_EXISTS);

        if(!usernameValidation(username))
            return Result.failure(AuthError.INVALID_USERNAME);

        if(!emailValidation(email))
            return Result.failure(AuthError.INVALID_EMAIL_FORMAT);

        if (password.equals("random")) {
            password = generateRandomPassword();
        }
        else {
            if (checkPassword(password).isError())
                return checkPassword(password);

            if (!password.equals(passwordConfirm))
                return Result.failure(AuthError.PASSWORD_CONFIRM_ERROR);
        }

        User user = new User(username , password , email , nickname , gender , null , null , false);
        App.getInstance().registeredUser = user;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User> users = readAllUsers(gson, "Users.json");
        users.add(user);
        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
//            throw e;
        }
        return Result.success(null, "User Registered " + username + " " + password);
    }

    public Result<Void> pickQuestion(String answer, String answerConfirm, String questionNumber) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(answer.equals(answerConfirm)) {
            App.getInstance().registeredUser.setSecurityQuestionID(Integer.parseInt(questionNumber));
            App.getInstance().registeredUser.setSecurityAnswer(answer);
            ArrayList<User> users = readAllUsers(gson, "Users.json");
            for(User user : users){
                if(user.getUsername().equals(App.getInstance().registeredUser.getUsername())) {
                    user.setSecurityAnswer(answer);
                    user.setSecurityQuestionID(Integer.parseInt(questionNumber));
                }
            }
            try (FileWriter writer = new FileWriter("Users.json")) {
                gson.toJson(users, writer);
            } catch (IOException e) {
                System.err.println("error" + e.getMessage());
//                throw e;
            }
            return Result.success(null, "Answer successfully set!");
        }
        return Result.failure(UserError.ANSWER_NOT_SET);
    }

    static boolean usernameValidation(String username) {
        String usernameRegex = "^[a-zA-Z0-9-]{3,20}$";
        return Pattern.compile(usernameRegex).matcher(username).matches();
    }

    static boolean emailValidation(String email) {
        String emailGetter = "(?<username>\\S+)@(?<domain>\\S+)";
        String emailUsernameRegex = "(?!.*\\.\\..*)[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]";
        String domainRegex = "[a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]\\.[a-zA-Z]{2,}";
        String invalidCharactersRegex = "(?!.*\\?.*)(?!.*>.*)(?!.*<.*)(?!.*,.*)(?!.*\".*)(?!.*'.*)(?!.*;.*)(?!.*:.*)" +
                "(?!.*/.*)(?!.*\\|.*)(?!.*].*)(?!.*\\[.*)(?!.*}.*)(?!.*\\{.*)(?!.*\\+.*)(?!.*=.*)(?!.*\\).*)" +
                "(?!.*\\(.*)(?!.*\\*.*)(?!.*&.*)(?!.*\\^.*)(?!.*%.*)(?!.*\\$.*)(?!.*#.*)(?!.*!.*)\\S+";
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

    public static Result<Void> checkPassword(String password) {
        if (password.length() < 8)
            return Result.failure(AuthError.PASSWORD_LENGTH);

        String specialCharacters = ".*[?><,\"';:/|\\\\\\]\\[{}()=+*&^@%$#!].*";
        if (!Pattern.compile(specialCharacters).matcher(password).matches())
            return Result.failure(AuthError.PASSWORD_SPECIAL_CHARACTERS);

        String containAlphabet = ".*[a-zA-Z].*";
        if (!Pattern.compile(containAlphabet).matcher(password).matches())
            return Result.failure(AuthError.PASSWORD_ALPHABET);

        String containNumber = ".*[0-9].*";
        if (!Pattern.compile(containNumber).matcher(password).matches())
            return Result.failure(AuthError.PASSWORD_NUMBERS);

        return Result.success(null);
    }

    private ArrayList<User> readAllUsers(Gson gson , String filePath) {
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
        App.getInstance().currentMenu = LoginMenuView.getInstance();
        return Result.success(null);
    }

    private User findUserByUsername(String username) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User> users = readAllUsers(gson , "Users.json");
        for(User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    private String generateRandomPassword() {
        int length = App.getInstance().random.nextInt(6) + 8;
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specials = "!@#$%^&*()-_=+[]{}";

        List<Character> passwordChars = new ArrayList<>();

        passwordChars.add(upper.charAt(App.getInstance().random.nextInt(upper.length())));
        passwordChars.add(lower.charAt(App.getInstance().random.nextInt(lower.length())));
        passwordChars.add(digits.charAt(App.getInstance().random.nextInt(digits.length())));
        passwordChars.add(specials.charAt(App.getInstance().random.nextInt(specials.length())));

        String all = upper + lower + digits + specials;
        for (int i = 4; i < length; i++) {
            passwordChars.add(all.charAt(App.getInstance().random.nextInt(all.length())));
        }

        Collections.shuffle(passwordChars);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

}
