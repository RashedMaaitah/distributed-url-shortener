package dev.rashed.urlshortener.infrastructure.persistence;

import dev.rashed.urlshortener.infrastructure.persistence.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByShortCode(String shortCode);

    Optional<UrlEntity> findByOriginalUrl(String originalUrl);
}