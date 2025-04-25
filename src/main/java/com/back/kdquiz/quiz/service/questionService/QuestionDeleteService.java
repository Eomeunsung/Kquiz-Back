package com.back.kdquiz.quiz.service.questionService;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionDeleteService {

    private final QuestionRepository questionRepository;

    @Transactional
    public ResponseDto<?> questionDelete(Long questionId){
        try{
            Optional<Question> questionOptional = questionRepository.findById(questionId);
            if(questionOptional.isEmpty()){
                return ResponseDto.setFailed("Q000", "Question 없음");
            }
            Question question = questionOptional.get();
            questionRepository.delete(question);
            return ResponseDto.setSuccess("Q200", "Question 삭제 완료");
        }catch (Exception e){
            return ResponseDto.setFailed("Q001", "Qeustion 삭제 오류 발생");
        }
    }
}
