package com.company.score.dto;

import com.company.score.entity.ScoreDailyEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ScoreDailyDto {
    private String userId;
    private double total;
    private double creator;
    private double player;
    private double company;
    private double exchange;
    private double use;
    private double event;
    private double coupon;
    private double etc;
    private String hDay;

    public static List<ScoreDailyDto> entityToDto(List<ScoreDailyEntity> entityList){
        return entityList.stream().map(entity -> ScoreDailyDto.builder()
                .userId(entity.getUserId())
                .total(entity.getTotal())
                .creator(entity.getCreator())
                .player(entity.getPlayer())
                .company(entity.getCompany())
                .exchange(entity.getExchange())
                .use(entity.getUse())
                .event(entity.getEvent())
                .coupon(entity.getCoupon())
                .etc(entity.getEtc())
                .hDay(entity.getHDay())
                .build()
        ).collect(Collectors.toList());
    }
}
