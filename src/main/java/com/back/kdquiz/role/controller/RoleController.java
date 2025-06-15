package com.back.kdquiz.role.controller;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.role.service.RoleService;
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

    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<ResponseDto<?>> roleGet(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return roleService.roleGet(customUserDetails);
    }
}
