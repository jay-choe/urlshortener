package com.shortener.urlshortener;

import com.shortener.common.util.Base62;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    private final Base62 base62 = new Base62();

    @Test
    void 허용된문자열체크() {
        boolean result1 = base62.checkInvalidCharacter("dns-query");
        assertEquals(false, result1);
        boolean result2 = base62.checkInvalidCharacter("dnsquery");
        assertEquals(true, result2);
    }
}
