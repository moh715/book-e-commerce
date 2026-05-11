package com.ecommerce.ecomgrade.service.impl;

import com.ecommerce.ecomgrade.exception.APIException;
import com.ecommerce.ecomgrade.exception.ResourceNotFound;
import com.ecommerce.ecomgrade.model.Book;
import com.ecommerce.ecomgrade.model.Category;
import com.ecommerce.ecomgrade.payload.request.BookRequest;
import com.ecommerce.ecomgrade.payload.response.BookResponse;
import com.ecommerce.ecomgrade.repository.BookRepository;
import com.ecommerce.ecomgrade.repository.CategoryRepository;
import com.ecommerce.ecomgrade.service.IBookService;
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
        return bookRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Book findEntityById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Book", "id", id));
    }
}
