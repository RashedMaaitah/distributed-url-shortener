package dev.rashed.urlshortener.domain.repository;

import dev.rashed.urlshortener.domain.model.ClickEvent;

public interface ClickEventRepository {
    ClickEvent save(ClickEvent clickEvent);
    long countByShortCode(String shortCode);
}
