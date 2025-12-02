package com.back.kdquiz.quiz.service.quizService;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.page.dto.PageRequestDTO;
import com.back.kdquiz.page.dto.PageResponseDTO;
import com.back.kdquiz.quiz.dto.get.QuizAllGetDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class QuizListService {

    private final QuizRepository quizRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> quizAllList(PageRequestDTO pageRequestDTO){
        ResponseDto responseDto;
        Page<Quiz> quizPage;
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("updatedAt").descending());
        if(pageRequestDTO.getSearch().equals("")){
            quizPage = quizRepository.findAll(pageable);
        }else{
            quizPage = quizRepository.findByTitleContainingIgnoreCase(pageRequestDTO.getSearch(), pageable);
        }

        log.info("퀴즈 페이지 "+quizPage);

        List<QuizAllGetDto> quizAllGetDtoList = buildQuizAllListResponse(quizPage.getContent());
        PageResponseDTO<QuizAllGetDto> responseDTO =
                PageResponseDTO.<QuizAllGetDto>withAll()
                        .dtoList(quizAllGetDtoList)
                        .pageRequestDTO(pageRequestDTO)
                        .total(quizPage.getTotalElements()).build();

        responseDto =  ResponseDto.setSuccess("Q200", "퀴즈 목록 조회 성공", responseDTO);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @Transactional
    private List<QuizAllGetDto> buildQuizAllListResponse(List<Quiz> quizList){
        List<QuizAllGetDto> quizAllGetDtoList = new ArrayList<>();
        for(Quiz quiz : quizList){
            QuizAllGetDto qad = new QuizAllGetDto();
            qad.setId(quiz.getId());
            qad.setTitle(quiz.getTitle());
            qad.setNickName(null);
            qad.setUpdateAt(quiz.getUpdatedAt());
            quizAllGetDtoList.add(qad);
        }
        return quizAllGetDtoList;
    }

    @Transactional
    public List<QuizAllGetDto> buildQuizAllListTest(List<Quiz> quizList){
        List<QuizAllGetDto> quizAllGetDtoList = new ArrayList<>();
        for(Quiz quiz : quizList){
            QuizAllGetDto qad = new QuizAllGetDto();
            qad.setId(quiz.getId());
            qad.setTitle(quiz.getTitle());
            qad.setNickName(null);
            qad.setUpdateAt(quiz.getUpdatedAt());
            quizAllGetDtoList.add(qad);
        }
        return quizAllGetDtoList;
    }
}
