package com.back.kdquiz.exception.roleException;

import com.back.kdquiz.exception.CustomException;

public class RoleDuplicationException extends CustomException {
    public RoleDuplicationException() {
        super("R000", "이미 존재하는 Role 입니다.");
    }
}
