package com.back.kdquiz.domain.repository;

import com.back.kdquiz.domain.entity.ImgUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgUrlRepository extends JpaRepository<ImgUrl, Long> {

    List<ImgUrl> findByQuiz_Id(Long id);

    ImgUrl findByImgUrl(String imgUrl);
}
