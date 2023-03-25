package com.shortener.shorturl.infrastructure.message;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
@RequiredArgsConstructor
public class KafkaLogMessageSender implements LogMessageSender {

    @Value("${kafka.log.topic.name}")
    private String topicName;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String message) {
        kafkaTemplate.send(topicName, message);
    }
}
