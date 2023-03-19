package com.company.score.controller;

import com.company.score.dto.ScoreDailyDto;
import com.company.score.dto.ScoreDto;
import com.company.score.dto.ScoreRequest;
import com.company.score.dto.User;
import com.company.score.entity.ScoreDailyEntity;
import com.company.score.entity.ScoreEntity;
import com.company.score.service.RestService;
import com.company.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("score")
public class ScoreController {

    private final RestService restService;
    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity addScore(@RequestBody ScoreRequest sr) {
        scoreService.addScore(sr);
        return ResponseEntity.ok(null);
    }

    @GetMapping("detail")
    public ResponseEntity getScoreDetail(@RequestHeader(value = "Authorization") String accessToken){
        User user = restService.getUserByAccessToken(accessToken);
        if (ObjectUtils.isEmpty(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            ScoreEntity scoreEntity = scoreService.getScore(user.getId());
            return ResponseEntity.ok(
                    scoreEntity == null ?
                            ScoreDto.builder().userId(user.getId()).build()
                            :ScoreDto.entityToDto(scoreEntity)
            );
        }
    }

    @GetMapping("detail/daily")
    public ResponseEntity getScoreDetailWithPeriod(@RequestHeader(value = "Authorization") String accessToken,
                                                   @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                   @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){
        User user = restService.getUserByAccessToken(accessToken);
        if (ObjectUtils.isEmpty(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            List<ScoreDailyEntity> scoreDailyEntityList = scoreService.getScoreDetailWithPeriod(user.getId(), startTime, endTime);
            List<ScoreDailyDto> scoreDailyDtoList = ScoreDailyDto.entityToDto(scoreDailyEntityList);
            return ResponseEntity.ok(scoreDailyDtoList);
        }
    }
}
