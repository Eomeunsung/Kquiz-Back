package com.back.kdquiz.user.service;

import com.back.kdquiz.config.security.configs.PasswordEncoderConfig;
import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.RoleRepository;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.user.dto.SignUpDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public ResponseEntity<ResponseDto<?>> SignUp(SignUpDto signUpDto){
        ResponseDto responseDto;
        try{
            Users userEmail = usersRepository.findByEmail(signUpDto.getEmail());
            if(userEmail!=null){
                responseDto =  ResponseDto.setFailed("U000", "이미 존재하는 이메일 입니다.");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            Role role = roleRepository.findByRoleName("ROLE_USER");
            if(role==null){
                responseDto =  ResponseDto.setFailed("U001", "사용 할 수 있는 권한이 없습니다.");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            Users users = new Users();
            users.setEmail(signUpDto.getEmail());
            users.setNickName(signUpDto.getNickName());
            users.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            users.setUserRoles(roleSet);
            users.setCreateAt(LocalDate.now());
            usersRepository.save(users);
            responseDto = ResponseDto.setSuccess("U200", "회원가입 완료 했습니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto = ResponseDto.setFailed("U002", "알 수 없는 오류가 발생 했습니다. "+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }
}
