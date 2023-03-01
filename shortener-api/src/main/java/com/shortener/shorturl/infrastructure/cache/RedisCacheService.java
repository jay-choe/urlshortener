package com.shortener.shorturl.infrastructure.cache;

import com.shortener.shorturl.domain.urlShortener.url.Url;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService implements CacheService<Url> {

    private final RedisTemplate<String, Url> redisTemplate;

    public RedisCacheService(RedisTemplate<String, Url> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Url get(String key) {
        ValueOperations<String, Url> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void set(Url toCache, int ttl) {
        ValueOperations<String, Url> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(toCache.getAddress(), toCache, ttl, TimeUnit.MINUTES);
    }

    @Override
    public void set(Url toCache) {
        ValueOperations<String, Url> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(toCache.getAddress(), toCache);
    }

    @Override
    public boolean exist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
