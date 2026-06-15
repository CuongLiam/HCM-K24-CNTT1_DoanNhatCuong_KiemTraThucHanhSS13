package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;
import com.example.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        log.debug("Request get all books");

        List<Book> books = bookRepository.findAll();

        log.info("Get all books successfully, total books: {}", books.size());
        return books;
    }

    public Book getBookById(Long id) {
        log.debug("Request get book by id: {}", id);

        return bookRepository.findById(id)
                .map(book -> {
                    log.info("Get book by id successfully, id: {}, title: {}", book.getId(), book.getTitle());
                    return book;
                })
                .orElseThrow(() -> {
                    log.error("Book not found with id: {}", id);
                    return new BookNotFoundException(id);
                });
    }

    public Book createBook(Book book) {
        log.debug("Request create book: {}", book);

        Book savedBook = bookRepository.save(book);

        log.info("Create book successfully, id: {}, title: {}", savedBook.getId(), savedBook.getTitle());
        return savedBook;
    }

    public Book updateBook(Long id, Book book) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setCategory(book.getCategory());
        existing.setQuantity(book.getQuantity());

        return bookRepository.save(existing);
    }

    public Book patchBook(Long id, Book book) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (book.getTitle() != null) existing.setTitle(book.getTitle());
        if (book.getAuthor() != null) existing.setAuthor(book.getAuthor());
        if (book.getCategory() != null) existing.setCategory(book.getCategory());
        if (book.getQuantity() != null) existing.setQuantity(book.getQuantity());

        return bookRepository.save(existing);
    }

    public void deleteBook(Long id) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.delete(existing);
    }
}