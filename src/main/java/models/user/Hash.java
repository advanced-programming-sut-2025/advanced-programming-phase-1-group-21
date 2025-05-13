package models.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Hash {
    private String hashedPassword;
    private static final String SALT = "namak";

    public Hash(String hashedPassword) {
        this.hashedPassword = hashPassword(hashedPassword);
    }

    public boolean verify(String inputPassword) {
        String inputHashed = hashPassword(inputPassword);
        return this.hashedPassword.equals(inputHashed);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(SALT.getBytes());
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
