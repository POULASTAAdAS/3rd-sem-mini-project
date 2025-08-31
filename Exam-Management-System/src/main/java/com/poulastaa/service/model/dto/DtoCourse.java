package com.poulastaa.service.model.dto;

import com.poulastaa.service.database.entity.EntityCourse;

public record DtoCourse(
        String title,
        String code,
        Integer duration
) {
    public EntityCourse toEntity() {
        return new EntityCourse(title, code, duration);
    }

    @Override
    public String toString() {
        return "CourseInput{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", duration=" + duration +
                '}';
    }
}