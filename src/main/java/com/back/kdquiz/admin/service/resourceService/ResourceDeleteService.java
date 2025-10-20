package com.back.kdquiz.admin.service.resourceService;

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
public class ResourceDeleteService {

    private final ResourcesRepository resourcesRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> resourceDelete(Long resourceId){
        Resources resources = resourcesRepository.findById(resourceId).orElseThrow(()->new ResourceNotFoundException());

        resourcesRepository.delete(resources);
        ResponseDto responseDto = ResponseDto.setSuccess("R200", "리소스 삭제 완료 되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
