package org.example.bookservice.service;

import feign.FeignException;
import org.example.bookservice.client.AuthorClient;
import org.example.bookservice.entity.Book;
import org.example.bookservice.exception.AuthorNotFoundException;
import org.example.bookservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorClient authorClient;

    public BookService(BookRepository bookRepository, AuthorClient authorClient) {
        this.bookRepository = bookRepository;
        this.authorClient = authorClient;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        try {
            authorClient.getAuthorById(book.getAuthorId());
        } catch (FeignException.NotFound exception) {
            throw new AuthorNotFoundException("AuthorId không tồn tại");
        }
        book.setId(null);
        return bookRepository.save(book);
    }
}
