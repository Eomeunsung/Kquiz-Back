package com.back.kdquiz.admin.service.roleService;

import com.back.kdquiz.admin.dto.roleDto.RoleGetDto;
import com.back.kdquiz.admin.dto.roleDto.RoleUpdateDto;
import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.repository.RoleRepository;
import com.back.kdquiz.exception.roleException.RoleNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleUpdateService {

    private final RoleRepository roleRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> roleUpdate(RoleUpdateDto roleUpdateDto){
        Role role = roleRepository.findById(roleUpdateDto.getRoleId()).orElseThrow(()->new RoleNotFoundException());
        role.setRoleName(roleUpdateDto.getRoleName());
        roleRepository.save(role);
        RoleGetDto roleGetDto = new RoleGetDto();
        roleGetDto.setRole(role.getRoleName());
        roleGetDto.setId(role.getId());
        ResponseDto responseDto = ResponseDto.setSuccess("R200", "Role 수정 완료", roleGetDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
