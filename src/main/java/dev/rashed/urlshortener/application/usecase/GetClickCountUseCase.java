package dev.rashed.urlshortener.application.usecase;

import dev.rashed.urlshortener.domain.service.ClickCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetClickCountUseCase {

    private final ClickCountService clickCountService;

    public long execute(String shortCode) {
        return clickCountService.getCount(shortCode);
    }
}
