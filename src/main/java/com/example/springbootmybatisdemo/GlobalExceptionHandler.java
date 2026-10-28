package com.example.springbootmybatisdemo;

import com.example.springbootmybatisdemo.exception.StudentClassNotFoundException;
import com.example.springbootmybatisdemo.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//接住已经发生的异常，把异常转换成我们想要的 HTTP 响应
@RestControllerAdvice   //告诉 Spring：这个类负责集中处理 Controller 中发生的异常，并将处理结果作为响应内容返回，它可以集中处理多个 Controller 的异常，而不必在每一个 Controller 方法中都写一遍 try-catch
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)  //当 Spring 处理请求时，发生了 MethodArgumentNotValidException 这种异常，就使用下面的方法来处理
    @ResponseStatus(HttpStatus.BAD_REQUEST)  //让这个异常处理方法返回 HTTP 400 状态码
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String message = error.getDefaultMessage();

            if (message != null && !messages.contains(message)) {
                messages.add(message);
            }
        });
        //ex.getBindingResult().getFieldErrors()：从本次请求产生的异常中，把所有校验失败的记录取出来
        //error.getDefaultMessage()：从这条错误记录中取出提示文字

        return Map.of(
                "status", 400,
                "error", "Bad Request",
                "messages", messages
        );
    }



    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(StudentNotFoundException ex) {

        Map<String, Object> body = Map.of(
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }



    @ExceptionHandler(StudentClassNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudentClassNotFound(
            StudentClassNotFoundException ex) {

        Map<String, Object> body = Map.of(
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }


}
