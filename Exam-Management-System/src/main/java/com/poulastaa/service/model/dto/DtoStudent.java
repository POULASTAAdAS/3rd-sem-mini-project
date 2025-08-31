package com.poulastaa.service.model.dto;

import com.poulastaa.service.database.entity.EntityStudent;

import java.sql.Date;

public record DtoStudent(
        String roll,
        String name,
        Integer age,
        Date bDate
) {
    public EntityStudent toEntity() {
        return new EntityStudent(roll, name, age, bDate);
    }

    @Override
    public String toString() {
        return "EntityStudent{" +
                "roll='" + roll + '\'' +
                ", title='" + name + '\'' +
                ", age=" + age +
                ", bDate=" + bDate +
                '}';
    }
}