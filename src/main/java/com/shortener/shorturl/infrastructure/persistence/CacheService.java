package com.shortener.shorturl.infrastructure.persistence;

public interface CacheService<T> {
    T get(String key);
    void set(T toCache, int ttl);
}
