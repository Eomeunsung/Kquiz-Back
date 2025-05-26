package com.back.kdquiz.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Quiz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    private List<Question> questions;

    @OneToMany(mappedBy = "imgUrl", cascade=CascadeType.REMOVE)
    private List<ImgUrl> imgUrl;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
