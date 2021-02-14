package ru.alexey.gerasimov.wiley.lib.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.alexey.gerasimov.wiley.lib.cache.impl.LFUCache;
import ru.alexey.gerasimov.wiley.lib.exception.NotValidKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LFUCacheTest {

    private LFUCache<Integer, String> lfuCache;
    private final static Integer MAX_SIZE = 3;

    @BeforeEach
    public void init() {
        lfuCache = new LFUCache<>(MAX_SIZE);
    }

    @Test
    @DisplayName("Add new value to empty cache")
    public void addNewValue_success() {
        final Integer key = 1;
        final String value = "value";

        assertThat(lfuCache).isNotNull()
                .extracting(cache -> cache.get(key)).isNull();

        lfuCache.add(key, value);

        assertThat(lfuCache).isNotNull()
                .extracting(cache -> cache.get(key)).isEqualTo(value);
    }

    @Test
    @DisplayName("Add new value to cache with remove old values")
    public void addNewValueWithRemoveOldValues_success() {
        lfuCache.add(1, "value_1");
        lfuCache.add(2, "value_2");
        lfuCache.add(3, "value_3");

        lfuCache.get(1);
        lfuCache.get(1);
        lfuCache.get(2);
        lfuCache.get(2);
        lfuCache.get(3);

        lfuCache.add(4, "value_4");

        assertThat(lfuCache.get(3)).isNull();
        assertThat(lfuCache.get(1)).isEqualTo("value_1");
        assertThat(lfuCache.get(2)).isEqualTo("value_2");
        assertThat(lfuCache.get(4)).isEqualTo("value_4");
    }

    @Test
    @DisplayName("Add new value with null key")
    public void addNewValue_fail() {
        final String value = "value";

        assertThatThrownBy(() -> lfuCache.add(null, value))
                .isInstanceOf(NotValidKeyException.class)
                .hasMessage("Can't add value with null key");
    }

    @Test
    @DisplayName("Remove value from cache")
    public void removeValue_success() {
        final Integer key = 1;
        final String value = "value";

        lfuCache.add(key, value);

        assertThat(lfuCache.get(key)).isEqualTo(value);

        lfuCache.remove(key);

        assertThat(lfuCache.get(key)).isNull();
    }

    @Test
    @DisplayName("Remove value from cache with null key")
    public void removeValue_fail() {
        assertThatThrownBy(() -> lfuCache.remove(null))
                .isInstanceOf(NotValidKeyException.class)
                .hasMessage("Can't remove value with null key");
    }

    @Test
    @DisplayName("Get value from cache")
    public void getValue_success() {
        final Integer key = 1;
        final String value = "value";

        assertThat(lfuCache.get(key)).isNull();

        lfuCache.add(key, value);

        assertThat(lfuCache.get(key)).isEqualTo(value);
    }

    @Test
    @DisplayName("Get value from cache with not existing key")
    public void getValueWithNotExistingKey_success() {
        assertThat(lfuCache.get(1)).isNull();
    }

    @Test
    @DisplayName("Get value from cache with null key")
    public void getValue_fail() {
        assertThatThrownBy(() -> lfuCache.get(null))
                .isInstanceOf(NotValidKeyException.class)
                .hasMessage("Can't get value with null key");
    }
}
