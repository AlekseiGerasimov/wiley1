package ru.alexey.gerasimov.wiley.lib.cache.impl;

import ru.alexey.gerasimov.wiley.lib.cache.Cache;
import ru.alexey.gerasimov.wiley.lib.exception.NotValidKeyException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
public class LRUCache<K,V> implements Cache<K,V> {

    private final Map<K, V> cacheMap;

    public LRUCache(int maxSize) {
        cacheMap = new CacheLinkedHashMap<>(maxSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(K key, V value) {
        if (key == null) {
            throw new NotValidKeyException("Can't add value with null key");
        }
        cacheMap.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(K key) {
        if (key == null) {
            throw new NotValidKeyException("Can't remove value with null key");
        }
        cacheMap.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new NotValidKeyException("Can't get value with null key");
        }
        return Optional.ofNullable(cacheMap.get(key))
                .map(value -> {
                    cacheMap.put(key, cacheMap.remove(key));
                    return value;
                })
                .orElse(null);
    }

    /**
     * The custom linked hash map with overriding removeEldestEntry method.
     * This approach allows to remove old values from map.
     */
    private static class CacheLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
        private final int maxSize;

        public CacheLinkedHashMap(int initialCapacity) {
            super(initialCapacity);
            this.maxSize = initialCapacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            if (this.size() > maxSize) {
                return true;
            }
            return super.removeEldestEntry(eldest);
        }
    }
}
