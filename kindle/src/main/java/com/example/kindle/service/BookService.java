package com.example.kindle.service;

import com.example.kindle.model.Book;
import com.example.kindle.repository.BookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static final Logger logger = LogManager.getLogger(BookService.class);
    @Autowired
    private BookRepository bookRepository;

    @Value("${book.base.path}")
    private String bookBasePath;

    private static final int LINES_PER_PAGE = 101;

    public Book addBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Book> getBooksByUserId(Long userId) {
        return bookRepository.findByUserId(userId);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public int checkIfPageExists(Book book, int pageNumber) throws Exception {
        String filePath = Paths.get(bookBasePath, book.getTitle().replace(" ","") + ".txt").toString();
        List<String> linesList = Files.lines(Paths.get(filePath)).collect(Collectors.toList());
        int totalLines = linesList.size();
        int totalPages = (int) Math.ceil((double) totalLines / LINES_PER_PAGE);
        return totalPages;
    }
}
