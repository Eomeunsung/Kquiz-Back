package com.back.kdquiz.role.service;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.role.dto.RoleGetDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleUserService {

    public ResponseEntity<ResponseDto<?>> userRoleGet(CustomUserDetails customUserDetails){
        ResponseDto responseDto;
        try{
            RoleGetDto roleGetDto = new RoleGetDto();
            Set<String> roleSet = customUserDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            if(roleSet.isEmpty()){
                responseDto = ResponseDto.setFailed("R000", "권한이 없습니다.");
                return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
            }
            roleGetDto.setRoles(roleSet);
            responseDto = ResponseDto.setSuccess("R200", "권한을 가져 왔습니다.", roleGetDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);

        }catch (Exception e){
            responseDto = ResponseDto.setFailed("R001", "오류가 발생했습니다."+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
