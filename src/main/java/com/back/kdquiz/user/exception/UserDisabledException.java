package com.back.kdquiz.user.exception;

import com.back.kdquiz.exception.CustomException;

public class UserDisabledException extends CustomException {

    public UserDisabledException() {
        super("U000", "계정이 정지 되었습니다. 관리자에게 문의 하여주십시오.");
    }
}
