package com.back.kdquiz.quiz.service.optionService.get;

import com.back.kdquiz.domain.entity.Option;
import com.back.kdquiz.quiz.dto.get.OptionGetDto;
import com.back.kdquiz.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface OptionGetService {

    ResponseEntity<ResponseDto<?>> optionGetResponse(Long optionId);

    OptionGetDto optionGetDto(Long optionId);

    OptionGetDto optionGetDto(Option option);
}
