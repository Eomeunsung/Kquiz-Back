package com.back.kdquiz.exception.quizException;

import com.back.kdquiz.exception.CustomException;

public class QuizCreateFailedException extends CustomException {
    public QuizCreateFailedException() {
        super("Q000", "퀴즈 생성 실패");
    }
}
