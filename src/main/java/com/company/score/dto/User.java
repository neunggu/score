package com.company.score.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String name;
    private String origin_name;
    private String email;
    private String imgKey;
    private String login_type;
    private String qr_id;
    private String locale;
    private String lang;
    private String userType;
}
