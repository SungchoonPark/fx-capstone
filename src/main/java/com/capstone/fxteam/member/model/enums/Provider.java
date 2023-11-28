package com.capstone.fxteam.member.model.enums;

public enum Provider {
    KAKAO("KAKAO");

    private String value;

    Provider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
