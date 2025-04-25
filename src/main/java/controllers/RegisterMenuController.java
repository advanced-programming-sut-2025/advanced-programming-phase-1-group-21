package controllers;

import models.App;
import models.result.Result;
import models.result.errorTypes.AuthError;
import models.result.errorTypes.UserError;
import models.user.User;
import models.user.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static models.user.Gender.getGenderByName;

public class RegisterMenuController{


    public Result<Void> showCurrentMenu(){
        return new Result<>(null , null , "Register Menu");
    }

    public Result<User> register(String username, String password,String passwordConfirm, String nickname , String email, String gender) {
        if(App.userRepository.findUserByUsername(username) != null)
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
        return new Result<>(user , null , null);
    }

    public Result<Void> pickQuestion(String answer, String repeatAnswer, int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean usernameValidation(String username) {
        String usernameRegex = "^[a-zA-Z0-9-]{3,20}$";
        return Pattern.compile(usernameRegex).matcher(username).matches();
    }

    private boolean emailValidation(String email) {
        String emailGetter = "(?<username>\\S+)@(?<domain>\\S+)";
        String emailUsernameRegex = "(?!.*\\.\\..*)[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]";
        String domainRegex = "[a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]\\.[a-zA-Z]{2,}";
        Matcher matcher;

        matcher = Pattern.compile(emailGetter).matcher(email);
        if(!matcher.matches())
            return false;
        if(!Pattern.compile(emailUsernameRegex).matcher(matcher.group("username")).matches())
            return false;
        if(!Pattern.compile(domainRegex).matcher(matcher.group("domain")).matches())
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
}
