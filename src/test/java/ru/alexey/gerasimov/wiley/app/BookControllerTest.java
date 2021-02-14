package ru.alexey.gerasimov.wiley.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.alexey.gerasimov.wiley.app.controller.BookController;
import ru.alexey.gerasimov.wiley.app.service.BookService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    @DisplayName("Get book by id")
    public void getBookById() {
        var id = 1L;
        var expectedInfo = "random_string";
        when(bookService.getBook(id)).thenReturn(expectedInfo);

        var bookInfo = bookController.getBook(id);

        assertThat(bookInfo).isEqualTo(expectedInfo);
        verifyNoMoreInteractions(bookService);
    }

}
