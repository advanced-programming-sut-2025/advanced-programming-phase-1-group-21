package models.user;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static UserRepository instance = null;
    List<User> users;

    private UserRepository() {
        users = new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }
    
    public User findUserByUsername(String username) {
        throw new UnsupportedOperationException("");
    }
}
