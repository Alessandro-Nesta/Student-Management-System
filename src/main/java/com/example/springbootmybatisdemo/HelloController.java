package com.example.springbootmybatisdemo;

import com.example.springbootmybatisdemo.dto.*;
import com.example.springbootmybatisdemo.service.StudentClassService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.springbootmybatisdemo.entity.Student;
import com.example.springbootmybatisdemo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController   //告诉 Spring，这是一个负责处理 Web 请求的类
public class HelloController {

    private final StudentService studentService;
    private final StudentClassService studentClassService;

    public HelloController(
            StudentService studentService,
            StudentClassService studentClassService) {

        this.studentService = studentService;
        this.studentClassService = studentClassService;
    }

    @GetMapping("/hello")   //告诉 Spring，当有人通过 HTTP GET 请求访问 /hello 时，就调用下面的 hello() 方法，网址是http://localhost:8080/hello
    public String hello() {
        return "Hello, Spring Boot!";   //把字符串作为响应内容返回给浏览器
    }


    @GetMapping("/greet")
    public String greet(@RequestParam(name = "name", defaultValue = "访客") String name) {     //@RequestParam的作用是让 Spring 从浏览器发送的请求中读取名为 name 的参数，并将其值交给 Java 方法中的变量 name
        return "你好，" + name + "！";
    }
    //例：http://localhost:8080/greet?name=Tom的结果是：你好，Tom！
    //例：http://localhost:8080/greet的结果是：你好，访客！


    @GetMapping("/welcome")
    public String welcome(@RequestParam("name") String userName) {
        return "你好，" + userName + "！";
    }
    //例：输入网址http://localhost:8080/welcome?name=Tom，/welcome 是请求路径，?name=Tom 是传给服务器的参数
    //Spring 从网址中取出参数 name 的值，也就是 Tom，然后将它交给 Java 变量 userName，然后网址的运行结果为：你好，Tom！



    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.selectAll();
    }
    //输入网址http://localhost:8080/students后浏览器里会出现数据库里的学生信息（JSON形式），但他和Student 类中的 toString() 方法无关
    //浏览器发请求 → Spring Boot 接住 → Controller 调 Service → Service 调 MyBatis → MyBatis 查 MySQL → 再把结果返回浏览器
    //由于@RestController 可以理解为组合了 @Controller 和 @ResponseBody 的效果，一个顶俩
    //@ResponseBody 的核心作用是告诉 Spring：不要把方法的返回值当作页面名称，而是把它作为 HTTP 响应的内容返回给客户端
    //于是，接下来Spring Boot 会利用 Jackson 等 JSON 处理组件，把 Java 对象转换为 JSON 文本


    @GetMapping("/students/{id}")   //{id} 表示这个位置可以接受一个动态的路径参数
    public Student getStudentById(@PathVariable("id") Integer id) {   //@PathVariable("id")告诉 Spring：从网址的路径中，取出名为 id 的变量值，然后赋值给整形变量id
        return studentService.selectById(id);
    }
    //例：输入网址http://localhost:8080/students/1后会浏览器里会出现相应信息
    //return的结果最终被 Spring 转换成 HTTP 响应，发送给请求它的客户端，因此虽然没写print但也会呈现出来


    @PostMapping("/students")
    public int addStudent(@Valid @RequestBody Student student) {
        return studentService.insertStudent(student);
    }


    @PutMapping("/students")
    public int updateStudent(@Valid @RequestBody Student student) {
        return studentService.updateById(student);
    }


    @DeleteMapping("/students/{id}")
    public int deleteStudent(@PathVariable("id") Integer id) {
        return studentService.deleteById(id);
    }


    @GetMapping("/students/search")
    public List<Student> searchStudents(@RequestParam("name") String name) {
        return studentService.selectByName(name);
    }




    @GetMapping("/students/page")
    public PageResult<Student> getStudentsByPage(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        if (page < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "页码必须大于等于1");
        }

        if (size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "每页数量必须在1到100之间");
        }

        List<Student> students = studentService.selectPage(page, size);
        long total = studentService.countAll();

        return new PageResult<>(page, size, total, students);
    }
    //由于Map.of() 不保证键值对的迭代顺序，所以当Spring将这个 Map 转换成 JSON 时，字段的排列顺序可能与源代码不同，比如先打印size，再打印学生数据，再打印剩余的






    @GetMapping("/students/search/page")
    public PageResult<Student> searchStudentsByPage(
            @RequestParam("name") String name,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        String keyword = name.trim();    //去掉name两端可能有的空行

        if (keyword.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "姓名查询条件不能为空");
        }

        if (page < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "页码必须大于等于1");
        }

        if (size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "每页数量必须在1到100之间");
        }

        List<Student> students =
                studentService.selectByNamePage(keyword, page, size);

        long total = studentService.countByName(keyword);

        return new PageResult<>(page, size, total, students);
    }



    @GetMapping("/students/with-class")
    public List<StudentWithClass> getStudentsWithClass() {
        return studentService.selectAllWithClass();
    }


    @GetMapping("/classes/{id}/students")
    public StudentClassDetail getClassWithStudents(@PathVariable("id") Integer id) {

        StudentClassDetail result = studentClassService.selectByIdWithStudents(id);

        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "班级不存在，id=" + id);
        }

        return result;
    }



    @PutMapping("/students/{studentId}/class")
    public Map<String, Object> transferStudentClass(
            @PathVariable("studentId") Integer studentId,
            @Valid @RequestBody ChangeStudentClassRequest request) {

        studentService.transferClass(studentId, request.getClassId());

        return Map.of(
                "message", "学生转班成功",
                "studentId", studentId,
                "classId", request.getClassId()
        );
    }




    @PutMapping("/classes/{classId}/students")
    public Map<String, Object> transferStudentsToClass(
            @PathVariable("classId") Integer classId,
            @Valid @RequestBody BatchTransferRequest request) {

        studentService.transferStudentsToClass(request.getStudentIds(), classId);

        return Map.of(
                "message", "批量转班成功",
                "classId", classId,
                "studentIds", request.getStudentIds(),
                "count", request.getStudentIds().size()
        );
    }

}

