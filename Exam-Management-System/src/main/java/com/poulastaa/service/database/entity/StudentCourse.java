package com.poulastaa.service.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_course")
@IdClass(StudentCourseId.class)
public class StudentCourse {
    @Id
    @Column(name = "student_id")
    private Integer studentId;

    @Id
    @Column(name = "course_id")
    private Integer courseId;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private EntityStudent student;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private EntityCourse course;

    // Default constructor
    public StudentCourse() {
    }

    public StudentCourse(Integer studentId, Integer courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // Getters and Setters
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public EntityStudent getStudent() {
        return student;
    }

    public void setStudent(EntityStudent student) {
        this.student = student;
    }

    public EntityCourse getCourse() {
        return course;
    }

    public void setCourse(EntityCourse course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                ", student=" + (student != null ? student.getName() : "null") +
                ", course=" + (course != null ? course.getName() : "null") +
                '}';
    }
}