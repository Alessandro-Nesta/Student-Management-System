package com.example.springbootmybatisdemo.mapper;

import com.example.springbootmybatisdemo.dto.StudentClassDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudentClassMapper {

    StudentClassDetail selectByIdWithStudents(@Param("id") Integer id);

    Integer selectIdById(@Param("id") Integer id);
}