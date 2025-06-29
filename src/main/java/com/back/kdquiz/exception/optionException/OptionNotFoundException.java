package com.back.kdquiz.exception.optionException;

import com.back.kdquiz.exception.CustomException;

public class OptionNotFoundException extends CustomException {
    public OptionNotFoundException() {
        super("O001", "옵션을 찾지 못 했습니다.");
    }
}
