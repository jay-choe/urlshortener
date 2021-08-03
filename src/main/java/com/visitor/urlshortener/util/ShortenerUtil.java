package com.visitor.urlshortener.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

@Component
public class ShortenerUtil {

    @Value("${api-key}")
    private String apiKey;

    public String encrypt(String toEncrypt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(toEncrypt.getBytes());

        return bytesToHex(md.digest());
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

//    public boolean apiKeyCheck(String apiKeyHash) throws NoSuchAlgorithmException {
//        return encrypt(apiKey).equals(apiKeyHash);
//    }
}
