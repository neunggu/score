package com.company.score.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDailyEntity {

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

    private String hDay;

}
