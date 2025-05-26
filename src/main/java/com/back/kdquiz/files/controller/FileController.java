package com.back.kdquiz.files.controller;

import com.back.kdquiz.config.util.CustomFileUtil;
import com.back.kdquiz.files.dto.img.ImgChangeDto;
import com.back.kdquiz.files.service.AddImgService;
import com.back.kdquiz.files.service.ChangeImgService;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final CustomFileUtil customFileUtil;
    private final ChangeImgService changeImgService;
    private final AddImgService addImgService;
    @Value("${spring.kdquiz.upload.path}")
    private String uploadPath;

    @PostMapping(value = "/upload/{quizId}", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> imgUpload(@RequestPart(value="files", required=false) List<MultipartFile> files, @PathVariable Long quizId){

        List<String> savedFilesDeatil = new ArrayList<>();
        if(files != null){
            try {
                savedFilesDeatil = customFileUtil.saveFiles(files);
                if(!savedFilesDeatil.isEmpty()){
//                    List<String> fullFileDeatils = savedFilesDeatil.stream()
//                            .map(fileName -> uploadPath+"/"+fileName)
//                            .collect(Collectors.toList());


                    ResponseDto responseDto = addImgService.addImg(savedFilesDeatil, quizId);
                    if(responseDto.getCode().equals("I200")){
                        return new ResponseEntity<>(responseDto, HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/img/change")
    public ResponseEntity<ResponseDto> imgChange(@RequestBody ImgChangeDto imgChangeDto) throws IOException {
        log.info("퀴즈 이미지 퀴즈 아이디 "+imgChangeDto.getId());

        ResponseDto responseDto = changeImgService.changeImg(imgChangeDto);

        if(responseDto.getCode().equals("I200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
