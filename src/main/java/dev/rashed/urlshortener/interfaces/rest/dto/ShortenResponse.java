package dev.rashed.urlshortener.interfaces.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenResponse {
    private String shortCode;
    private String originalUrl;
    private LocalDateTime expiresAt;
}
