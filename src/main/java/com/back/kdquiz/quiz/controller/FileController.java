package com.back.kdquiz.quiz.controller;

import com.back.kdquiz.config.util.CustomFileUtil;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final CustomFileUtil customFileUtil;

    @Value("${spring.kdquiz.upload.path}")
    private String uploadPath;

    @PostMapping(value = "/upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> imgUpload(@RequestPart(value="files", required=false) List<MultipartFile> files){
        log.info("서버에서 받은 이미지 "+files);
        List<String> savedFilesDeatil = new ArrayList<>();
        if(files != null){
            try {
                savedFilesDeatil = customFileUtil.saveFiles(files);
                if(!savedFilesDeatil.isEmpty()){
                    List<String> fullFileDeatils = savedFilesDeatil.stream()
                            .map(fileName -> uploadPath+"/"+fileName)
                            .collect(Collectors.toList());
                    ResponseDto responseDto = ResponseDto.setSuccess("200","이미지 업로드 성공", fullFileDeatils);
                    return new ResponseEntity<>(responseDto, HttpStatus.OK);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
