package com.company.score.service.impl;

import com.company.score.dto.ScorePercentage;
import com.company.score.dto.ScoreRequest;
import com.company.score.dto.type.LimitType;
import com.company.score.entity.ScoreDailyEntity;
import com.company.score.entity.ScoreEntity;
import com.company.score.mapper.ScoreMapper;
import com.company.score.service.GrantCheckService;
import com.company.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final GrantCheckService grantCheckService;
    private final ScoreMapper scoreMapper;

    @Value("${master.id}")
    private String MASTER_ID;

    @Override
    public void checkLimitedUser(ScoreRequest sr) {
        //default
        sr.setLimitType(LimitType.NO_LIMIT);

        if (isShare(sr)){
            sr.setLimitType(LimitType.SHARE);
            return;
        }

        // creator
        if (isLimitedCreator(sr.getCreatorId())) {
            sr.setLimitType(LimitType.CREATOR);
        }

        // player
        if (equalsPlayerAndCreator(sr.getPlayerId(), sr.getCreatorId())
                || isLimitedVisitPlayer(sr.getPlayerId(), sr.getQuiz().getDtId())
                || isLimitedPlayer(sr.getPlayerId())) {
            if (sr.getLimitType() == LimitType.CREATOR) {
                sr.setLimitType(LimitType.PLAYER_CREATOR);
            } else {
                sr.setLimitType(LimitType.PLAYER);
            }
            return;
        }

        // black
        if (isBlackUser(sr)) {
            sr.setLimitType(LimitType.BLACK);
            return;
        }
    }

    @Override
    public void addScore(ScoreRequest sr) {
        List<ScoreEntity> scoreEntityList
            = switch (sr.getScoreType()) {
                case EVENT -> setEventScoreToUser(sr);
                case COUPON -> setCouponScoreToUser(sr);
                case PLAY -> {
                    checkLimitedUser(sr);
                    yield setPlayScoreToUser(sr);
                }
                default -> List.of();
            };
        if (!ObjectUtils.isEmpty(scoreEntityList)) {
            scoreMapper.addScore(scoreEntityList);
        }
    }

    @Override
    public ScoreEntity getScore(String id) {
        return scoreMapper.findById(id);
    }

    @Override
    public List<ScoreDailyEntity> getScoreDetailWithPeriod(String id, Date startTime, Date endTime) {
        return scoreMapper.findScoreDetailWithPeriod(id, startTime, endTime);
    }

    private List<ScoreEntity> setPlayScoreToUser(ScoreRequest sr) {
        ScorePercentage sp = sr.getScoreType().valueOf(sr);
        double requestScore = sr.getRequestScore();
        return List.of(
            ScoreEntity.buildScoreEntity(MASTER_ID,requestScore*sp.getCompany(),"company"),
            ScoreEntity.buildScoreEntity(sr.getPlayerId(),requestScore*sp.getPlayer(),"player"),
            ScoreEntity.buildScoreEntity(sr.getCreatorId(),requestScore*sp.getCreator(),"creator")
        ).stream().filter(entity -> entity.getScore()!=0)
        .map(entity -> entity
                .setScoreTypeByValue(sr.getScoreType())
                .setSubTypeByValue(sr.getSubType())
                .setLimitTypeByValue(sr.getLimitType()))
        .collect(Collectors.toList());
    }

    private List<ScoreEntity> setEventScoreToUser(ScoreRequest sr) {
        ScorePercentage sp = sr.getScoreType().valueOf(sr);
        double requestScore = sr.getRequestScore();
        return List.of(
            ScoreEntity.buildScoreEntity(sr.getTargetId(),requestScore*sp.getTarget(),"event")
        ).stream().filter(entity -> entity.getScore()!=0)
        .map(entity -> entity
                .setScoreTypeByValue(sr.getScoreType())
                .setSubTypeByValue(sr.getSubType()))
        .collect(Collectors.toList());
    }

    private List<ScoreEntity> setCouponScoreToUser(ScoreRequest sr) {
        ScorePercentage sp = sr.getScoreType().valueOf(sr);
        double requestScore = sr.getRequestScore();
        return List.of(
                ScoreEntity.buildScoreEntity(sr.getTargetId(),requestScore*sp.getTarget(),"coupon")
            ).stream().filter(entity -> entity.getScore()!=0)
            .map(entity -> entity
                    .setScoreTypeByValue(sr.getScoreType())
                    .setSubTypeByValue(sr.getSubType()))
            .collect(Collectors.toList());
    }

    private boolean equalsPlayerAndCreator(String playerId, String creatorId) {
        return grantCheckService.equalsPlayerAndCreator(playerId, creatorId);
    }

    private boolean isLimitedCreator(String userId) {
        return grantCheckService.isLimitedCreator(userId);
    }

    private boolean isLimitedPlayer(String userId) {
        return grantCheckService.isLimitedPlayer(userId);
    }

    private boolean isShare(ScoreRequest sr) {
        return grantCheckService.isShare(sr);
    }

    private boolean isBlackUser(ScoreRequest sr) {
        return grantCheckService.isBlackUser(sr.getPlayerId());
    }

    private boolean isLimitedVisitPlayer(String userId, String dtId) {
        return grantCheckService.isLimitedVisitPlayer(userId, dtId);
    }

}
