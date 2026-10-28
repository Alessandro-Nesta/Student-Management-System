package com.example.springbootmybatisdemo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class BatchTransferRequest {

    @NotEmpty(message = "学生ID列表不能为空")
    private List<
            @NotNull(message = "学生ID不能为空")
            @Positive(message = "学生ID必须大于0")
                    Integer
            > studentIds;

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }
}
