package com.example.springbootmybatisdemo.service;

import com.example.springbootmybatisdemo.dto.StudentClassDetail;
import com.example.springbootmybatisdemo.mapper.StudentClassMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentClassServiceImpl implements StudentClassService {

    private final StudentClassMapper studentClassMapper;

    public StudentClassServiceImpl(StudentClassMapper studentClassMapper) {
        this.studentClassMapper = studentClassMapper;
    }

    @Override
    public StudentClassDetail selectByIdWithStudents(Integer id) {
        return studentClassMapper.selectByIdWithStudents(id);
    }
}