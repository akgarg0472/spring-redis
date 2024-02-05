package com.akgarg.springredis.redis;

import jakarta.annotation.Nonnull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;

@Service
public class RedisService {

    private static final Logger LOG = LogManager.getLogger(RedisService.class);

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(final RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void insertKeyValueWithoutExpiration(
            @Nonnull final String key,
            @Nonnull final Object value
    ) {
        Objects.requireNonNull(key, "Key can't be null");
        Objects.requireNonNull(value, "Object can't be null");

        final Boolean recordInserted = redisTemplate.opsForValue()
                .setIfAbsent(key, value);

        LOG.info("insertKeyWithoutExpiration({}): {}", key, recordInserted);
    }

    public void insertKeyValueWithExpiration(
            @Nonnull final String key,
            @Nonnull final Object value,
            @Nonnull final Duration ttl
    ) {
        Objects.requireNonNull(key, "Key can't be null");
        Objects.requireNonNull(value, "Object can't be null");
        Objects.requireNonNull(ttl, "TTL can't be null");

        final Boolean recordInserted = redisTemplate.opsForValue()
                .setIfAbsent(key, value, ttl);

        LOG.info("insertKeyValueWithExpiration({}, {}s): {}", key, ttl.toSeconds(), recordInserted);
    }

    public Set<String> getAllKeyValues(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void removeKey(final String key) {
        final Boolean keyDeleted = redisTemplate.delete(key);
        LOG.info("Key {} deleted: {}", key, keyDeleted);
    }

}
