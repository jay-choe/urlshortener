package com.shortener.shorturl.infrastructure.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!prod")
@Slf4j
public class ConsoleLogMessageSender implements LogMessageSender {

    @Override
    public void send(String message) {
      log.info(message);
    }
}
