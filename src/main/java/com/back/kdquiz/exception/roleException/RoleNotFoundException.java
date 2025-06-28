package com.back.kdquiz.exception.roleException;

import com.back.kdquiz.exception.CustomException;

public class RoleNotFoundException extends CustomException {
    public RoleNotFoundException() {
        super("R000", "사용할 수 있는 권한이 없습니다.");
    }

}
