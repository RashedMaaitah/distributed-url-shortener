package dev.rashed.urlshortener.infrastructure.persistence;

import dev.rashed.urlshortener.domain.model.Url;
import dev.rashed.urlshortener.domain.repository.UrlRepository;
import dev.rashed.urlshortener.infrastructure.persistence.entity.UrlEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUrlRepository implements UrlRepository {

    private final SpringDataUrlRepository repository;
    private final UrlMapper mapper;

    @Override
    public Url save(Url url) {
        UrlEntity entity = mapper.toEntity(url);
        UrlEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Url> findByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Url> findByOriginalUrl(String originalUrl) {
        return repository.findByOriginalUrl(originalUrl)
                .map(mapper::toDomain);
    }


}
