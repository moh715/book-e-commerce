package com.ecommerce.ecomgrade.service.impl;

import com.bookverse.exception.APIException;
import com.bookverse.exception.ResourceNotFound;
import com.bookverse.model.Book;
import com.bookverse.model.Category;
import com.bookverse.payload.request.BookRequest;
import com.bookverse.payload.response.BookResponse;
import com.bookverse.repository.BookRepository;
import com.bookverse.repository.CategoryRepository;
import com.bookverse.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private BookResponse toResponse(Book book) {
        String catName = (book.getCategory() != null) ? book.getCategory().getName() : null;
        return new BookResponse(
                book.getId(), book.getTitle(), book.getAuthor(),
                book.getPrice(), book.getStock(), book.getImageUrl(), catName
        );
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse getBookById(Long id) {
        return toResponse(findEntityById(id));
    }

    @Override
    public BookResponse addBook(BookRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new APIException("Book title cannot be empty.");
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());
        book.setImageUrl(request.getImageUrl());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFound("Category", "id", request.getCategoryId()));
            book.setCategory(category);
        }

        return toResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        findEntityById(id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponse> searchBooks(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new APIException("Search keyword cannot be empty.");
        }
        return bookRepository.findByTitleContaining(keyword).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Book findEntityById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Book", "id", id));
    }
}
