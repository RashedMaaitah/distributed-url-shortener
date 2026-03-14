package dev.rashed.urlshortener.domain.service;

import dev.rashed.urlshortener.domain.repository.ClickEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClickCountServiceImpl implements ClickCountService {

    private final ClickEventRepository clickEventRepository;

    @Override
    public long getCount(String shortCode) {
        return clickEventRepository.countByShortCode(shortCode);
    }
}
