package com.back.kdquiz.quiz.service.questionService;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.entity.Option;
import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.ChoiceRepository;
import com.back.kdquiz.domain.repository.OptionRepository;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.init.QuizInit;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class QuestionCreateService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final OptionRepository optionRepository;
    private final QuestionGetService questionGetService;
    private final QuizInit quizInit;

    @Transactional
    public ResponseDto<?> questionCreate(Long quizId){
        try{
            Optional<Quiz> quizOption = quizRepository.findById(quizId);
            if(quizOption.isEmpty()){
                return ResponseDto.setFailed("Q000", "퀴즈가 없음");
            }

            Quiz quiz = quizOption.get();

            Question question = quizInit.questionInit(new Question());
            question.setQuiz(quiz);
            question.setTitle("");
            questionRepository.save(question);

            for(int i=0; i<2; i++){
                Choice choice = quizInit.choiceInit(new Choice());
                choice.setQuestion(question);
                choiceRepository.save(choice);
            }

            Option option = quizInit.optionInit(new Option());
            option.setQuestion(question);
            optionRepository.save(option);

            ResponseDto responseDto = questionGetService.questionGet(question.getId());
            log.info("퀘스천 "+responseDto.getCode()+" "+responseDto.getMessage());
            return responseDto.getCode().equals("Q200") ?
                    ResponseDto.setSuccess("Q200", "생성 성공", responseDto.getData()) :
                    ResponseDto.setFailed("Q001", "Question 찾을 수 없음");

        }catch (Exception e){
            return ResponseDto.setFailed("Q002", "생성 실패");
        }

    }

}
