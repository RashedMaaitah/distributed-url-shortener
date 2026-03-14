package dev.rashed.urlshortener.infrastructure.messaging.producer;

import dev.rashed.urlshortener.domain.model.ClickEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClickEventProducer {

    private final KafkaTemplate<String, ClickEvent> kafkaTemplate;
    private static final String TOPIC = "click-events";

    public void sendClickEvent(ClickEvent event) {
        log.info("Producing click event for short code: {}", event.getShortCode());
        kafkaTemplate.send(TOPIC, event.getShortCode(), event);
    }
}
