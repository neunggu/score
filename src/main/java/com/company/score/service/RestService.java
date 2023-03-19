package com.company.score.service;

import com.company.score.dto.User;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

public interface RestService {

    @Cacheable(value="sso:user:token:accessToken", key="#accessToken", unless="#result==null")
    User getUserByAccessToken(String accessToken);

    @Cacheable(value="sso:user:id", key="#userId", unless="#result==null")
    User getUserByUserId(String userId);

    boolean isLimitedVisitPlayer(String userId, String dtId, int limitEnter, int limitPlay);
}
