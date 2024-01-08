package org.codingweek.model;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Allow to hash and check password */
public class PasswordUtility {

    /** Hash the password */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());

            // Convert byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                hexString.append(String.format("%02x", b & 0xFF));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /** Check if the password is correct */
    public static boolean checkPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
