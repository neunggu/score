package com.company.score.aspect.db;

import com.company.score.config.db.DBSessionConfigUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Aspect
@Component
@RequiredArgsConstructor
public class DB{

    private final HttpSession httpSession;
    private final DBSessionConfigUtil dbSessionUtil;

    @Around("@annotation(SlaveDB)")
    public Object setSlave(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            dbSessionUtil.setDbKey(httpSession, "slave");
            result = joinPoint.proceed();
        } catch (Throwable e) {
        } finally {
            dbSessionUtil.setDbKey(httpSession, "master");
        }
        return result;
    }
}
