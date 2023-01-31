package com.company.score.service;

import com.company.score.dto.ScoreRequest;
import com.company.score.entity.ScoreDailyEntity;
import com.company.score.entity.ScoreEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.List;

public interface ScoreService {

    void checkLimitedUser(ScoreRequest sr);
    @Async
    void addScore(ScoreRequest sr);
    ScoreEntity getScore(String id);

    List<ScoreDailyEntity> getScoreDetailWithPeriod(String id, Date startTime, Date endTime);
}
