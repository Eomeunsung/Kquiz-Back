package com.back.kdquiz.admin.service;

import com.back.kdquiz.admin.dto.MappingUpdateDto;
import com.back.kdquiz.domain.entity.Resources;
import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.repository.ResourcesRepository;
import com.back.kdquiz.domain.repository.RoleRepository;
import com.back.kdquiz.exception.resourceException.ResourceNotFoundException;
import com.back.kdquiz.exception.roleException.RoleNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MappingUpdateService {

    private final ResourcesRepository resourcesRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> mappingUpdate(MappingUpdateDto mappingUpdateDto){
        Optional<Resources> resourceFind = resourcesRepository.findById(mappingUpdateDto.getResourceId());
        if(resourceFind.isEmpty()){
            throw new ResourceNotFoundException();
        }


        for(Long id : mappingUpdateDto.getRoleId()){
            roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException());
        }

        Set<Role> roleSet = new HashSet<>();
        for(Long id : mappingUpdateDto.getRoleId()){
            Optional<Role> roleFind = roleRepository.findById(id);
            Role role = roleFind.get();
            roleSet.add(role);
        }

        Resources resources = resourceFind.get();
        resources.setRoleSet(roleSet);

        ResponseDto responseDto = ResponseDto.setSuccess("M200", "매핑 성공", null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
}
