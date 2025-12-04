package com.back.kdquiz.domain.repository;

import com.back.kdquiz.domain.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findById(Long id);

//    @Modifying
//    @Transactional
//    @Query("Update Quiz q SET q.title=:title WHERE q.id=:quizId AND q.userId=:userId")
//    void updateQuiz(Long quizId, Long userId, String title);


//    @Query("SELECT DISTINCT q FROM Quiz q JOIN q.users u LEFT JOIN q.imgUrl LEFT JOIN q.questions qu LEFT JOIN qu.option WHERE q.id=:quizId")
    @EntityGraph(attributePaths = {"imgUrl", "questions", "questions.option", "questions.choices"})
    @Query("SELECT q FROM Quiz q WHERE q.id = :quizId")
    Optional<Quiz> findAllQuiz(@Param("quizId") Long quizId);

    Page<Quiz> findByTitleContainingIgnoreCase(String title, Pageable pageable);


    @Query(value = "SELECT * FROM quiz ORDER BY created_at DESC LIMIT 10", nativeQuery = true)
    List<Quiz> quizTodayList();


//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Quiz q WHERE q.id=:quizId")
//    int quizDelete(@Param("quizId") Long quizId);
}
