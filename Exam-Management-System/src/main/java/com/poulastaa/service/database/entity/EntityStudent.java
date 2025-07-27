package com.poulastaa.service.database.entity;

import java.sql.Date;

public record EntityStudent(Integer id,
                            String roll,
                            String name,
                            Integer age,
                            Date bDate
) {
    @Override
    public String toString() {
        return "DtoStudent{" +
                "id=" + id +
                ", roll='" + roll + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", bDate=" + bDate +
                '}';
    }
}