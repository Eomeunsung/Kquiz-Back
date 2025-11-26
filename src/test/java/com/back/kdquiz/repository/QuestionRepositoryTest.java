package com.back.kdquiz.repository;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.dto.update.ChoiceUpdateDto;
import com.back.kdquiz.quiz.dto.update.QuestionUpdateDto;
import com.back.kdquiz.quiz.service.questionService.get.QuestionGetService;
import com.back.kdquiz.quiz.service.questionService.update.QuestionUpdateService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionGetService questionGetService;

    @Autowired
    private QuestionUpdateService questionUpdateService;

    @Test
    public void testRead(){
        Optional<Question> questionOptional = questionRepository.findQuestion(1L);
        if(questionOptional.isEmpty()){
            log.info("Question x");
        }else{
            log.info(questionOptional.get());
        }
    }


//    @Test
//    public void testUpdate(){
////        Optional<Question> questionOptional = questionRepository.findQuestion(1L);
////        Question question = questionOptional.get();
////        question.setTitle("Update Test");
////        questionRepository.save(question);
//        QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto();
//        questionUpdateDto.setTitle("Update Test....");
//        questionUpdateDto.setId(1L);
//        questionUpdateDto.setChoices();
//        List<ChoiceUpdateDto> choiceUpdateDtoList = new ArrayList<>();
//
//
//
//        questionUpdateService.questionUpdateDto(questionUpdateDto);
//    }
}
