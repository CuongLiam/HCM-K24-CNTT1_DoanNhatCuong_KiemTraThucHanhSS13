package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        // TODO: Thêm log DEBUG, INFO tại đây
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        // TODO: Thêm log DEBUG, INFO, ERROR tại đây
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book createBook(Book book) {
        // TODO: Thêm log DEBUG, INFO tại đây
        return bookRepository.save(book);
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
