package com.example.springbootmybatisdemo.dto;

import com.example.springbootmybatisdemo.entity.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentClassDetail {

    private Integer id;
    private String className;
    private List<Student> students = new ArrayList<>();

    public StudentClassDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}