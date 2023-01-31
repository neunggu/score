package com.company.score.service.impl;

import com.company.score.dto.User;
import com.company.score.service.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {

    private final RestTemplate restTemplate;

    @Value("${sso.url}")
    private String ssoUrl;
    @Value("${channel.url}")
    private String channelUrl;

    @Override
    public User getUserByUserId(String userId) {
        String url = ssoUrl+"/users/find?"+"id="+userId;
        return restTemplate.postForObject(url, null, User.class);
    }

    @Override
    public User getUserByAccessToken(String accessToken) {
        String url = ssoUrl + "/users";
        HttpEntity request = getHttpEntity(accessToken);
        ResponseEntity<User> result;
        try {
            result = restTemplate.exchange(url, HttpMethod.GET, request, User.class);
        } catch (Exception e) {
            return null;
        }
        return result.getBody();
    }

    @Override
    public boolean isLimitedVisitPlayer(String userId, String dtId, int limitEnter, int limitPlay) {
        String url = channelUrl+"/score/isLimitedVisitPlayer?userId="+userId
                +"&dtId="+dtId+"&limitEnter="+limitEnter+"&limitPlay="+limitPlay;
        return restTemplate.getForObject(url, boolean.class);
    }

    private HttpEntity getHttpEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);
        return new HttpEntity(headers);
    }
}
