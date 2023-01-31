package com.company.score.service;

import com.company.score.dto.ScoreRequest;
import org.springframework.cache.annotation.Cacheable;

public interface GrantCheckService {

    @Cacheable(value="score:limit:todayCreator", key="#userId", unless="#result==false")
    boolean isLimitedCreator(String userId);

    @Cacheable(value="score:limit:todayPlayer", key="#userId", unless="#result==false")
    boolean isLimitedPlayer(String userId);

    @Cacheable(value="score:limit:todayVisit", key="{#userId, #dtId}", unless="#result==false")
    boolean isLimitedVisitPlayer(String userId, String dtId);

    boolean isBlackUser(String userId);

    boolean equalsPlayerAndCreator(String playerId, String creatorId);

    boolean isShare(ScoreRequest sr);
}
