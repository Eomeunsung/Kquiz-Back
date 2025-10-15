package com.back.kdquiz.admin.service;

import com.back.kdquiz.admin.dto.RoleGetDto;
import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.repository.RoleRepository;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminRoleGetService {

    private final RoleRepository roleRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> roleGet(){
        ResponseDto responseDto;
        List<Role> roleList = roleRepository.findAll();
        if(roleList.isEmpty()){
            responseDto = ResponseDto.setSuccess("A000", "권한 목록 가져오기", null);
        }else{
            List<RoleGetDto> roleGetDtoList = new ArrayList<>();
            for(Role role : roleList) {
                RoleGetDto roleGetDto = new RoleGetDto();
                roleGetDto.setId(role.getId());
                roleGetDto.setRole(role.getRoleName());
                roleGetDtoList.add(roleGetDto);
            }
            responseDto = ResponseDto.setSuccess("A000", "권한 목록 가져오기", roleGetDtoList);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
