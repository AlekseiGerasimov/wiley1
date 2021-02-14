package ru.alexey.gerasimov.wiley.lib.cache.impl;

import ru.alexey.gerasimov.wiley.lib.cache.Cache;
import ru.alexey.gerasimov.wiley.lib.exception.CacheDataHandleException;
import ru.alexey.gerasimov.wiley.lib.exception.NotValidKeyException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * {@inheritDoc}
 */
public class LFUCache<K,V> implements Cache<K,V> {

    private final Map<K, InnerObject<K,V>> cacheMap;
    private final Integer maxSize;

    public LFUCache(int maxSize) {
        cacheMap = new HashMap<>(maxSize);
        this.maxSize = maxSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(K key, V value) {
        if (key == null) {
            throw new NotValidKeyException("Can't add value with null key");
        }
        if (cacheMap.size() == maxSize) {
            var removeKey = cacheMap.values().stream()
                    .min(Comparator.comparing(InnerObject::getCount))
                    .map(InnerObject::getKey)
                    .orElseThrow(() -> new CacheDataHandleException("Exception with handling cache map"));
            cacheMap.remove(removeKey);
        }
        cacheMap.put(key, new InnerObject<>(key, value));
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
        var innerObject = cacheMap.get(key);
        if (innerObject != null) {
            innerObject.setCount(innerObject.getCount() + 1);
            return innerObject.getValue();
        } else {
            return null;
        }
    }

    /**
     * The inner class for storing information and counter for an object.
     */
    private static class InnerObject<K,V> {
        private final K key;
        private final V value;
        private Integer count;

        public InnerObject(K key, V value) {
            this.key = key;
            this.value = value;
            this.count = 0;
        }

        public V getValue() {
            return value;
        }

        public Integer getCount() {
            return count;
        }

        public K getKey() {
            return key;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
