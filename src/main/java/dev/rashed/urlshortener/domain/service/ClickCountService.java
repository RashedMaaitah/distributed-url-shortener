package dev.rashed.urlshortener.domain.service;

public interface ClickCountService {
    long getCount(String shortCode);
}
