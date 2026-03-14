package dev.rashed.urlshortener.application.usecase;

import dev.rashed.urlshortener.domain.model.ClickEvent;
import dev.rashed.urlshortener.domain.model.Url;
import dev.rashed.urlshortener.domain.service.UrlExpiredException;
import dev.rashed.urlshortener.domain.service.UrlNotFoundException;
import dev.rashed.urlshortener.domain.service.UrlService;
import dev.rashed.urlshortener.infrastructure.cache.RedisUrlCache;
import dev.rashed.urlshortener.infrastructure.messaging.producer.ClickEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResolveUrlUseCase {

    private final UrlService urlService;
    private final RedisUrlCache redisUrlCache;
    private final ClickEventProducer clickEventProducer;

    public String execute(String shortCode, String ipAddress, String userAgent, String referrer) {
        Url url = redisUrlCache.get(shortCode)
                .or(() -> urlService.resolve(shortCode).map(u -> {
                    redisUrlCache.put(u);
                    return u;
                }))
                .orElseThrow(() -> new UrlNotFoundException("Short code not found: " + shortCode));

        if (url.isExpired()) {
            redisUrlCache.evict(shortCode);
            throw new UrlExpiredException("URL has expired");
        }

        ClickEvent event = ClickEvent.builder()
                .shortCode(shortCode)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .referrer(referrer)
                .clickedAt(LocalDateTime.now())
                .build();
        
        clickEventProducer.sendClickEvent(event);

        return url.getOriginalUrl();
    }
}
