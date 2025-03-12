package com.wallentx.hudi.blowfish;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.security.GeneralSecurityException;

public class OnlyForYouEncryptor {
    private static final String ALGORITHM = "Blowfish";
    private final SecretKeySpec secretKeySpec;

    public OnlyForYouEncryptor(String key) {
        // Ensure the key length is valid for Blowfish (4 to 56 bytes)
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 4 || keyBytes.length > 56) {
            throw new IllegalArgumentException("Blowfish key must be between 4 and 56 bytes.");
        }
        this.secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public String encryptForYou(String value) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptForYou(String encrypted) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
