package com.example.library;

import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void getAllBooks_returnList() {
        List<Book> books = Arrays.asList(
                new Book(1L, "Java Basic", "Author A", "Programming", 5),
                new Book(2L, "Spring Boot", "Author B", "Programming", 3)
        );

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Java Basic", result.get(0).getTitle());
        assertEquals("Spring Boot", result.get(1).getTitle());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_found() {
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", "Software Engineering", 7);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Clean Code", result.getTitle());
        assertEquals("Robert C. Martin", result.getAuthor());

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_notFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.getBookById(99L)
        );

        assertEquals("Book not found with id: 99", exception.getMessage());

        verify(bookRepository, times(1)).findById(99L);
    }
}