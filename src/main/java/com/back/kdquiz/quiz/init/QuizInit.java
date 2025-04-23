package com.back.kdquiz.quiz.init;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.entity.Option;
import com.back.kdquiz.domain.entity.Question;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class QuizInit {

    public Question questionInit(Question question){
        question.setContent("제목");
        question.setCreatedAt(LocalDateTime.now());
        return question;
    }

    public Choice choiceInit(Choice choice){
        choice.setContent("답");
        choice.setIsCorrect(false);
        return choice;
    }

    public Option optionInit(Option option){
        option.setTime(30);
        option.setUseAiFeedback(false);
        option.setAiQuestion("ai 피드백");
        option.setCommentary("코멘트 피드백");
        option.setScore(0);
        return option;
    }

}
