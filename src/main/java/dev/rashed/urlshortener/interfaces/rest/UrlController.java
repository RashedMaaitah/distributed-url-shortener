package dev.rashed.urlshortener.interfaces.rest;

import dev.rashed.urlshortener.application.usecase.GetClickCountUseCase;
import dev.rashed.urlshortener.application.usecase.ResolveUrlUseCase;
import dev.rashed.urlshortener.application.usecase.ShortenUrlUseCase;
import dev.rashed.urlshortener.domain.model.Url;
import dev.rashed.urlshortener.interfaces.rest.dto.ClickStatsResponse;
import dev.rashed.urlshortener.interfaces.rest.dto.ShortenRequest;
import dev.rashed.urlshortener.interfaces.rest.dto.ShortenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UrlController {

    private final ShortenUrlUseCase shortenUrlUseCase;
    private final ResolveUrlUseCase resolveUrlUseCase;
    private final GetClickCountUseCase getClickCountUseCase;

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(@Valid @RequestBody ShortenRequest request) {
        Url url = shortenUrlUseCase.execute(request.getOriginalUrl(), request.getTtlSeconds());
        ShortenResponse response = ShortenResponse.builder()
                .shortCode(url.getShortCode())
                .originalUrl(url.getOriginalUrl())
                .expiresAt(url.getExpiresAt())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> resolve(
            @PathVariable String code,
            @RequestHeader(value = "User-Agent", required = false) String userAgent,
            @RequestHeader(value = "Referer", required = false) String referrer,
            HttpServletRequest request) {
        
        String ipAddress = request.getRemoteAddr();
        String originalUrl = resolveUrlUseCase.execute(code, ipAddress, userAgent, referrer);
        
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @GetMapping("/{code}/stats")
    public ResponseEntity<ClickStatsResponse> getStats(@PathVariable String code) {
        long count = getClickCountUseCase.execute(code);
        return ResponseEntity.ok(ClickStatsResponse.builder()
                .shortCode(code)
                .totalClicks(count)
                .build());
    }
}
