package com.back.kdquiz.repository;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.quiz.dto.create.QuizCreateDto;
import com.back.kdquiz.quiz.dto.get.QuizAllGetDto;
import com.back.kdquiz.quiz.dto.update.QuizTitleUpdateDto;
import com.back.kdquiz.quiz.service.quizService.QuizListService;
import com.back.kdquiz.quiz.service.quizService.QuizTitleUpdateService;
import com.back.kdquiz.quiz.service.quizService.create.QuizCreateService;
import com.back.kdquiz.quiz.service.quizService.delete.QuizDeleteService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizCreateService quizCreateService;

    @Autowired
    private QuizListService quizListService;

    @Autowired
    private QuizTitleUpdateService quizTitleUpdateService;

    @Autowired
    private QuizDeleteService quizDeleteService;
    @Test
    public void testInsert(){
        for(int i=1; i<=50; i++){
            QuizCreateDto quizCreateDto = new QuizCreateDto();
            quizCreateDto.setTitle("Test"+i);
            quizCreateService.quizCreateDto(quizCreateDto);
        }
    }

    @Test
    public void testRead(){
        List<Quiz> quizList = quizRepository.findAll();
        List<QuizAllGetDto> quizAllGetDtoList = quizListService.buildQuizAllListTest(quizList);
        quizAllGetDtoList.forEach(arr->log.info(arr));
    }

    @Test
    public void testUpdate(){
        Long id = 1L;
        QuizTitleUpdateDto quizTitleUpdateDto = new QuizTitleUpdateDto();
        quizTitleUpdateDto.setId(1L);
        quizTitleUpdateDto.setTitle("updateTest");
        quizTitleUpdateService.quizTitleUpdate(quizTitleUpdateDto);
    }

    @Test
    public void testDelete() throws IOException {
        Long id = 4L;
        quizDeleteService.quizDeleteResponse(id);
    }

    @Test
    public void testDetailRead(){
        Long id =1L;
        Optional<Quiz> quizOptional = quizRepository.findAllQuiz(id);

        if (quizOptional.isEmpty()){
            log.info("퀴즈 가 없음");
        }else{
            Quiz quiz = quizOptional.get();
            log.info(quiz);
//            log.info(quiz.getQuestions().forEach(arr->log.info(arr)));
            quiz.getQuestions().forEach(arr->log.info(arr.getContent()));
        }
    }

}
