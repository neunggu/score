package com.company.score.aspect.history;

import com.company.score.entity.ScoreEntity;
import com.company.score.entity.mongo.ScoreHistory;
import com.company.score.mapper.ScoreMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class History {

    private final ScoreMapper scoreMapper;
    private final MongoTemplate mongoTemplate;

    @After("@annotation(com.company.score.aspect.history.AddScoreHistory)")
    public void addScoreHistory(JoinPoint joinPoint) {
        List<ScoreEntity> scoretList = (List) joinPoint.getArgs()[0];
        scoreMapper.addDailyScore(scoretList);
        // mongo history insert
        List scoreHistory = ScoreHistory.createScoreHistory(scoretList);
        mongoTemplate.insertAll(scoreHistory);
    }
}
