package models.user;
//SHA-256

public class Hash {
    String hash;

    public static String calculateHash(String input) {
        //TODO : implement 256SHA hash
        return input;
    }

    public Hash(String input) {
        this.hash = calculateHash(input);
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Hash)) {
            return false;
        }   
        Hash other = (Hash) obj;
        return this.getHash().equals(other.getHash());
    }
}
