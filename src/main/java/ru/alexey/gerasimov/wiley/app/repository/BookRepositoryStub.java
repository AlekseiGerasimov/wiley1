package ru.alexey.gerasimov.wiley.app.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepositoryStub {
    private final Map<Long, String> bookStore = Map.of(
            1L, "First",
            2L, "Second"
    );

    public Optional<String> getInfoById(Long id) {
        return Optional.ofNullable(bookStore.get(id));
    }
}
