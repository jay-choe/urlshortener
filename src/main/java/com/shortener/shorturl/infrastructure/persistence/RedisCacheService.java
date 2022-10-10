package com.shortener.shorturl.infrastructure.persistence;

import com.shortener.shorturl.domain.urlShortener.url.Url;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService implements CacheService<Url> {

    private final RedisTemplate redisTemplate;

    public RedisCacheService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Url get(String key) {
        ValueOperations<String, Url> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void set(Url toCache, int ttl) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(toCache.getTarget(), toCache.getOriginalUrl(), ttl, TimeUnit.MINUTES);
    }
}
