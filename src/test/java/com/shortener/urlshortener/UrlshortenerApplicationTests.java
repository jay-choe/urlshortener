package com.shortener.urlshortener;

import com.shortener.common.util.ShortenerUtil;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlshortenerApplicationTests {

	@Autowired
	ShortenerUtil shortenerUtil;
	@Value("${api-key}")
	String key;

	@Test
	void contextLoads() throws NoSuchAlgorithmException {
		System.out.println(key);
		System.out.println(shortenerUtil.encrypt(key));
	}

}
