package dev.rashed.urlshortener.infrastructure.messaging.consumer;

import dev.rashed.urlshortener.domain.model.ClickEvent;
import dev.rashed.urlshortener.domain.repository.ClickEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClickEventConsumer {

    private final ClickEventRepository clickEventRepository;

    @KafkaListener(topics = "click-events", groupId = "url-shortener-group")
    public void consume(ClickEvent event) {
        log.info("Consuming click event for short code: {}", event.getShortCode());
        clickEventRepository.save(event);
    }
}
