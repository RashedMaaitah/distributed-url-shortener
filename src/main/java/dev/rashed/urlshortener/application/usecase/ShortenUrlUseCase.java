package dev.rashed.urlshortener.application.usecase;

import dev.rashed.urlshortener.domain.model.Url;
import dev.rashed.urlshortener.domain.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortenUrlUseCase {

    private final UrlService urlService;

    public Url execute(String originalUrl, Long ttlSeconds) {
        if (originalUrl == null || originalUrl.isBlank()) {
            throw new IllegalArgumentException("Original URL cannot be empty");
        }
        return urlService.shorten(originalUrl, ttlSeconds);
    }
}
