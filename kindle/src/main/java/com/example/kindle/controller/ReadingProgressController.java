package com.example.kindle.controller;


import com.example.kindle.model.Book;
import com.example.kindle.model.ReadingProgress;
import com.example.kindle.service.BookService;
import com.example.kindle.service.ReadingProgressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReadingProgressController {
    private static final Logger logger = LogManager.getLogger(ReadingProgressController.class);
    @Autowired
    private ReadingProgressService progressService;

    @Autowired
    private BookService bookService;


    @PostMapping
    public ReadingProgress saveProgress(@RequestBody ReadingProgress progress) {
        return progressService.saveProgress(progress);
    }

    @GetMapping("/fetchBooks")
    public ResponseEntity<List<Book>> fetchBooks(@RequestParam Long userId) {
        List<Book> books = bookService.getBooksByUserId(userId);
        return new ResponseEntity<>(books, HttpStatus.CREATED);
    }


    @GetMapping("/startRead")
    public String startRead(@RequestBody Book book) {
        try {
            return progressService.readPage(book, 1);
        } catch (Exception e) {
            return "Error starting read session: " + e.getMessage();
        }
    }

    @GetMapping("/jumpToPage")
    public String jumpToPage(@RequestBody Book book, @RequestParam int pageNumber) {
        try {
            int totalPages = bookService.checkIfPageExists(book, pageNumber);
            if (pageNumber > totalPages || pageNumber < 1) {
                return "PAGE DOES NOT EXIST IN BOOK" + ", TOTAL PAGES PRESENT IN BOOK ARE " + totalPages;
            } else {
                return progressService.readPage(book, pageNumber);
            }
        } catch (Exception e) {
            return "Error starting read session: " + e.getMessage();
        }
    }

    @GetMapping("/resumeReading")
    public String resumeReadingFromLastStopped(@RequestBody Book book) {
        int currentPage = progressService.getCurrentPage(book.getUser().getId(), book.getId());
        try {
            return progressService.readPage(book, currentPage);
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }
}


