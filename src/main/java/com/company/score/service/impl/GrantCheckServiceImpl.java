package com.company.score.service.impl;

import com.company.score.dto.ScoreRequest;
import com.company.score.dto.User;
import com.company.score.mapper.ScoreMapper;
import com.company.score.service.GrantCheckService;
import com.company.score.service.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class GrantCheckServiceImpl implements GrantCheckService {

    private final RestService restService;
    private final ScoreMapper scoreMapper;

    @Value("${limit.daily.player}")
    private int LIMIT_DAILY_PLAYER_SCORE;
    @Value("${limit.daily.creator}")
    private int LIMIT_DAILY_CREATOR_SCORE;
    @Value("${limit.pack.enter}")
    private int LIMIT_DAILY_PACK_ENTER_SCORE;
    @Value("${limit.pack.play}")
    private int LIMIT_DAILY_PACK_PLAY_SCORE;

    @Override
    public boolean isLimitedCreator(String userId) {
        return isLimitedCreatorScore(userId);
    }

    @Override
    public boolean isLimitedPlayer(String userId) {
        return isLimitedPlayerScore(userId);
    }

    @Override
    public boolean isBlackUser(String userId) {
        User user = restService.getUserByUserId(userId);
        return ObjectUtils.isEmpty(user) ? true : user.getUserType().equals("B");
    }

    @Override
    public boolean isLimitedVisitPlayer(String userId, String dtId) {
        return restService.isLimitedVisitPlayer(userId, dtId, LIMIT_DAILY_PACK_ENTER_SCORE, LIMIT_DAILY_PACK_PLAY_SCORE);
    }

    @Override
    public boolean equalsPlayerAndCreator(String playerId, String creatorId) {
        return playerId.equals(creatorId);
    }

    @Override
    public boolean isShare(ScoreRequest sr) {
        return sr.getQuiz().isShare();
    }

    private boolean isLimitedCreatorScore(String userId) {
        Double score = scoreMapper.findDailyCreatorScoreByUserId(userId);
        return ObjectUtils.isEmpty(score) ? false : score > LIMIT_DAILY_CREATOR_SCORE;
    }

    private boolean isLimitedPlayerScore(String userId) {
        Double score =  scoreMapper.findDailyPlayerScoreByUserId(userId);
        return ObjectUtils.isEmpty(score) ? false : score > LIMIT_DAILY_PLAYER_SCORE;
    }



}
