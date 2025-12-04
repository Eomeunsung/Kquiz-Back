package com.back.kdquiz.quiz.service.quizService.delete;

import com.back.kdquiz.config.util.CustomFileUtil;
import com.back.kdquiz.domain.entity.ImgUrl;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.ImgUrlRepository;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.exception.quizException.QuizNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class QuizDeleteServiceImpl implements QuizDeleteService{

    private final QuizRepository quizRepository;
    private final ImgUrlRepository imgUrlRepository;
    private final CustomFileUtil customFileUtil;

    @Transactional
    @Override
    public ResponseDto quizDelete(Long quizId) throws IOException {
        Optional<Quiz> quizOptional = quizRepository.findAllQuiz(quizId);
        if(quizOptional.isEmpty()){
            throw new QuizNotFoundException();
        }
        Set<ImgUrl> imgUrlList = quizOptional.get().getImgUrl();
        if(!imgUrlList.isEmpty()){
            for(ImgUrl imgUrl : imgUrlList){
                customFileUtil.deleteProfileImg(imgUrl.getImgUrl());
            }
        }
        quizRepository.delete(quizOptional.get());
        return ResponseDto.setSuccess("Q200", "퀴즈 삭제하였습니다.");
    }
}
