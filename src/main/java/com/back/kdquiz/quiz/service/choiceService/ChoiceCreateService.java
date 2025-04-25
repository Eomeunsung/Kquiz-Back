package com.back.kdquiz.quiz.service.choiceService;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.ChoiceRepository;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.quiz.dto.create.ChoiceCreateDto;
import com.back.kdquiz.quiz.init.QuizInit;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChoiceCreateService {

    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final QuizInit quizInit;

    @Transactional
    public ResponseDto<?> choiceCreate(Long questionId){
        try{
            Optional<Question> questionOptional = questionRepository.findById(questionId);
            if(questionOptional.isEmpty()){
                return ResponseDto.setFailed("Q000", "Question 찾을 수 없습니다.");
            }
            Question question = questionOptional.get();
            Choice choice = quizInit.choiceInit(new Choice());
            choice.setQuestion(question);
            choiceRepository.save(choice);
            ChoiceCreateDto data = new ChoiceCreateDto();
            data.setId(choice.getId());
            data.setContent(choice.getContent());
            data.setIsCorrect(choice.getIsCorrect());
            return ResponseDto.setSuccess("Q200", "Choice 생성 성공", data);
        }catch (Exception e){
            return ResponseDto.setFailed("Q001", "Choice 오류 발생");
        }
    }

}
