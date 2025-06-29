package com.back.kdquiz.exception.quizException;

import com.back.kdquiz.exception.CustomException;

public class QuizNotFoundException extends CustomException {

    public QuizNotFoundException() {
        super("Q001", "퀴즈를 찾을 수 없습니다.");
    }
}
