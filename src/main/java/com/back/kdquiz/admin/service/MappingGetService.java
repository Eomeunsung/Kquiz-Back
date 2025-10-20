package com.back.kdquiz.admin.service;

import com.back.kdquiz.admin.dto.resourceDto.ResourceGetDto;
import com.back.kdquiz.admin.dto.MappingGetDto;
import com.back.kdquiz.admin.dto.roleDto.RoleGetDto;
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
public class MappingGetService {

    private final ResourcesRepository resourcesRepository;


    @Transactional
    public ResponseEntity<ResponseDto<?>> mappingGet(){
        ResponseDto responseDto;
        List<Resources> repositoryList = resourcesRepository.findAll();
        if(repositoryList.isEmpty()){
            responseDto = ResponseDto.setSuccess("A000", "매핑 목록", null);
        }else{
            List<MappingGetDto> mappingGetDtoList = new ArrayList<>();
            for(Resources resources : repositoryList){
                MappingGetDto mappingGetDto = new MappingGetDto();
                ResourceGetDto resourceGetDto = new ResourceGetDto();
                resourceGetDto.setId(resources.getId());
                resourceGetDto.setResource(resources.getResourceName());

                Set<RoleGetDto> roleGetDtoSet = new HashSet<>();
                for(Role role : resources.getRoleSet()){
                    RoleGetDto roleGetDto = new RoleGetDto();
                    roleGetDto.setId(role.getId());
                    roleGetDto.setRole(role.getRoleName());
                    roleGetDtoSet.add(roleGetDto);
                }
                mappingGetDto.setResource(resourceGetDto);
                mappingGetDto.setRoles(roleGetDtoSet);
                mappingGetDtoList.add(mappingGetDto);
            }
            responseDto = ResponseDto.setSuccess("A000", "매핑 목록", mappingGetDtoList);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
