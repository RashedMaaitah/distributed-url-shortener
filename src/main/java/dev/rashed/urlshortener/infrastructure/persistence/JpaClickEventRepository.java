package dev.rashed.urlshortener.infrastructure.persistence;

import dev.rashed.urlshortener.domain.model.ClickEvent;
import dev.rashed.urlshortener.domain.repository.ClickEventRepository;
import dev.rashed.urlshortener.infrastructure.persistence.entity.ClickEventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
public class JpaClickEventRepository implements ClickEventRepository {

    private final SpringDataClickEventRepository repository;
    private final UrlMapper mapper;

    @Override
    public ClickEvent save(ClickEvent clickEvent) {
        ClickEventEntity entity = mapper.toEntity(clickEvent);
        ClickEventEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public long countByShortCode(String shortCode) {
        return repository.countByShortCode(shortCode);
    }

}
