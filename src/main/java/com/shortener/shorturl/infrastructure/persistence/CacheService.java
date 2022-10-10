package com.shortener.shorturl.infrastructure.persistence;

public interface CacheService<T> {
    T get();
    void set(T toCache, int ttl);
}
