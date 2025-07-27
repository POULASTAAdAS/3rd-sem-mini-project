package com.poulastaa.service.database.entity;

public record EntitySubject(
        Integer id,
        String name,
        String code,
        Double credits
) {
    @Override
    public String toString() {
        return "DtoSubject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", credits=" + credits +
                '}';
    }
}