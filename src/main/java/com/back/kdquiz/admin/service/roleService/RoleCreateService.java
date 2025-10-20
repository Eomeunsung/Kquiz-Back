package com.back.kdquiz.admin.service.roleService;

import com.back.kdquiz.admin.dto.roleDto.RoleCreateDto;
import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.repository.RoleRepository;
import com.back.kdquiz.exception.resourceException.ResourceDuplicationException;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleCreateService {

    private final RoleRepository roleRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> roleCreate(RoleCreateDto roleCreateDto){
        Role roleFind = roleRepository.findByRoleName(roleCreateDto.getRole());
        ResponseDto responseDto;
        if(roleFind!=null){
            throw new ResourceDuplicationException();
        }else{
            Role role = new Role();
            role.setRoleName(roleCreateDto.getRole());
            roleRepository.save(role);
            responseDto = ResponseDto.setSuccess("R200","Role 추가 되었습니다.", null);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }
}
