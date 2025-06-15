package com.back.kdquiz.quiz.service.quizSerivce;

import com.back.kdquiz.config.util.CustomFileUtil;
import com.back.kdquiz.domain.entity.ImgUrl;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.ImgUrlRepository;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizDeleteService {

    private final QuizRepository quizRepository;
    private final ImgUrlRepository imgUrlRepository;
    private final CustomFileUtil customFileUtil;

    @Transactional
    public ResponseEntity<ResponseDto<?>> quizDelete(Long quizId){
        ResponseDto responseDto;
        try{
            Optional<Quiz> quizOptional = quizRepository.findById(quizId);
            if(quizOptional==null){
               responseDto = ResponseDto.setFailed("Q000","퀴즈를 못찾았습니다.");
               return new ResponseEntity<>(responseDto, HttpStatus.OK);
            }
            List<ImgUrl> imgUrlList = imgUrlRepository.findByQuiz_Id(quizOptional.get().getId());
            if(!imgUrlList.isEmpty()){
                for(ImgUrl imgUrl : imgUrlList){
                    customFileUtil.deleteProfileImg(imgUrl.getImgUrl());
                }
            }
            quizRepository.deleteById(quizId);
            responseDto = ResponseDto.setSuccess("Q200", "퀴즈 삭제하였습니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto = ResponseDto.setFailed("Q001", "퀴즈 삭제 오류 "+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }
}
