package com.example.kindle.service;

import com.example.kindle.model.Book;
import com.example.kindle.model.ReadingProgress;
import com.example.kindle.model.User;
import com.example.kindle.repository.BookRepository;
import com.example.kindle.repository.ReadingProgressRepository;
import com.example.kindle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReadingProgressService {

    @Autowired
    private ReadingProgressRepository readingProgressRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${book.base.path}")
    private String bookBasePath;

    private static final int LINES_PER_PAGE = 100;

    public ReadingProgress saveProgress(ReadingProgress progress) {
        return readingProgressRepository.save(progress);
    }

    public Optional<ReadingProgress> findByUserIdAndBookId(Long userId, Long bookId) {
        return readingProgressRepository.findByUserIdAndBookId(userId, bookId);
    }

    public String readPage(Book book, int pageNumber) throws IOException {
        Optional<ReadingProgress> progress = readingProgressRepository.findByUserIdAndBookId(book.getUser().getId(), book.getId());
        String filePath = Paths.get(bookBasePath, book.getTitle().replace(" ","") + ".txt").toString();
        if (!Files.exists(Paths.get(filePath))) {
            throw new IllegalArgumentException("Book not found");
        }
        long totalLines;
        try (var lines = Files.lines(Paths.get(filePath))) {
            totalLines = lines.count();
        }
        int startLine = (pageNumber - 1) * LINES_PER_PAGE;
        if (startLine >= totalLines) {
            throw new IllegalArgumentException("Page number exceeds the number of available pages");
        }
        if (pageNumber == 1 && progress.isEmpty()) {
            User user = new User();
            user.setId(book.getUser().getId());
            ReadingProgress readingProgress = new ReadingProgress();
            readingProgress.setUser(user);
            readingProgress.setBookId(book.getId());
            readingProgress.setCurrentPage(pageNumber);
            readingProgress.setPositionInFile(startLine);
            readingProgressRepository.save(readingProgress);
        } else if (pageNumber >= 1 && !progress.isEmpty()) {
            ReadingProgress readingProgress = progress.get();
            readingProgress.setCurrentPage(pageNumber);
            readingProgress.setPositionInFile(startLine);
            readingProgressRepository.save(readingProgress);
        }
        List<String> linesList = Files.lines(Paths.get(filePath)).collect(Collectors.toList());
        return generatePageData(linesList, pageNumber);
    }

    private String generatePageData(List<String> lines, int pageNumber) {
        StringBuilder paginatedContent = new StringBuilder();
        int pageCount = 1;
        for (int i = 0; i < lines.size(); i++) {
            if (i % LINES_PER_PAGE == 0) {
                if (pageCount == pageNumber) {
                    paginatedContent.append("Page ").append(pageCount).append("\n");
                }
                pageCount++;
            }
            if (pageCount - 1 == pageNumber) {
                paginatedContent.append(lines.get(i)).append("\n");
            }
        }
        return paginatedContent.toString().trim();
    }

    public int getCurrentPage(long userId, Long bookId) {
        return readingProgressRepository.findByUserIdAndBookId(userId, bookId)
                .map(ReadingProgress::getCurrentPage)
                .orElse(1);
    }
}