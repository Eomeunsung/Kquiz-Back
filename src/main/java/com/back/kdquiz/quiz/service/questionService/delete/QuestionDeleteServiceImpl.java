package com.back.kdquiz.quiz.service.questionService.delete;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.exception.questionException.QuestionNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionDeleteServiceImpl implements QuestionDeleteService{

    private final QuestionRepository questionRepository;

    @Transactional
    @Override
    public ResponseEntity questionDeleteResponse(Long questionId) {

        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isEmpty()){
            throw new QuestionNotFoundException();
        }
        Question question = questionOptional.get();
        questionRepository.delete(question);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "Question 삭제 완료"));
    }
}
