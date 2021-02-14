package ru.alexey.gerasimov.wiley.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexey.gerasimov.wiley.app.exception.BookNotFoundException;
import ru.alexey.gerasimov.wiley.app.repository.BookRepositoryStub;
import ru.alexey.gerasimov.wiley.lib.annotation.Cacheable;
import ru.alexey.gerasimov.wiley.lib.config.CacheStrategy;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepositoryStub bookRepositoryStub;

    @Cacheable(strategy = CacheStrategy.LRU, name = "getBooks")
    public String getBook(Long id) {
        return bookRepositoryStub.getInfoById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book with id %s wasn't found", id)));
    }
}
