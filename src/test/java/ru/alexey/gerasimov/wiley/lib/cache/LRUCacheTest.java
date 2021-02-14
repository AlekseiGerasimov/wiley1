package ru.alexey.gerasimov.wiley.lib.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.alexey.gerasimov.wiley.lib.cache.impl.LRUCache;
import ru.alexey.gerasimov.wiley.lib.exception.NotValidKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LRUCacheTest {

    private LRUCache<Integer, String> lruCache;
    private final static Integer MAX_SIZE = 3;

    @BeforeEach
    public void init() {
        lruCache = new LRUCache<>(MAX_SIZE);
    }

    @Test
    @DisplayName("Add new value to empty cache")
    public void addNewValue_success() {
        final Integer key = 1;
        final String value = "value";

        assertThat(lruCache).isNotNull()
                .extracting(cache -> cache.get(key)).isNull();

        lruCache.add(key, value);

        assertThat(lruCache).isNotNull()
                .extracting(cache -> cache.get(key)).isEqualTo(value);
    }

    @Test
    @DisplayName("Add new value to cache with remove old values")
    public void addNewValueWithRemoveOldValues_success() {
        lruCache.add(1, "value_1");
        lruCache.add(2, "value_2");
        lruCache.add(3, "value_3");
        lruCache.add(4, "value_4");

        assertThat(lruCache.get(1)).isNull();
        assertThat(lruCache.get(2)).isEqualTo("value_2");
        assertThat(lruCache.get(3)).isEqualTo("value_3");
        assertThat(lruCache.get(4)).isEqualTo("value_4");
    }

    @Test
    @DisplayName("Add new value with null key")
    public void addNewValue_fail() {
        final String value = "value";

        assertThatThrownBy(() -> lruCache.add(null, value))
                .isInstanceOf(NotValidKeyException.class)
                .hasMessage("Can't add value with null key");
    }

    @Test
    @DisplayName("Remove value from cache")
    public void removeValue_success() {
        final Integer key = 1;
        final String value = "value";

        lruCache.add(key, value);

        assertThat(lruCache.get(key)).isEqualTo(value);

        lruCache.remove(key);

        assertThat(lruCache.get(key)).isNull();
    }

    @Test
    @DisplayName("Remove value from cache with null key")
    public void removeValue_fail() {
        assertThatThrownBy(() -> lruCache.remove(null))
                .isInstanceOf(NotValidKeyException.class)
                .hasMessage("Can't remove value with null key");
    }

    @Test
    @DisplayName("Get value from cache")
    public void getValue_success() {
        final Integer key = 1;
        final String value = "value";

        assertThat(lruCache.get(key)).isNull();

        lruCache.add(key, value);

        assertThat(lruCache.get(key)).isEqualTo(value);
    }

    @Test
    @DisplayName("Get value from cache with not existing key")
    public void getValueWithNotExistingKey_success() {
        assertThat(lruCache.get(1)).isNull();
    }

    @Test
    @DisplayName("Get value from cache with null key")
    public void getValue_fail() {
        assertThatThrownBy(() -> lruCache.get(null))
                .isInstanceOf(NotValidKeyException.class)
                .hasMessage("Can't get value with null key");
    }
}
