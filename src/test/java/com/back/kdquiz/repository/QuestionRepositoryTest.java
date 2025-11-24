package com.back.kdquiz.repository;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.service.questionService.get.QuestionGetService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionGetService questionGetService;

    @Test
    public void testRead(){
        Optional<Question> questionOptional = questionRepository.findQuestion(1L);
        if(questionOptional.isEmpty()){
            log.info("Question x");
        }else{
            log.info(questionOptional.get());
        }
    }


    @Test
    public void testUpdate(){
        Optional<Question> questionOptional = questionRepository.findQuestion(1L);
        Question question = questionOptional.get();
        question.setTitle("Update Test");
        questionRepository.save(question);
    }
}
