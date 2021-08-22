package com.visitor.urlshortener.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class ShortenerUtil {

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
}
