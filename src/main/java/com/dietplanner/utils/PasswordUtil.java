package com.dietplanner.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashing password sederhana pakai SHA-256.
 * Password tidak pernah disimpan dalam bentuk asli di database.
 */
public class PasswordUtil {

    public static String hash(String passwordAsli) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hasil = md.digest(passwordAsli.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hasil) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cocok(String passwordInput, String passwordHashTersimpan) {
        if (passwordInput == null || passwordHashTersimpan == null) return false;
        return hash(passwordInput).equals(passwordHashTersimpan);
    }
}
