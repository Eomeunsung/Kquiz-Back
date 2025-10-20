package com.back.kdquiz.role.controller;

import com.back.kdquiz.admin.service.roleService.RoleGetService;
import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.role.service.RoleUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleUserService roleUserService;
    private final RoleGetService roleGetService;

    @GetMapping("")
    public ResponseEntity<ResponseDto<?>> roleGet(){
        return roleGetService.roleGet();
    }


    @GetMapping("/user")
    public ResponseEntity<ResponseDto<?>> userRoleGet(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return roleUserService.userRoleGet(customUserDetails);
    }

}
