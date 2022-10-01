package com.shortener.common.util;

import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Base62 {
    private final int BASE = 62;
    public  final String BASE_FORMAT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encoding(BigInteger hashValue) {

        if (hashValue.compareTo(BigInteger.ZERO) == 0) {
            return String.valueOf(BASE_FORMAT.charAt(0));
        }

        StringBuffer sb = new StringBuffer();

        while (hashValue.compareTo(BigInteger.ZERO) > 0) {
            sb.append(BASE_FORMAT.charAt((hashValue.mod(BigInteger.valueOf(BASE)).intValue())));
            hashValue = hashValue.divide(BigInteger.valueOf(BASE));
        }
        return sb.reverse().toString();
    }

    public String decoding(String encodedStr) {
        BigInteger retValue = BigInteger.ZERO;
        int indexOfEncodedStr = 0;
        while (indexOfEncodedStr < encodedStr.length()) {
            retValue = (retValue.multiply(BigInteger.valueOf(BASE))
                .add(BigInteger.valueOf(BASE_FORMAT.indexOf(encodedStr.charAt(indexOfEncodedStr)))));
            ++indexOfEncodedStr;
        }

        return addPadding(retValue.toString(16));
    }

    public String addPadding(String decodedValue) {
        if (decodedValue.length() < 10) {
            log.info("Add padding - decodedValue: {}", decodedValue);
            while (decodedValue.length() < 10) {
                decodedValue = "0".concat(decodedValue);
            }
            log.info("Add padding - Padded value: {}", decodedValue);
        }
        return decodedValue;
    }
    public boolean checkInvalidCharacter(String value) {
        int len = value.length();
        for (int i = 0; i < len; i++) {
            if (BASE_FORMAT.indexOf(value.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }
}
