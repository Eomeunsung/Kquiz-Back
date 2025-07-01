package com.back.kdquiz.exception.gameException;

import com.back.kdquiz.exception.CustomException;

public class GameNotFoundException extends CustomException {

    public GameNotFoundException() {
        super("P000", "게임 방 번호를 찾을 수 없습니다. 다시 입력해주시기 바랍니다.");
    }
}
