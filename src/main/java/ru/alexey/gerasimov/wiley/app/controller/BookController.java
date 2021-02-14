package ru.alexey.gerasimov.wiley.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alexey.gerasimov.wiley.app.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @RequestMapping("/book")
    public String getBook(@RequestParam Long id) {
        return bookService.getBook(id);
    }
}
