package com.sbt.test.entities;

public enum Type {

    NUMBER("Число"), STRING("Строка"), ENUMERATION("Перечисление");

    private String type;

    private Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
