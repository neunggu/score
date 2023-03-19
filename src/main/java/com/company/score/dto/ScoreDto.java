package com.company.score.dto;

import com.company.score.entity.ScoreEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreDto {
    private String userId;
    private double score;
    private double total;
    private double creator;
    private double player;
    private double company;
    private double exchange;
    private double use;
    private double event;
    private double coupon;
    private double etc;


    public static ScoreDto entityToDto(ScoreEntity entity){
        return ScoreDto.builder()
                .userId(entity.getUserId())
                .score(entity.getScore())
                .total(entity.getTotal())
                .creator(entity.getCreator())
                .player(entity.getPlayer())
                .company(entity.getCompany())
                .exchange(entity.getExchange())
                .use(entity.getUse())
                .event(entity.getEvent())
                .coupon(entity.getCoupon())
                .etc(entity.getEtc())
                .build();
    }
}
