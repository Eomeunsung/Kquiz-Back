package com.back.kdquiz.config.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomFileUtil {
    //파일 저장 위치 경로
    @Value ("${spring.kdquiz.upload.path}")
    private String uploadPath;

    //실행할때 디폴트로 실행
    @PostConstruct
    public void init(){
        File file = new File(uploadPath);
        if(file.exists()==false){ //만약 디렉토리가 없으면
            file.mkdirs(); //디렉토리 새로 생성
        }
        uploadPath = file.getAbsolutePath(); //경로 절대 경로
    }

    public List<String> saveFiles(List<MultipartFile> files) throws IOException {
        if(files ==null || files.isEmpty()){
            log.info("파일이 없음 ");
            return null;
        }
        List<String> uploadNames = new ArrayList<>();
        for(MultipartFile file : files){
            String saveName = file.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, saveName);
            try{
                Files.copy(file.getInputStream(), savePath);
                uploadNames.add(saveName);
            }catch (IOException e){
                throw new IOException("파일 저장 중 오류 발생: " + file.getOriginalFilename(), e);
            }
        }
        return uploadNames;
    }

    public String changeSaveFiles(MultipartFile file) throws IOException{
        if(file ==null || file.isEmpty()){
            log.info("파일이 없음 ");
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // UUID로 새로운 파일 이름 생성
        String saveName = UUID.randomUUID().toString() + extension;
        try{
            Path savePath = Paths.get(uploadPath, saveName);

            Files.copy(file.getInputStream(), savePath);
        }catch (IOException e){
            throw new IOException("파일 저장 중 오류 발생: " + file.getOriginalFilename(), e);
        }
        log.info("변경된 파일 이름: {}", saveName);
        return saveName;
    }

    public void deleteProfileImg(String fileName) throws IOException{
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
