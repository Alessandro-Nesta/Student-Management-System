package com.example.springbootmybatisdemo.service;

import com.example.springbootmybatisdemo.dto.StudentWithClass;
import com.example.springbootmybatisdemo.entity.Student;
import com.example.springbootmybatisdemo.exception.StudentClassNotFoundException;
import com.example.springbootmybatisdemo.exception.StudentNotFoundException;
import com.example.springbootmybatisdemo.mapper.StudentClassMapper;
import com.example.springbootmybatisdemo.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service   //告诉 Spring：这个类是业务层组件，需要由 Spring 容器管理
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final StudentClassMapper studentClassMapper;

    //Spring 会把已经注册的 StudentMapper 对象传进来，这样就不用自己 new StudentMapper，因为它是 MyBatis 提供的代理对象
    public StudentServiceImpl(
            StudentMapper studentMapper,
            StudentClassMapper studentClassMapper) {

        this.studentMapper = studentMapper;
        this.studentClassMapper = studentClassMapper;
    }

    @Override
    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }


    @Override
    public Student selectById(Integer id) {
        Student student = studentMapper.selectById(id);

        if (student == null) {
            throw new StudentNotFoundException(
                    "学生不存在，id=" + id);
        }

        return student;
    }


    @Override
    public int insertStudent(Student student) {
        return studentMapper.insert(student);
    }


    @Override
    public int updateById(Student student) {
        int affectedRows = studentMapper.updateById(student);

        if (affectedRows == 0 && studentMapper.selectById(student.getId()) == null) {
            throw new StudentNotFoundException("学生不存在，id=" + student.getId());
        }

        return affectedRows;
    }

    @Override
    public int deleteById(Integer id) {
        int affectedRows = studentMapper.deleteById(id);

        if (affectedRows == 0) {
            throw new StudentNotFoundException("学生不存在，id=" + id);
        }

        return affectedRows;
    }


    @Override
    public List<Student> selectByName(String name) {
        return studentMapper.selectByName(name);
    }

    @Override
    public List<Student> selectPage(int page, int size) {
        int offset = (page - 1) * size;
        return studentMapper.selectPage(offset, size);
    }

    @Override
    public long countAll() {
        return studentMapper.countAll();
    }

    @Override
    public List<Student> selectByNamePage(String name, int page, int size) {
        int offset = (page - 1) * size;
        return studentMapper.selectByNamePage(name, offset, size);
    }

    @Override
    public long countByName(String name) {
        return studentMapper.countByName(name);
    }

    @Override
    public List<StudentWithClass> selectAllWithClass() {
        return studentMapper.selectAllWithClass();
    }


    @Override
    public void transferClass(Integer studentId, Integer classId) {

        Student student = studentMapper.selectById(studentId);

        if (student == null) {
            throw new StudentNotFoundException(
                    "学生不存在，id=" + studentId);
        }

        Integer existingClassId =
                studentClassMapper.selectIdById(classId);

        if (existingClassId == null) {
            throw new StudentClassNotFoundException(
                    "班级不存在，id=" + classId);
        }

        studentMapper.updateClassIdById(studentId, classId);
    }



    @Override
    @Transactional  //告诉 Spring：调用这个业务方法时，要在事务中执行数据库操作
    public void transferStudentsToClass(List<Integer> studentIds, Integer classId) {

        // 1. 检查目标班级是否存在
        Integer existingClassId = studentClassMapper.selectIdById(classId);

        if (existingClassId == null) {
            throw new StudentClassNotFoundException("班级不存在，id=" + classId);
        }

        // 2. 逐个检查学生并更新班级
        for (Integer studentId : studentIds) {

            Student student = studentMapper.selectById(studentId);

            if (student == null) {
                throw new StudentNotFoundException("学生不存在，id=" + studentId);
            }

            int affectedRows = studentMapper.updateClassIdById(studentId, classId);

            // 如果学生原本不属于目标班级，却没有更新任何记录，
            // 说明更新没有按预期完成，终止整个业务操作。
            if (affectedRows == 0 && !java.util.Objects.equals(student.getClassId(), classId)) {
                throw new IllegalStateException("更新学生班级失败，id=" + studentId);
            }
        }
    }


}