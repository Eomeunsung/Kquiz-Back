package com.back.kdquiz.admin.service.resourceService;

import com.back.kdquiz.admin.dto.resourceDto.ResourceGetDto;
import com.back.kdquiz.admin.dto.resourceDto.ResourceUpdateDto;
import com.back.kdquiz.domain.entity.Resources;
import com.back.kdquiz.domain.repository.ResourcesRepository;
import com.back.kdquiz.exception.resourceException.ResourceNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResourceUpdateService {

    private final ResourcesRepository resourcesRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> resourceUpdate(ResourceUpdateDto resourceUpdateDto){
        Resources resources = resourcesRepository.findById(resourceUpdateDto.getResourceId()).orElseThrow(()->new ResourceNotFoundException());
        resources.setResourceName(resourceUpdateDto.getName());
        resourcesRepository.save(resources);
        ResourceGetDto resourceGetDto = new ResourceGetDto();
        resourceGetDto.setResource(resources.getResourceName());
        resourceGetDto.setId(resources.getId());
        ResponseDto responseDto = ResponseDto.setSuccess("R200", "업데이트 성공", resourceGetDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
