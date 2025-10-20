package com.back.kdquiz.admin.service.resourceService;

import com.back.kdquiz.admin.dto.resourceDto.ResourceCreateDto;
import com.back.kdquiz.domain.entity.Resources;
import com.back.kdquiz.domain.repository.ResourcesRepository;
import com.back.kdquiz.exception.resourceException.ResourceDuplicationException;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceCreateService {

    private final ResourcesRepository resourcesRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> resourceCreate(ResourceCreateDto resourceCreateDto){
        ResponseDto responseDto;
        Optional<Resources> resourcesOptional = resourcesRepository.findByResourceName(resourceCreateDto.getResource());
        if(!resourcesOptional.isEmpty()){
            throw new ResourceDuplicationException();
        }else{
            Resources resources = new Resources();
            resources.setResourceName(resourceCreateDto.getResource());
            resourcesRepository.save(resources);
            responseDto = ResponseDto.setSuccess("R200", "리소스 추가 성공", null);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

    }

}
