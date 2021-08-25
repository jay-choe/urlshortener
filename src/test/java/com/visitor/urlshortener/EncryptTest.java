package com.visitor.urlshortener;

import com.visitor.urlshortener.util.ShortenerUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EncryptTest {


    ShortenerUtil util = new ShortenerUtil();

    @Test
    public void encryptTest() throws Exception {
        String secretKey = "1234567890abcdfe";
        String toEncrypt = "test";
        String encrypted = util.encryptApiKey(toEncrypt, secretKey);
        String decrypted = util.decryptApiKey(encrypted, secretKey);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        assertEquals(util.decryptApiKey(encrypted, secretKey), toEncrypt);
    }
}
