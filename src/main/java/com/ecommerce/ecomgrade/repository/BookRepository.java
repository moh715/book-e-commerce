package com.ecommerce.ecomgrade.repository;
import com.ecommerce.ecomgrade.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String keyword);
}
