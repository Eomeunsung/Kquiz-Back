package com.back.kdquiz.admin.adminAccount;

import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.RoleRepository;
import com.back.kdquiz.domain.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//CommandLineRunner는 Spring Boot 애플리케이션이 실행된 직후, 한 번 실행되는 로직을 작성할 수 있게 해주는 인터페이스입니다.
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminAccountInitializer implements CommandLineRunner {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(usersRepository.findByEmail("admin")==null){
            Role role_admin = roleRepository.findByRoleName("ROLE_ADMIN");
            if(role_admin==null){
                role_admin = new Role();
                role_admin.setRoleName("ROLE_ADMIN");
                roleRepository.save(role_admin);
                log.info("아직 DB에 권한들이 등록되지 않았습니다.");
            }
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role_admin);

            Users users = new Users();
            users.setEmail("admin");
            users.setNickName("admin");
            users.setPassword(passwordEncoder.encode("1234"));
            users.setEnabled(true);
            users.setUserRoles(roleSet);
            users.setCreateAt(LocalDate.now());
            usersRepository.save(users);
            log.info("관리자 계정 생성");

            Role role_user = new Role();
            role_user.setRoleName("ROLE_USER");
            roleRepository.save(role_user);

        }else{
            log.info("관리자 계정 존재");
        }

    }
}
