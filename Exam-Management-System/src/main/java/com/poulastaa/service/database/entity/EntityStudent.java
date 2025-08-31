package com.poulastaa.service.database.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "student")
public class EntityStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "roll", nullable = false, length = 20)
    private String roll;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "bDate", nullable = false)
    private Date bDate;

    // Default constructor required by Hibernate
    public EntityStudent() {
    }

    public EntityStudent(Integer id, String roll, String name, Integer age, Date bDate) {
        this.id = id;
        this.roll = roll;
        this.name = name;
        this.age = age;
        this.bDate = bDate;
    }

    public EntityStudent(String roll, String name, Integer age, Date bDate) {
        this.roll = roll;
        this.name = name;
        this.age = age;
        this.bDate = bDate;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBDate() {
        return bDate;
    }

    public void setBDate(Date bDate) {
        this.bDate = bDate;
    }

    @Override
    public String toString() {
        return "EntityStudent{" +
                "id=" + id +
                ", roll='" + roll + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", bDate=" + bDate +
                '}';
    }
}