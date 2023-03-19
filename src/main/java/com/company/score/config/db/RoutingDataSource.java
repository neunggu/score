package com.company.score.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        Object dbKey = null;
        if(RequestContextHolder .getRequestAttributes() == null
            || RequestContextHolder.getRequestAttributes()
                .getAttribute("db_key", RequestAttributes.SCOPE_SESSION) == null) {
            dbKey = "master";
        } else {
            dbKey = RequestContextHolder
                    .getRequestAttributes()
                    .getAttribute("db_key", RequestAttributes.SCOPE_SESSION);
        }
        return "current:" + dbKey;

    }
}
