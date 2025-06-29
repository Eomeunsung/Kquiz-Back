package com.back.kdquiz.exception.choiceException;

import com.back.kdquiz.exception.CustomException;

public class ChoiceCreateFailedException extends CustomException {
    public ChoiceCreateFailedException() {
        super("C000", "choice 생성에 실패 했습니다.");
    }
}
