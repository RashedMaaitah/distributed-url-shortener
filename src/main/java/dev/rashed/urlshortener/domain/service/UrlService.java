package dev.rashed.urlshortener.domain.service;

import dev.rashed.urlshortener.domain.model.Url;
import java.util.Optional;

public interface UrlService {
    Url shorten(String originalUrl, Long ttlSeconds);
    Optional<Url> resolve(String shortCode);
}
