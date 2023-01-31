package com.company.score.dto.type;

import com.company.score.dto.ScorePercentage;
import com.company.score.dto.ScoreRequest;

public enum ScoreType {
    PLAY(ScorePercentage.builder().build()),
    EVENT(ScorePercentage.builder().target(1).build()),
    COUPON(ScorePercentage.builder().target(1).build());

    private final ScorePercentage scorePercentage;

    ScoreType(ScorePercentage scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public ScorePercentage valueOf(ScoreRequest sr){
        return switch (this) {
            case PLAY -> playScorePercentage(sr);
            default -> this.scorePercentage;
        };
    }

    private ScorePercentage playScorePercentage(ScoreRequest sr) {
        ScorePercentage.ScorePercentageBuilder builder = ScorePercentage.builder();
        boolean isCorrect = sr.getQuiz().isCorrect();
        double player = 0.6;
        double creator = 0.2;
        double sharer = 0.1;
        double company = 0.1;
        if (!isCorrect) {
            player = 0;
            creator = 0.8;
        }

        switch (sr.getLimitType()) {
            case BLACK: case PLAYER: case PLAYER_CREATOR: case SHARE:
                player = 0; creator = 0; sharer = 0; company=1;
                break;
            case CREATOR:
                creator = 0;
                break;
        }

        return builder
                .player(player)
                .creator(creator)
                .sharer(sharer)
                .company(company)
                .build();
    }

}
