package com.back.kdquiz.exception.choiceException;

import com.back.kdquiz.exception.CustomException;

public class ChoiceSaveFailedException extends CustomException {
    public ChoiceSaveFailedException() {
        super("C002", "Choice 저장 실패하였습니다. Choice 없습니다.");

    }
}
