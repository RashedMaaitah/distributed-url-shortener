package dev.rashed.urlshortener.infrastructure.persistence;

import dev.rashed.urlshortener.domain.model.ClickEvent;
import dev.rashed.urlshortener.domain.model.Url;
import dev.rashed.urlshortener.infrastructure.persistence.entity.ClickEventEntity;
import dev.rashed.urlshortener.infrastructure.persistence.entity.UrlEntity;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {

    public UrlEntity toEntity(Url url) {
        if (url == null) return null;
        return UrlEntity.builder()
                .id(url.getId())
                .shortCode(url.getShortCode())
                .originalUrl(url.getOriginalUrl())
                .createdAt(url.getCreatedAt())
                .expiresAt(url.getExpiresAt())
                .build();
    }

    public Url toDomain(UrlEntity entity) {
        if (entity == null) return null;
        return Url.builder()
                .id(entity.getId())
                .shortCode(entity.getShortCode())
                .originalUrl(entity.getOriginalUrl())
                .createdAt(entity.getCreatedAt())
                .expiresAt(entity.getExpiresAt())
                .build();
    }

    public ClickEventEntity toEntity(ClickEvent domain) {
        if (domain == null) return null;
        return ClickEventEntity.builder()
                .id(domain.getId())
                .shortCode(domain.getShortCode())
                .ipAddress(domain.getIpAddress())
                .userAgent(domain.getUserAgent())
                .referrer(domain.getReferrer())
                .clickedAt(domain.getClickedAt())
                .build();
    }

    public ClickEvent toDomain(ClickEventEntity entity) {
        if (entity == null) return null;
        return ClickEvent.builder()
                .id(entity.getId())
                .shortCode(entity.getShortCode())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .referrer(entity.getReferrer())
                .clickedAt(entity.getClickedAt())
                .build();
    }
}
