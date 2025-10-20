//package com.back.kdquiz.role.service;
//
//import com.back.kdquiz.domain.entity.Role;
//import com.back.kdquiz.domain.repository.RoleRepository;
//import com.back.kdquiz.response.ResponseDto;
//import com.back.kdquiz.role.dto.RoleGetDto;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class RoleGetService {
//
//    private final RoleRepository roleRepository;
//
//    @Transactional
//    public ResponseEntity<ResponseDto<?>> roleGet(){
//        ResponseDto responseDto;
//        try{
//            RoleGetDto roleGetDto = new RoleGetDto();
//            List<Role> roleList = roleRepository.findAll();
//            if(roleList.isEmpty()){
//                responseDto = ResponseDto.setFailed("R000", "권한 목록이 비었습니다.");
//                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
//            }
//            Set<String> roleSet = roleList.stream().map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toSet());
//
//            roleGetDto.setRoles(roleSet);
//            responseDto = ResponseDto.setSuccess("R200", "권한을 가지고 왔습니다.", roleGetDto);
//            return new ResponseEntity<>(responseDto, HttpStatus.OK);
//        }catch (Exception e){
//            responseDto = ResponseDto.setFailed("R001", "권한 목록이 불러오기 실패했습니다."+e.getMessage());
//            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
//        }
//    }
//}
