package com.back.kdquiz.exception.userException;

import com.back.kdquiz.exception.CustomException;

public class EmailAlreadyExistsException extends CustomException {
    public EmailAlreadyExistsException() {
        super("U000", "이미 존재하는 이메일 입니다.");
    }
}
