package com.poulastaa.service.model.dto;

import com.poulastaa.service.database.entity.EntitySubject;

public record DtoSubject(
        String name,
        String code,
        Double credits
) {
    public EntitySubject withId(int subjectId) {
        return new EntitySubject(subjectId, name, code, credits);
    }

    @Override
    public String toString() {
        return "SubjectInput{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", credits=" + credits +
                '}';
    }
}