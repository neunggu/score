package com.company.score.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class CacheService {

    private final CacheManager cacheManager;

    public Collection<String> getCacheNames() {
        return cacheManager.getCacheNames();
    }

    public Cache getCache(String cacheName){
        return cacheManager.getCache(cacheName);
    }

    public boolean clearAll(){
        cacheManager.getCacheNames()
                .parallelStream()
                .forEach(name -> cacheManager.getCache(name).clear());
        return true;
    }

    public boolean clear(String cacheName){
        if (!ObjectUtils.isEmpty(getCache(cacheName))) {
            getCache(cacheName).clear();
            return true;
        }
        return false;
    }

    @Scheduled(cron = "0 0 00 * * ?")
    public void clearAllCachesAtIntervals() {
        clearAll();
    }

}
