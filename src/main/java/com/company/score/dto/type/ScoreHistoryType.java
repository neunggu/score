package com.company.score.dto.type;

public enum ScoreHistoryType {
    PLAYER(100,  "player"),
    CREATOR(200, "creator"),
    COMPANY(300, "company"),
    EXCHANGE(400, "exchange"),
    COUPON(500, "coupon"),
    USE(600,"use"),
    EVENT(700,"event"),
    ETC(900,  "etc");


    private final int code;
    private final String field;
    ScoreHistoryType(int code, String field) {
        this.code = code;
        this.field = field;
    }
    public int value() {
        return this.code;
    }
    public static ScoreHistoryType valueOfField(String field) {
        for (ScoreHistoryType type : values()) {
            if (type.field == field) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + field + "]");
    }
}
