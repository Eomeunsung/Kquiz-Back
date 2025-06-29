package com.back.kdquiz.exception.questionException;

import com.back.kdquiz.exception.CustomException;

public class QuestionNotFoundException extends CustomException {
    public QuestionNotFoundException() {
        super("Q001", "question이 없습니다.");
    }
}
