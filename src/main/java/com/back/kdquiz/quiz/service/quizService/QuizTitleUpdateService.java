package com.back.kdquiz.quiz.service.quizService;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.exception.quizException.QuizNotFoundException;
import com.back.kdquiz.quiz.dto.update.QuizTitleUpdateDto;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizTitleUpdateService {

    private final QuizRepository quizRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> quizTitleUpdate(QuizTitleUpdateDto quizTitleUpdateDto){
        ResponseDto responseDto;
        Optional<Quiz> quizOptional = quizRepository.findById(quizTitleUpdateDto.getId());
        if(quizOptional.isEmpty()){
            throw new QuizNotFoundException();
        }
        Quiz quiz = quizOptional.get();
        quiz.setTitle(quizTitleUpdateDto.getTitle());
        quizRepository.save(quiz);

        responseDto = ResponseDto.setSuccess("Q200", "제목 수정 되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
}
