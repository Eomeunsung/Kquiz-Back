package com.back.kdquiz.user.exception;

import com.back.kdquiz.exception.CustomException;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super("U001", "유저를 찾을 수 없습니다.");
    }
}
