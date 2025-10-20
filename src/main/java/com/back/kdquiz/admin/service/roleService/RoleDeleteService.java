package com.back.kdquiz.admin.service.roleService;

import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.repository.RoleRepository;
import com.back.kdquiz.exception.roleException.RoleNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleDeleteService {

    private final RoleRepository roleRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> roleDelete(Long roleId){
        Role role = roleRepository.findById(roleId).orElseThrow(()->new RoleNotFoundException());
        roleRepository.delete(role);
        ResponseDto responseDto = ResponseDto.setSuccess("R200", "권한이 삭제되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
