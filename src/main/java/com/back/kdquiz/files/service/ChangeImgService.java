package com.back.kdquiz.files.service;

import com.back.kdquiz.domain.entity.ImgUrl;
import com.back.kdquiz.domain.repository.ImgUrlRepository;
import com.back.kdquiz.files.dto.img.ImgChangeDto;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChangeImgService {

    @Value("${spring.kdquiz.upload.path}")
    private String uploadPath;

    private final ImgUrlRepository imgUrlRepository;

    public ChangeImgService(ImgUrlRepository imgUrlRepository) {
        this.imgUrlRepository = imgUrlRepository;
    }

    @Transactional
    public ResponseDto changeImg(ImgChangeDto imgChangeDto) throws IOException {
        for(String img : imgChangeDto.getImg()){
            System.out.println("들어온 이미지 DTo Img: " + img);
        }
       try{
           List<ImgUrl> imgUrlList = imgUrlRepository.findByQuiz_Id(imgChangeDto.getId());
           if(imgUrlList.isEmpty() || imgUrlList==null){
               return ResponseDto.setFailed("I001", "이미지 없음");
           }else{
               List<ImgUrl> deleteList = imgUrlList.stream()
                       .filter(imgUrl -> !imgChangeDto.getImg().contains(imgUrl.getImgUrl())).collect(Collectors.toList());

               for(ImgUrl imgUrl : deleteList){
                   deleteImg(imgUrl.getImgUrl());
                   imgUrlRepository.delete(imgUrl);
               }
           }
           return ResponseDto.setSuccess("I200", "이미지 삭제 성공");
       }catch (Exception e){
           log.info("이미지 에러 "+e.getMessage());
           return ResponseDto.setFailed("I000", "이미지 삭제 실패"+e.getMessage());
       }
    }

    public void deleteImg(String fileName) throws IOException {
        if (fileName == null) {
            log.info("삭제할 파일이 없음");
            return;
        }
        Path filePath = Paths.get(uploadPath, fileName);
        try {
            Files.deleteIfExists(filePath);
            log.info("파일 삭제 완료: " + fileName);
        } catch (IOException e) {
            log.error("파일 삭제 중 오류 발생: " + fileName, e);
            throw new IOException("파일 삭제 중 오류 발생: " + fileName, e);
        }
    }
}
