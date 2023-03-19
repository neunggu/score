package com.company.score.dto;

import com.company.score.dto.type.LimitType;
import com.company.score.dto.type.ScoreType;
import com.company.score.dto.type.SubType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreRequest {
    private String playerId;
    private String creatorId;
    private String sharerId;
    private String targetId;
    private ScoreRequestQuiz quiz;
    private Double requestScore;
    private Map data;

    @Builder.Default
    private ScoreType scoreType = ScoreType.PLAY;
    @Builder.Default
    private SubType subType = SubType.DEFAULT;
    @Builder.Default
    private LimitType limitType = LimitType.NO_LIMIT;
}
