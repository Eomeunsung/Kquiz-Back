package com.back.kdquiz.admin.service;

import com.back.kdquiz.admin.dto.UserGetDto;
import com.back.kdquiz.admin.dto.UserListDto;
import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminUserListService {

    private final UsersRepository usersRepository;

    public AdminUserListService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public ResponseEntity<ResponseDto<?>> userList(){
        ResponseDto responseDto;
        try{
            List<Users> usersList = usersRepository.findAll();

            if(usersList.isEmpty()){
                responseDto = ResponseDto.setFailed("U000", "유저가 비었습니다.");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
            UserListDto userListDto = new UserListDto();
            List<UserGetDto> userGetDtos = new ArrayList<>();
            for(Users users : usersList){

                Set<String> roles = users.getUserRoles().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                UserGetDto userGetDto = new UserGetDto(users.getId(), users.getEmail(), users.getNickName(), users.getCreateAt(), roles);
                userGetDtos.add(userGetDto);
            }
            userListDto.setUserList(userGetDtos);
            responseDto = ResponseDto.setSuccess("U200", "유저 리스트", userListDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto = ResponseDto.setFailed("U001", "알 수 없는 오류 발생"+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
