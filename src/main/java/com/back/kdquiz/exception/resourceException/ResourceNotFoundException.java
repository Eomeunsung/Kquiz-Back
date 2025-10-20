package com.back.kdquiz.exception.resourceException;

import com.back.kdquiz.exception.CustomException;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException() {
        super("R000", "존재하지 않는 리소스 입니다. 다시 시도해 주시기 바랍니다.");
    }
}
