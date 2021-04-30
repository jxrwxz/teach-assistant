package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Course;
import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.data.AssignmentRepository;
import jxrwxz.teachassistant.data.CourseRepository;
import jxrwxz.teachassistant.data.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private AssignmentRepository assignmentRepo;

    private Long teacherId;

    @GetMapping("/contactTea/{teacherId}")
    public ModelAndView contactTea(@PathVariable("teacherId") Long teacherId){
        this.teacherId=teacherId;
        return new ModelAndView("contactTea");
    }

    @GetMapping("/getContactTeaId")
    public String getContactTeaId(){
        return "{\"teacherId\":\"" + teacherId + "\"}";
    }

    //查看学生自己参加的所有课程
    @GetMapping("/stuMyCourses")
    public List<Course> stuMyCourses(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        if(session!=null){
            return courseRepo.findAllByStudentId(student.getId());
        }
        return null;
    }
    //查看学生自己参加的所有课程
    @GetMapping("/preCourses")
    public List<Course> preCourses(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        if(session!=null){
            return courseRepo.findAllByNotStudentId(student.getId());
        }
        return null;
    }

    //参加课程
    @PostMapping("/attendCourses")
    public void attendCourses(HttpServletRequest request) throws ParseException {
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        Long courseId=Long.parseLong(request.getParameter("courseId"));
        String teacherName=request.getParameter("teacherName");
        String courseName=request.getParameter("courseName");
        courseRepo.attendcourses(courseId,student.getId(),teacherName,courseName,student.getName());
    }

    //退出课程
    @PostMapping("/exitCourses")
    public void exitCourses(HttpServletRequest request) throws ParseException {
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        Long courseId=Long.parseLong(request.getParameter("courseId"));
        courseRepo.exitcourses(courseId,student.getId());
    }

    //修改密码
    @PostMapping("/changePassword")
    public void changePassword(HttpServletRequest request) throws ParseException {
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        String password=request.getParameter("password");
        studentRepo.changePassword(password,student.getId());
    }
}
