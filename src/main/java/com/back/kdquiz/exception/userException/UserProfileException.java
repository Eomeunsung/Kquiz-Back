package com.back.kdquiz.exception.userException;

import com.back.kdquiz.exception.CustomException;

public class UserProfileException extends CustomException {
    public UserProfileException() {
        super("U004", "프로필을 찾을 수 없습니다.");
    }
}
