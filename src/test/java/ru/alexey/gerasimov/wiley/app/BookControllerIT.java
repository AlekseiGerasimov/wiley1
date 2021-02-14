package ru.alexey.gerasimov.wiley.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.alexey.gerasimov.wiley.app.repository.BookRepositoryStub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("Integration")
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private BookRepositoryStub bookRepositoryStub;

    @Test
    @DisplayName("Get existing book info by id")
    public void getExistingBookById() throws Exception {
        var info = mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(info).isEqualTo("First");
    }

    @Test
    @DisplayName("Get existing book info by id with caching. Try to create multiple requests and check, that cache is working")
    public void getExistingBookByIdWIthCaching() throws Exception {
        var info = mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        verify(bookRepositoryStub).getInfoById(1L);
        assertThat(info).isEqualTo("First");

        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON));

        verify(bookRepositoryStub, times(1)).getInfoById(1L);
    }

    @Test
    @DisplayName("Get exception when book not found")
    public void getExceptionWhenBookNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .param("id", "12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Book with id 12345 wasn't found"))
                .andExpect(jsonPath("$.errorCode").value(1));
    }
}
