package com.back.kdquiz.exception.choiceException;

import com.back.kdquiz.exception.CustomException;

public class ChoiceNotFoundException extends CustomException {
    public ChoiceNotFoundException() {
        super("C001", "choice가 없습니다.");
    }
}
