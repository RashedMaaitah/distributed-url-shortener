package dev.rashed.urlshortener.domain.repository;

import dev.rashed.urlshortener.domain.model.Url;
import java.util.Optional;

public interface UrlRepository {
    Url save(Url url);
    Optional<Url> findByShortCode(String shortCode);
    Optional<Url> findByOriginalUrl(String originalUrl);
}
