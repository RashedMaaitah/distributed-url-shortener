package dev.rashed.urlshortener.infrastructure.cache;

import dev.rashed.urlshortener.domain.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisUrlCache {

    private final RedisTemplate<String, Url> redisTemplate;
    private static final String CACHE_PREFIX = "url:";

    public void put(Url url) {
        if (url == null || url.getShortCode() == null) return;
        
        String key = CACHE_PREFIX + url.getShortCode();
        if (url.getExpiresAt() != null) {
            Duration ttl = Duration.between(java.time.LocalDateTime.now(), url.getExpiresAt());
            if (!ttl.isNegative()) {
                redisTemplate.opsForValue().set(key, url, ttl);
            }
        } else {
            // Default TTL for non-expiring URLs to avoid filling up Redis infinitely, 
            // though the requirement says "evict on expiry"
            redisTemplate.opsForValue().set(key, url, Duration.ofDays(7));
        }
    }

    public Optional<Url> get(String shortCode) {
        Url url = redisTemplate.opsForValue().get(CACHE_PREFIX + shortCode);
        return Optional.ofNullable(url);
    }

    public void evict(String shortCode) {
        redisTemplate.delete(CACHE_PREFIX + shortCode);
    }
}
