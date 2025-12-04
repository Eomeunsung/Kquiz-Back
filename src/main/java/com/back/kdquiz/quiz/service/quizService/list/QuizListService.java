package com.back.kdquiz.quiz.service.quizService.list;

import com.back.kdquiz.page.dto.PageRequestDTO;
import com.back.kdquiz.response.ResponseDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QuizListService {

    ResponseDto quizTodayList();

    ResponseDto quizAllList(PageRequestDTO pageRequestDTO);


}
