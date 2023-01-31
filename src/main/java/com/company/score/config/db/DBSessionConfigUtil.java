package com.company.score.config.db;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class DBSessionConfigUtil {

    public void setDbKey(HttpSession httpSession, String dbKey) {
        if(httpSession != null && httpSession.getAttribute("db_key") != null ) {
            String dbSessionkey = (String) httpSession.getAttribute("db_key");
            if(!dbSessionkey.equals(dbKey)) {
                httpSession.setAttribute("db_key", dbKey);
            }
        } else {
            httpSession.setAttribute("db_key", dbKey);
        }
    }

}
