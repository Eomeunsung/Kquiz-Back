package com.back.kdquiz.exception.resourceException;

import com.back.kdquiz.exception.CustomException;

public class ResourceDuplicationException extends CustomException {
    public ResourceDuplicationException() {
        super("R000", "이미 존재 하는 리소스 입니다.");
    }
}
