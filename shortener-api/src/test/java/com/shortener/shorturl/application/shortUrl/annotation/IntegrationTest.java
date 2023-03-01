package com.shortener.shorturl.application.shortUrl.annotation;

import com.shortener.shorturl.application.shortUrl.config.TestRedisConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@Import(TestRedisConfiguration.class)
@SpringBootTest
public @interface IntegrationTest {
}
