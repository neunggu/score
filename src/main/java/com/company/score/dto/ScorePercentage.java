package com.company.score.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScorePercentage {
    private double player;
    private double creator;
    private double sharer;
    private double company;
    private double target;
}
