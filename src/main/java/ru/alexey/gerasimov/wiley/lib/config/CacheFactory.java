package ru.alexey.gerasimov.wiley.lib.config;

import ru.alexey.gerasimov.wiley.lib.cache.Cache;
import ru.alexey.gerasimov.wiley.lib.cache.impl.LFUCache;
import ru.alexey.gerasimov.wiley.lib.cache.impl.LRUCache;

import java.util.Map;
import java.util.function.Function;

/**
 * Factory for creating new instances of caches.
 */
public class CacheFactory {
    private final static Map<CacheStrategy, Function<Integer, Cache<?, ?>>> cacheStore =
            Map.of(CacheStrategy.LFU, LFUCache::new,
                    CacheStrategy.LRU, LRUCache::new
            );

    public static Cache createNewInstance(CacheStrategy strategy, Integer size) {
        return cacheStore.get(strategy).apply(size);
    }
}
