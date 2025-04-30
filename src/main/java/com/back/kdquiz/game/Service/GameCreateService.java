package com.back.kdquiz.game.Service;

import com.back.kdquiz.response.ResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
public class GameCreateService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom random = new SecureRandom();

    @Transactional
    public ResponseDto<String> gameCreate(){
        try{
            StringBuilder sb = new StringBuilder(CODE_LENGTH);
            for(int i=0; i<CODE_LENGTH; i++){
                int index = random.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(index));
            }
            return ResponseDto.setSuccess("G200", "게임 생성 성공", sb.toString());
        }catch (Exception e){
            return ResponseDto.setFailed("G000", "게임 생성 오류"+e.getMessage());
        }
    }
}
