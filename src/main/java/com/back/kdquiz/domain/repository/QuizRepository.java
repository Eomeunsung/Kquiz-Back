package com.back.kdquiz.domain.repository;

import com.back.kdquiz.domain.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
