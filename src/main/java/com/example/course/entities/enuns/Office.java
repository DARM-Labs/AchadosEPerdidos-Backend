package com.example.course.entities.enuns;

public enum Office {
    DISCENTE(1),
    DOSCENTE(2),
    TAES(3);
    private int code;

    private Office(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static Office valueOf(int code) {
        for(Office value : Office.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid User Office code");
    }
}
