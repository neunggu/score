package com.company.score.mapper;

import com.company.score.aspect.db.SlaveDB;
import com.company.score.aspect.history.AddScoreHistory;
import com.company.score.entity.ScoreDailyEntity;
import com.company.score.entity.ScoreEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ScoreMapper {

    @SlaveDB
    @Select("SELECT * FROM score WHERE user_id = #{userId}")
    ScoreEntity findById(String userId);

    @SlaveDB
    @Select("SELECT creator FROM daily_score WHERE h_day=DATE(NOW()) and user_id=#{userId}")
    Double findDailyCreatorScoreByUserId(String userId);

    @SlaveDB
    @Select("SELECT player FROM daily_score WHERE h_day=DATE(NOW()) and user_id=#{userId}")
    Double findDailyPlayerScoreByUserId(String userId);

    @SlaveDB
    @Select("""
        SELECT * FROM daily_score 
        WHERE user_id=#{userId} 
        AND h_day >=#{startTime} 
        AND h_day <=#{endTime} 
        ORDER BY update_time desc
    """)
    List<ScoreDailyEntity> findScoreDetailWithPeriod(String userId, Date startTime, Date endTime);

    @AddScoreHistory
    @Insert("""
        <script>
            INSERT INTO score (user_id, score, total, creator, player, company, event, use, etc) 
            VALUES 
            <foreach item='e' collection='entityList' open='' separator=',' close=''> 
                (#{e.userId},#{e.score},#{e.total},#{e.creator},#{e.player},#{e.company},#{e.event},#{e.use},#{e.etc})
            </foreach> 
            ON DUPLICATE KEY UPDATE 
                score = score + VALUE(score),
                total = total + VALUE(total),
                creator = creator + VALUE(creator),
                player = player + VALUE(player),
                company = company + VALUE(company),
                event = event + VALUE(event),
                use = use + VALUE(use),
                etc = etc + VALUE(etc)
        </script>
        """)
    Integer addScore(List entityList);

    @Insert("""
        <script>
            INSERT INTO daily_score (h_day, user_id, total, creator, player, company, event, use, etc)
            VALUES
            <foreach item='e' collection='entityList' open='' separator=',' close=''> 
               (DATE(NOW()), #{e.userId},#{e.total},#{e.creator},#{e.player},#{e.company},#{e.event},#{e.use},#{e.etc})
            </foreach> 
            ON DUPLICATE KEY UPDATE 
                total = total + VALUE(total),
                creator = creator + VALUE(creator),
                player = player + VALUE(player),
                company = company + VALUE(company),
                event = event + VALUE(event),
                use = use + VALUE(use),
                etc = etc + VALUE(etc)
        </script>
        """)
    void addDailyScore(List entityList);

}
