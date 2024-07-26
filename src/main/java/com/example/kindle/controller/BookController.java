package com.example.kindle.controller;

import com.example.kindle.model.Book;
import com.example.kindle.model.User;
import com.example.kindle.service.BookService;
import com.example.kindle.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final Logger logger = LogManager.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(
            @RequestBody Book book, @AuthenticationPrincipal User user) {
        logger.info("Inside addBook method");
        book.setUser(user);
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestBody Book bookDetails) {
            Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublicationDate(bookDetails.getPublicationDate());
        book.setDescription(bookDetails.getDescription());

        return ResponseEntity.ok(bookService.addBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
        return ResponseEntity.ok("Book deleted successfully");
    }
}
