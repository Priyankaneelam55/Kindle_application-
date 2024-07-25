
package com.example.kindle.repository;

import com.example.kindle.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByUserId(Long userId);
}
