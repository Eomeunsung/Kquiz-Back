package com.back.kdquiz.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
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

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade=CascadeType.REMOVE)
    private List<ImgUrl> imgUrl;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Quiz() {
    }

    @Builder
    public Quiz(String title, Users users, List<Question> questions, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.users = users;
        this.questions = questions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
