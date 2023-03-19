package com.company.score.dto;

import lombok.Data;

@Data
public class ScoreRequestQuiz {
    private String id;
    private String dtId;
    private String shareId;
    private boolean correct;
    private boolean share;
}
