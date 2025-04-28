package models;

import models.user.User;
import models.user.UserRepository;

public class App {
    public static Menu currentMenu = Menu.Game;
    public static boolean play = true;
    public static UserRepository userRepository;
    public static User registeredUser;
    public static User logedInUser;

}
