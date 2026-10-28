package com.example.springbootmybatisdemo.mapper;

import com.example.springbootmybatisdemo.dto.StudentWithClass;
import com.example.springbootmybatisdemo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper  //告诉 MyBatis：这是一个数据库映射接口，需要识别并管理它
public interface StudentMapper {

    List<Student> selectAll();

    Student selectById(@Param("id") Integer id);

    int insert(Student student);

    int updateById(Student student);

    int deleteById(Integer id);

    List<Student> selectByName(@Param("name") String name);

    List<Student> selectPage(
            @Param("offset") int offset,
            @Param("size") int size
    );

    long countAll();

    List<Student> selectByNamePage(
            @Param("name") String name,
            @Param("offset") int offset,
            @Param("size") int size
    );

    long countByName(@Param("name") String name);

    List<StudentWithClass> selectAllWithClass();

    int updateClassIdById(
            @Param("studentId") Integer studentId,
            @Param("classId") Integer classId
    );
}

