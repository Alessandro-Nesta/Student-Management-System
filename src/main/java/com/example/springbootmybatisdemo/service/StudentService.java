package com.example.springbootmybatisdemo.service;

import com.example.springbootmybatisdemo.dto.StudentWithClass;
import com.example.springbootmybatisdemo.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> selectAll();

    Student selectById(Integer id);

    int insertStudent(Student student);

    int updateById(Student student);

    int deleteById(Integer id);

    List<Student> selectByName(String name);

    List<Student> selectPage(int page, int size);

    long countAll();

    List<Student> selectByNamePage(String name, int page, int size);

    long countByName(String name);

    List<StudentWithClass> selectAllWithClass();

    void transferClass(Integer studentId, Integer classId);

    void transferStudentsToClass(
            List<Integer> studentIds,
            Integer classId
    );

}