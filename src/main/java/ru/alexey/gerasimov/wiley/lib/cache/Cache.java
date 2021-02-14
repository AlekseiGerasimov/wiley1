package ru.alexey.gerasimov.wiley.lib.cache;

/**
 * Interface for cache strategies.
 */
public interface Cache<K,V> {

     /**
      * Add new value to cache
      *
      * @param key - the key for new object in the cache
      * @param value - value which putting to cache
      */
     void add(K key, V value);

     /**
      * Remove value from cache
      *
      * @param key - the key by which the value will be deleted
      */
     void remove(K key);

     /**
      * Get value by key from cache
      *
      * @param key - the key by which the value will be searched
      * @return value by key
      */
     V get(K key);
}
