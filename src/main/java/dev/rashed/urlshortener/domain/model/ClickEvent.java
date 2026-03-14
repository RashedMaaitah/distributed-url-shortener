package dev.rashed.urlshortener.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickEvent {
    private Long id;
    private String shortCode;
    private String ipAddress;
    private String userAgent;
    private String referrer;
    private LocalDateTime clickedAt;
}
