package com.back.kdquiz.quiz.service.quizSerivce;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
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
        try{
            Optional<Quiz> quizOptional = quizRepository.findById(quizTitleUpdateDto.getId());
            if(quizOptional.isEmpty()){
                responseDto = ResponseDto.setFailed("Q000", "퀴즈를 찾지 못 했습니다. 다시 시도해 주시기 바랍니다.");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
            Quiz quiz = quizOptional.get();
            quiz.setTitle(quizTitleUpdateDto.getTitle());
            quizRepository.save(quiz);

            responseDto = ResponseDto.setSuccess("Q200", "제목 수정 되었습니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto = ResponseDto.setFailed("Q001", "알 수 없는 오류가 발생했습니다."+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
