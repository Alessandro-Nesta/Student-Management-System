package com.example.springbootmybatisdemo.service;

import com.example.springbootmybatisdemo.dto.StudentClassDetail;

public interface StudentClassService {

    StudentClassDetail selectByIdWithStudents(Integer id);
}