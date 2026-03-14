package dev.rashed.urlshortener.interfaces.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickStatsResponse {
    private String shortCode;
    private long totalClicks;
}
