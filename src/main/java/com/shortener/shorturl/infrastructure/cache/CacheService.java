package com.shortener.shorturl.infrastructure.cache;

public interface CacheService<T> {
    T get(String key);

    void set(T toCache, int ttl);

    void set(T toCache);

    boolean exist(String key);
}
