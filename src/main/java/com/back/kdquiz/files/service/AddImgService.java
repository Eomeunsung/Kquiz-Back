package com.back.kdquiz.files.service;

import com.back.kdquiz.domain.entity.ImgUrl;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.ImgUrlRepository;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddImgService {
    private final ImgUrlRepository imgUrlRepository;

    private final QuizRepository quizRepository;

    @Transactional
    public ResponseDto<?> addImg(List<String> imgList, Long quizId){
        try{
            Optional<Quiz> quizOptional = quizRepository.findById(quizId);
            if(quizOptional==null){
                return ResponseDto.setFailed("Q200", "퀴즈를 못 찾았습니다.");
            }
            Quiz quiz = quizOptional.get();

            for(String img : imgList){
                ImgUrl imgUrl = new ImgUrl();
                imgUrl.setQuiz(quiz);
                imgUrl.setImgUrl(img);
                imgUrlRepository.save(imgUrl);
            }
            return ResponseDto.setSuccess("I200", "이미지 저장 완료 했습니다.");
        }catch (Exception e){
            return ResponseDto.setFailed("I000", "알 수 없는 오류가 발생했습니다."+e.getMessage());
        }
    }
}
