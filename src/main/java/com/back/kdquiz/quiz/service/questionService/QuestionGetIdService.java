package com.back.kdquiz.quiz.service.questionService;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.quiz.dto.get.QuestionGetIdDto;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//일단 안쓰는거
@Service
@AllArgsConstructor
public class QuestionGetIdService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public ResponseDto<?> questionGetId(Long quizId){
        try{
            List<Question> questionList = questionRepository.findByQuiz_Id(quizId);
            Optional<Quiz> quizOptional = quizRepository.findById(quizId);
            if(questionList.isEmpty() && !quizOptional.isPresent()){
                return ResponseDto.setFailed("Q000", "없는 퀴즈거나 퀘스천이 없습니다.");
            }
            Quiz quiz = quizOptional.get();
            QuestionGetIdDto questionGetIdDto = new QuestionGetIdDto();
            questionGetIdDto.setQuizId(quiz.getId());
            questionGetIdDto.setTitle(quiz.getTitle());
            List<Long> questionId = new ArrayList<>();
            for(Question question : questionList){
                questionId.add(question.getId());
            }
            questionGetIdDto.setQuestionId(questionId);
            return ResponseDto.setSuccess("Q200", "Question", questionGetIdDto);

        }catch (Exception e){
            return ResponseDto.setFailed("Q001", "오류:"+e.getMessage());
        }
    }
}
