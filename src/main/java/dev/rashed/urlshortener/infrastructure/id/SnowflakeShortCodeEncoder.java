package dev.rashed.urlshortener.infrastructure.id;

import org.springframework.stereotype.Component;

/**
 * Base62 encoder for long IDs.
 */
@Component
public class SnowflakeShortCodeEncoder {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = CHARACTERS.length();

    public String encode(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(CHARACTERS.charAt((int) (id % BASE)));
            id /= BASE;
        }
        return sb.reverse().toString();
    }
}
