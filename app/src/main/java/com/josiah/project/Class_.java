package com.josiah.project;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Class_ implements Serializable {

    String name;
    String description;
    String type;
    String day;
    String difficulty;
    String capacity;
    String time;

    public Class_(String name, String description) {
        this.name = name;
        this.description = description;
        type = "";
        day = "";
        difficulty = "";
        capacity = "";
    }

    public Class_(String name, String description, String type, String day, String time, String difficulty, String capacity) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.day = day;
        this.difficulty = difficulty;
        this.time = time;
        this.capacity = capacity;
    }

    @NonNull
    @Override
    public String toString() {
        return "Class_{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", day='" + day + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", capacity=" + capacity +
                ", time=" + time +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getDay() {
        return day;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getTime() {
        return time;
    }
}


