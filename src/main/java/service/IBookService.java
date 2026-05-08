package service;

import com.ecommerce.ecomgrade.model.Book;
import com.ecommerce.ecomgrade.payload.request.BookRequest;
import com.ecommerce.ecomgrade.payload.response.BookResponse;

import java.util.List;

public interface IBookService {
    List<BookResponse> getAllBooks();
    BookResponse getBookById(Long id);
    BookResponse addBook(BookRequest request);
    void deleteBook(Long id);
    List<BookResponse> searchBooks(String keyword);
    Book findEntityById(Long id);
}