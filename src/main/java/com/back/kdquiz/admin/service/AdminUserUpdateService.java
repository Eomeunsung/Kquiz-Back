package com.back.kdquiz.admin.service;

import com.back.kdquiz.admin.dto.UserUpdateDto;
import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.RoleRepository;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserUpdateService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> userUpdate(UserUpdateDto userUpdateDto){
        ResponseDto responseDto;
        try{
            Optional<Users> usersOptional = usersRepository.findById(userUpdateDto.getUserId());
            if(usersOptional.isEmpty()){
                responseDto = ResponseDto.setFailed("A000", "유저를 못 찾았습니다. 다시 시도해 주시기 바랍니다.");
                log.info("유저를 못 찾았습니다. 다시 시도해 주시기 바랍니다.");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
            Users users = usersOptional.get();
            if(usersRepository.findByEmail(userUpdateDto.getEmail())!=null && !users.getEmail().equals(userUpdateDto.getEmail())){
                responseDto = ResponseDto.setFailed("A001", "이미 존재하는 이메일 입니다.");
                log.info("이미 존재하는 이메일 입니다.");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
            if(!userUpdateDto.getRoles().isEmpty()){
                Set<Role> roleSet = new HashSet<>();
                for(String role : userUpdateDto.getRoles()){
                    Role roleName = roleRepository.findByRoleName(role);
                    if(roleName!=null){
                        roleSet.add(roleName);
                    }
                }
                users.setUserRoles(roleSet);
            }

            users.setEmail(userUpdateDto.getEmail());
            users.setNickName(userUpdateDto.getNickName());
            users.setEnabled(userUpdateDto.getEnabled());
            usersRepository.save(users);
            responseDto = ResponseDto.setSuccess("A200", "유저 업데이트를 성공 했습니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto = ResponseDto.setFailed("A001", "오류 발생"+e.getMessage());
            log.info("업데이트 오류: "+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }


}
