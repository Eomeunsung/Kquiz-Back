package com.back.kdquiz.admin.service;

import com.back.kdquiz.admin.dto.UserGetDto;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserGetService {

    private final UsersRepository usersRepository;
    @Transactional
    public ResponseEntity<ResponseDto<?>> userGet(Long userId){
        ResponseDto responseDto;
        try{
           Optional<Users> usersOptional = usersRepository.findById(userId);
           if(usersOptional.isEmpty()){
               responseDto = ResponseDto.setFailed("A000", "회원 정보를 못 찾았습니다.");
               return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
           }
            Users users = usersOptional.get();
            Set<String> roles = users.getUserRoles().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            UserGetDto userGet = new UserGetDto(users.getId(), users.getEmail(), users.getNickName(),users.getEnabled(), users.getCreateAt(), roles);
            responseDto = ResponseDto.setSuccess("A200", "회원 정보를 찾았습니다.", userGet);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto = ResponseDto.setFailed("A001", "알 수 없는 오류 발생했습니다."+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
