package com.capstone.fxteam.metal.model.enums;

public enum ImageCategory {
    MECHANICAL_PROPERTIES("기계적물성"),
    MICROSTRUCTURE("미세조직"),
    CONDITION_DIAGRAM("상태도");

    ImageCategory(String value) {
        this.value = value;
    }
    private String value;

    public String getValue() {
        return value;
    }
}
