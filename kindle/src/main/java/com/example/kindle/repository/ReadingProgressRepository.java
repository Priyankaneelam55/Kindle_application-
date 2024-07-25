package com.example.kindle.repository;

import com.example.kindle.model.ReadingProgress;
import com.example.kindle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, Long> {
    Optional<ReadingProgress> findByUserIdAndBookId(Long userId, Long bookId);

}


