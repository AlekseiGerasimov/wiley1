package ru.alexey.gerasimov.wiley.app.aspect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.alexey.gerasimov.wiley.lib.cache.Cache;
import ru.alexey.gerasimov.wiley.lib.config.CacheFactory;
import ru.alexey.gerasimov.wiley.lib.config.CacheStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CacheStore {
    private final Map<String, Cache> cacheMap = new HashMap<>();

    @Value("${app.cache.size}")
    private Integer cacheSize;

    public Cache getCache(String key, CacheStrategy strategy) {
        return Optional.ofNullable(cacheMap.get(key))
                .orElseGet(() -> {
                    var newInstance = CacheFactory.createNewInstance(strategy, cacheSize);
                    cacheMap.put(key, newInstance);
                    return newInstance;
                });
    }
}
