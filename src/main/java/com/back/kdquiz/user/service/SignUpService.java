package com.back.kdquiz.user.service;

import com.back.kdquiz.config.security.configs.PasswordEncoderConfig;
import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.RoleRepository;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.exception.roleException.RoleNotFoundException;
import com.back.kdquiz.exception.userException.EmailAlreadyExistsException;
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

        Users userEmail = usersRepository.findByEmail(signUpDto.getEmail());
        if(userEmail!=null){
            throw new EmailAlreadyExistsException();
        }

        Role role = roleRepository.findByRoleName("ROLE_USER");
        if(role==null){
            throw new RoleNotFoundException();
        }
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        Users users = Users.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .nickName( signUpDto.getNickName())
                .enabled(true)
                .createAt(LocalDate.now())
                .userRoles(roleSet)
                .build();

        usersRepository.save(users);
        return new ResponseEntity<>(
                ResponseDto.setSuccess("U200", "회원가입 완료 했습니다."), HttpStatus.OK);

    }
}
