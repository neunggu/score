package com.company.score.entity.mongo;

import com.company.score.entity.ScoreEntity;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Document(collection = "score_history")
public class ScoreHistory {
    private String type;
    private String user_id;
    private double score;
    private String transfer_idx;
    private String sub_type;
    private String update_time;
    private int year;
    private int month;

    public static List createScoreHistory(List<ScoreEntity> scoreEntityList){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        return scoreEntityList.stream().map(
                entity-> ScoreHistory.builder()
                    .score(entity.getScore())
                    .user_id(entity.getUserId())
                    .type(entity.getScoreHistoryType().toString())
                    .sub_type(entity.getSubType().toString() + "," + entity.getLimitType().toString())
                    .update_time(sdf.format(c.getTime()))
                    .year(c.get(Calendar.YEAR))
                    .month(c.get(Calendar.MONTH)+1)
                    .build()
            ).collect(Collectors.toList());
    }
}
