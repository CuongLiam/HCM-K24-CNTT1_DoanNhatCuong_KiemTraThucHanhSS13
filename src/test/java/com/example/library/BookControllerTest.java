package com.example.library;

import com.example.library.controller.BookController;
import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.GlobalExceptionHandler;
import com.example.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(GlobalExceptionHandler.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooks_returnJsonList() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1L, "Java Basic", "Author A", "Programming", 5),
                new Book(2L, "Spring Boot", "Author B", "Programming", 3)
        );

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Java Basic"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Spring Boot"));
    }

    @Test
    void getBookById_found_returnJsonObject() throws Exception {
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", "Software Engineering", 7);

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.category").value("Software Engineering"))
                .andExpect(jsonPath("$.quantity").value(7));
    }

    @Test
    void getBookById_notFound_return404() throws Exception {
        when(bookService.getBookById(99L)).thenThrow(new BookNotFoundException(99L));

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Book not found with id: 99"));
    }
}