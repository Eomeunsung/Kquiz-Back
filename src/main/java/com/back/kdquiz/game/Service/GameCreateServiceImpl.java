package com.back.kdquiz.game.Service;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.exception.quizException.QuizNotFoundException;
import com.back.kdquiz.game.Repository.GameRepositoryRedis;
import com.back.kdquiz.game.dto.GameCreateDto;
import com.back.kdquiz.game.dto.GameQuizInfoDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameCreateServiceImpl implements GameCreateService {

    private final QuizRepository quizRepository;
    private final GameRepositoryRedis gameRepositoryRedis;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom random = new SecureRandom();

    @Transactional
    @Override
    public ResponseEntity gameCreateResponse(Long quizId) {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for(int i=0; i<CODE_LENGTH; i++){
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        GameCreateDto gameCreateDto = new GameCreateDto();
        GameQuizInfoDto quizInfoDto = gameQuizInfo(quizId, sb.toString());
        gameRepositoryRedis.gameCreate(sb.toString(), String.valueOf(quizId));
        Long userId = gameRepositoryRedis.addUser(String.valueOf(quizId), "HOST");

        gameCreateDto.setGameId(sb.toString());
        gameCreateDto.setQuizTitle(quizInfoDto.getQuizTitle());
        gameCreateDto.setQuestionSize(quizInfoDto.getQuestionSize());
        gameCreateDto.setUserId(userId);


        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("G200", "게임 생성 성공", gameCreateDto));
    }

    @Override
    public GameQuizInfoDto gameQuizInfo(Long quizId, String roomId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if(quiz.isEmpty()){
            throw new QuizNotFoundException();
        }
        Long questionSize = questionIndexRedisSave(quiz.get().getQuestions(), roomId);
        return GameQuizInfoDto
                .builder()
                .quizTitle(quiz.get().getTitle())
                .questionSize(questionSize)
                .build();
    }

    @Transactional
    public Long questionIndexRedisSave(List<Question> questionList, String roomId){

        List<Long> questionIndexList = new ArrayList<>();

        for(Question question : questionList){
            questionIndexList.add(question.getId());
        }

       return gameRepositoryRedis.saveQuestionIndex(roomId, questionIndexList);
    }


}
