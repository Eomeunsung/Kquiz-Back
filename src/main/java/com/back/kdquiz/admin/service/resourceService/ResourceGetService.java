package com.back.kdquiz.admin.service.resourceService;


import com.back.kdquiz.admin.dto.resourceDto.ResourceGetDto;
import com.back.kdquiz.domain.entity.Resources;
import com.back.kdquiz.domain.repository.ResourcesRepository;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceGetService {

    private final ResourcesRepository resourcesRepository;

    public ResponseEntity<ResponseDto<?>> resourceGet(){
        ResponseDto responseDto;
        List<Resources> resourcesList = resourcesRepository.findAll();
        if(resourcesList.isEmpty()){
            responseDto = ResponseDto.setSuccess("A000", "리소스 목록 가져오기", null);
        }else{
            List<ResourceGetDto> resourcesGetDtoList = new ArrayList<>();
            for(Resources resources : resourcesList){
                ResourceGetDto resourcesGetDto = new ResourceGetDto();
                resourcesGetDto.setId(resources.getId());
                resourcesGetDto.setResource(resources.getResourceName());
                resourcesGetDtoList.add(resourcesGetDto);
            }
            responseDto = ResponseDto.setSuccess("A000", "리소스 목록 가져오기", resourcesGetDtoList);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
