package com.company.score.entity;

import com.company.score.dto.type.LimitType;
import com.company.score.dto.type.ScoreHistoryType;
import com.company.score.dto.type.ScoreType;
import com.company.score.dto.type.SubType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreEntity {

    private String userId;

    @Builder.Default
    private Double score = 0.0;

    @Builder.Default
    private Double total = 0.0;

    @Builder.Default
    private Double creator = 0.0;

    @Builder.Default
    private Double player = 0.0;

    @Builder.Default
    private Double company = 0.0;

    @Builder.Default
    private Double exchange = 0.0;

    @Builder.Default
    private Double use = 0.0;

    @Builder.Default
    private Double event = 0.0;

    @Builder.Default
    private Double coupon = 0.0;

    @Builder.Default
    private Double etc = 0.0;

    @Builder.Default
    private ScoreType scoreType = ScoreType.PLAY;
    @Builder.Default
    private ScoreHistoryType scoreHistoryType = ScoreHistoryType.PLAYER;
    @Builder.Default
    private SubType subType = SubType.DEFAULT;
    @Builder.Default
    private LimitType limitType = LimitType.NO_LIMIT;

    public ScoreEntity setScoreByField(Double score, String field) {
        try {
            Field declaredField = this.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);
            declaredField.set(this, score);
        } catch (Exception e) {
            System.out.println(e);
        }
        return this;
    }

    public static ScoreEntity buildScoreEntity(String userId, Double score, String field){
        return ScoreEntity.builder()
                .userId(userId)
                .total(score)
                .score(score)
                .build()
                .setScoreByField(score, field)
                .setScoreHistoryTypeByField(field);
    }

    public ScoreEntity setSubTypeByValue(SubType subType){
        this.setSubType(subType==null
                ? SubType.DEFAULT : subType);
        return this;
    }

    public ScoreEntity setLimitTypeByValue(LimitType limitType){
        this.setLimitType(limitType==null
                ? LimitType.NO_LIMIT : limitType);
        return this;
    }

    public ScoreEntity setScoreTypeByValue(ScoreType scoreType){
        this.setScoreType(scoreType==null
                ? ScoreType.PLAY : scoreType);
        return this;
    }

    private ScoreEntity setScoreHistoryTypeByField(String field){
        this.setScoreHistoryType(ScoreHistoryType.valueOfField(field));
        return this;
    }
}
