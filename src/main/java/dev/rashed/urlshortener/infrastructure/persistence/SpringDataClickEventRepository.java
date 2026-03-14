package dev.rashed.urlshortener.infrastructure.persistence;


import dev.rashed.urlshortener.infrastructure.persistence.entity.ClickEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataClickEventRepository extends JpaRepository<ClickEventEntity, Long> {
    long countByShortCode(String shortCode);
}
