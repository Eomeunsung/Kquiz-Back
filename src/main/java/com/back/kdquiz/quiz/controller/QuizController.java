package com.back.kdquiz.quiz.controller;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.page.dto.PageRequestDTO;
import com.back.kdquiz.quiz.dto.create.QuizCreateDto;
import com.back.kdquiz.quiz.dto.update.QuizTitleUpdateDto;
import com.back.kdquiz.quiz.dto.update.QuizUpdateDto;
import com.back.kdquiz.quiz.service.quizService.QuizListService;
import com.back.kdquiz.quiz.service.quizService.QuizTitleUpdateService;
import com.back.kdquiz.quiz.service.quizService.create.QuizCreateService;
import com.back.kdquiz.quiz.service.quizService.delete.QuizDeleteService;
import com.back.kdquiz.quiz.service.quizService.get.QuizGetService;
import com.back.kdquiz.quiz.service.quizService.update.QuizUpdateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/quiz")
@Log4j2
public class QuizController {

    private final QuizCreateService quizCreateService;
    private final QuizGetService quizGetService;
    private final QuizListService quizListService;
    private final QuizUpdateService quizUpdateService;
    private final QuizDeleteService quizDeleteService;
    private final QuizTitleUpdateService quizTitleUpdateService;

    @PostMapping("/create")
    public ResponseEntity quizCreate(@RequestBody QuizCreateDto quizCreateDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        return quizCreateService.quizCreateResponse(quizCreateDto, userDetails);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<?>> quizGetList(PageRequestDTO pageRequestDTO){
        log.info("요청 페이지 "+pageRequestDTO.getPage());
        return quizListService.quizAllList(pageRequestDTO);
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<ResponseDto<?>> quizGet(@PathVariable Long quizId){
        return quizGetService.quizGetResponse(quizId);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<?>> quizUpdate(@RequestBody QuizUpdateDto quizUpdateDto){
        return quizUpdateService.quizUpdateResponse(quizUpdateDto);
    }

    @PutMapping("/update/title")
    public ResponseEntity<ResponseDto<?>> quizUpdateTitle(@RequestBody QuizTitleUpdateDto quizTitleUpdateDto){
        return quizTitleUpdateService.quizTitleUpdate(quizTitleUpdateDto);
    }

    @DeleteMapping("/delete/{quizId}")
    public ResponseEntity<ResponseDto<?>> quizDelete(@PathVariable Long quizId) throws IOException {
        return quizDeleteService.quizDeleteResponse(quizId);
    }


}
