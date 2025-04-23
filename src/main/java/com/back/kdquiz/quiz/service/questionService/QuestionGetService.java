package com.back.kdquiz.quiz.service.questionService;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.entity.Option;
import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.quiz.dto.ChoiceGetDto;
import com.back.kdquiz.quiz.dto.OptionGetDto;
import com.back.kdquiz.quiz.dto.QuestionGetDto;
import com.back.kdquiz.quiz.service.choiceService.ChoiceGetService;
import com.back.kdquiz.quiz.service.optionService.OptionGetService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionGetService {

    private final QuestionRepository questionRepository;
    private final ChoiceGetService choiceGetService;
    private final OptionGetService optionGetService;

    @Transactional
    public ResponseDto<?> questionGet(Long id){
        try{
            Optional<Question> questionOptional = questionRepository.findById(id);
            if(questionOptional.isEmpty()){
                return ResponseDto.setFailed("Q000","question 없습니다.");
            }
            Question question = questionOptional.get();
            QuestionGetDto questionGetDto = new QuestionGetDto();
            questionGetDto.setId(question.getId());
            questionGetDto.setContent(question.getContent());
            questionGetDto.setCreateAt(question.getUpdatedAt());

            List<ChoiceGetDto> choiceGetDtoList = new ArrayList<>();
            for(Choice choice : question.getChoice()){
                ChoiceGetDto choiceGetDto = new ChoiceGetDto();
                choiceGetDto.setId(choice.getId());
                choiceGetDto.setContent(choice.getContent());
                choiceGetDto.setIsCorrect(choice.getIsCorrect());
                choiceGetDtoList.add(choiceGetDto);
            }

            questionGetDto.setChoiceList(choiceGetDtoList);
            Option option = question.getOption();
            OptionGetDto optionGetDto = new OptionGetDto();
            optionGetDto.setId(option.getId());
            optionGetDto.setUseAiFeedBack(option.getUseAiFeedback());
            optionGetDto.setAiQuestion(option.getAiQuestion());
            optionGetDto.setTime(option.getTime());
            optionGetDto.setCommentary(option.getCommentary());
            optionGetDto.setScore(option.getScore());

            questionGetDto.setOption(optionGetDto);
            return ResponseDto.setSuccess("Q200", "question 목록 조회 성공", questionGetDto);
        }catch (Exception e){
            return ResponseDto.setSuccess("Q001", "question 오류 발생");
        }
    }
}
