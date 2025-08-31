package com.poulastaa.service.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class EntityCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "code", nullable = false, length = 20, unique = true)
    private String code;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    // Default constructor required by Hibernate
    public EntityCourse() {
    }

    public EntityCourse(Integer id, String name, String code, Integer duration) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.duration = duration;
    }

    public EntityCourse(String name, String code, Integer duration) {
        this.name = name;
        this.code = code;
        this.duration = duration;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "EntityCourse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", duration=" + duration +
                '}';
    }
}