package com.shortener.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.util.Base64Utils;

public class ShortenerUtil {

    public static String encrypt(String toEncrypt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(toEncrypt.getBytes());

        return bytesToHex(md.digest());
    }

    public static String encryptApiKey(String toEncrypt, String key) throws Exception{
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return Base64Utils.encodeToUrlSafeString(cipher.doFinal(toEncrypt.getBytes()));
    }

    public static String decryptApiKey(String toDecrypt, String key) throws Exception{
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return new String(cipher.doFinal(Base64Utils.decodeFromUrlSafeString(toDecrypt)));
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
