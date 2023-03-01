package com.shortener.shorturl.application.shortUrl.service.generator;

import com.shortener.shorturl.application.shortUrl.exception.NotSupportMethodException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FixedShortURLGenerateStrategy implements ShortURLGenerateStrategy{

    @Override
    public String create(String originalURL) {
        return null;
    }

    @Override
    public String create() {
        throw new NotSupportMethodException(this.getClass().getName() + "create");
    }


    private String hashValueOf(String toEncrypt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(toEncrypt.getBytes());

        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
