package dev.rashed.urlshortener.domain.service;

import dev.rashed.urlshortener.domain.model.Url;
import dev.rashed.urlshortener.domain.repository.UrlRepository;
import dev.rashed.urlshortener.infrastructure.id.SnowflakeIdGenerator;
import dev.rashed.urlshortener.infrastructure.id.SnowflakeShortCodeEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final SnowflakeIdGenerator idGenerator;
    private final SnowflakeShortCodeEncoder shortCodeEncoder;

    @Override
    public Url shorten(String originalUrl, Long ttlSeconds) {
        return urlRepository.findByOriginalUrl(originalUrl)
                .orElseGet(() -> {
                    long id = idGenerator.nextId();
                    String shortCode = shortCodeEncoder.encode(id);
                    Url url = Url.builder()
                            .id(id)
                            .shortCode(shortCode)
                            .originalUrl(originalUrl)
                            .createdAt(LocalDateTime.now())
                            .expiresAt(ttlSeconds != null ? LocalDateTime.now().plusSeconds(ttlSeconds) : null)
                            .build();
                    return urlRepository.save(url);
                });
    }

    @Override
    public Optional<Url> resolve(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }
}
