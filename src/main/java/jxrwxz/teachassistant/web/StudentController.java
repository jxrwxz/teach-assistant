package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Assignment;
import jxrwxz.teachassistant.Assignment_Student;
import jxrwxz.teachassistant.Course;
import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
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

    @Autowired
    private CourseStudentRepository courseStudentRepo;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentStudentRepository assignmentStudentRepo;

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
    public String attendCourses(HttpServletRequest request) throws ParseException {
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        Long studentId=student.getId();
        Long courseId=Long.parseLong(request.getParameter("courseId"));
        String teacherName=request.getParameter("teacherName");
        if(courseStudentRepo.findRecordByCourseIdAndStudentId(courseId,student.getId())==null){
            courseStudentRepo.attendcourses(courseId,studentId,teacherName,student.getName());
            List<Assignment> assignments=assignmentRepository.findAllByCourseId(courseId);
            Iterator iterator=assignments.iterator();
            Assignment assignmentTemp;
            while(iterator.hasNext()){
                assignmentTemp=(Assignment)(iterator.next());
                assignmentStudentRepo.saveAssignment(studentId,assignmentTemp.getId(),0);
            }
            return "{\"content\":\"参加课程成功\"}";
        }
        else return "{\"content\":\"不能重复参加课程\"}";
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
