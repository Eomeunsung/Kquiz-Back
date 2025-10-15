package com.back.kdquiz.admin.service;

import com.back.kdquiz.admin.dto.ResourceGetDto;
import com.back.kdquiz.admin.dto.ResourceRoleMappingGetDto;
import com.back.kdquiz.admin.dto.RoleGetDto;
import com.back.kdquiz.domain.entity.Resources;
import com.back.kdquiz.domain.entity.Role;
import com.back.kdquiz.domain.repository.ResourcesRepository;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminMappingGetService {

    private final ResourcesRepository resourcesRepository;


    @Transactional
    public ResponseEntity<ResponseDto<?>> mappingGet(){
        ResponseDto responseDto;
        List<Resources> repositoryList = resourcesRepository.findAll();
        if(repositoryList.isEmpty()){
            responseDto = ResponseDto.setSuccess("A000", "매핑 목록", null);
        }else{
            List<ResourceRoleMappingGetDto> resourceRoleMappingGetDtoList = new ArrayList<>();
            for(Resources resources : repositoryList){
                ResourceRoleMappingGetDto resourceRoleMappingGetDto = new ResourceRoleMappingGetDto();
                ResourceGetDto resourceGetDto = new ResourceGetDto();
                resourceGetDto.setResource(resourceGetDto.getResource());

                Set<RoleGetDto> roleGetDtoSet = new HashSet<>();
                for(Role role : resources.getRoleSet()){
                    RoleGetDto roleGetDto = new RoleGetDto();
                    roleGetDto.setId(role.getId());
                    roleGetDto.setRole(role.getRoleName());
                    roleGetDtoSet.add(roleGetDto);
                }


                resourceRoleMappingGetDto.setResource(resourceGetDto);
                resourceRoleMappingGetDto.setRoles(roleGetDtoSet);
                resourceRoleMappingGetDtoList.add(resourceRoleMappingGetDto);
            }
            responseDto = ResponseDto.setSuccess("A000", "매핑 목록", resourceRoleMappingGetDtoList);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
