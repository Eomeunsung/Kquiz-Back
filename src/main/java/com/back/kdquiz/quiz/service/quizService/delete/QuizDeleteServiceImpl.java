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
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizDeleteServiceImpl implements QuizDeleteService{

    private final QuizRepository quizRepository;
    private final ImgUrlRepository imgUrlRepository;
    private final CustomFileUtil customFileUtil;

    @Transactional
    @Override
    public ResponseEntity quizDeleteResponse(Long quizId) throws IOException {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(quizOptional.isEmpty()){
            throw new QuizNotFoundException();
        }
        List<ImgUrl> imgUrlList = imgUrlRepository.findByQuiz_Id(quizOptional.get().getId());
        if(!imgUrlList.isEmpty()){
            for(ImgUrl imgUrl : imgUrlList){
                customFileUtil.deleteProfileImg(imgUrl.getImgUrl());
            }
        }
        quizRepository.delete(quizOptional.get());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "퀴즈 삭제하였습니다."));

    }
}
