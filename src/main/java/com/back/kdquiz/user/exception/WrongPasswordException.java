package com.back.kdquiz.user.exception;

import com.back.kdquiz.exception.CustomException;

public class WrongPasswordException extends CustomException {
    public WrongPasswordException() {
        super("U002", "비밀번호가 잘 못 되었습니다.");
    }
}
