package com.back.kdquiz.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class Option implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private int time; //시간

    @Column
    private Boolean useAiFeedback; //Ai여부

    @Column
    private String aiQuestion;//ai질문


    @Column
    private Boolean useCommentary;

    @Column
    private String commentary; //해설

    @Column
    private int score; //점수

    @OneToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Option() {
    }

    @Builder
    public Option(Long id, int time, Boolean useAiFeedback, String aiQuestion, Boolean useCommentary, String commentary, int score, Question question) {
        this.id = id;
        this.time = time;
        this.useAiFeedback = useAiFeedback;
        this.aiQuestion = aiQuestion;
        this.useCommentary = useCommentary;
        this.commentary = commentary;
        this.score = score;
        this.question = question;
    }

    @Builder
    void optionUpdate(Long id, int time, Boolean useAiFeedback, String aiQuestion, Boolean useCommentary, String commentary, int score, Question question){
        this.id = id;
        this.time = time;
        this.useAiFeedback = useAiFeedback;
        this.aiQuestion = aiQuestion;
        this.useCommentary = useCommentary;
        this.commentary = commentary;
        this.score = score;
        this.question = question;
    }
}
