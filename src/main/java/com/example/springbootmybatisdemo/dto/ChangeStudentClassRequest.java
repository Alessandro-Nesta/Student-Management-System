package com.example.springbootmybatisdemo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ChangeStudentClassRequest {

    @NotNull(message = "目标班级ID不能为空")
    @Positive(message = "目标班级ID必须大于0")
    private Integer classId;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}
