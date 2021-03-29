package jxrwxz.teachassistant.web;


import jxrwxz.teachassistant.Course;
import jxrwxz.teachassistant.Teacher;
import jxrwxz.teachassistant.data.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseRepository courseRepo;

    @GetMapping("/course/recent")
    public List<Course> recentCourses(){
//        Pageable pageable= PageRequest.of(0,16);
        return courseRepo.findRecentCourses();
    }

    @GetMapping("/course/popular")
    public List<Course> popularCourses(){
        return courseRepo.findPopularCourses();
    }

    @PostMapping("/course")
    @ResponseStatus(HttpStatus.CREATED)
    public Course addCourse(HttpServletRequest request){
        Teacher teacher=(Teacher)request.getSession(false).getAttribute("login");
        String teacherName=teacher.getName();
        String courseName=request.getParameter("courseName");
        String introduction=request.getParameter("introduction");
        Course course=new Course(teacherName,courseName,introduction);
        return courseRepo.save(course);
    }

    @GetMapping("/courseDetail/{courseId}")
    public String courseDetail(@PathVariable("courseId") Long courseId){
        return "{\"courseId\":\"" + courseId + "\"}";
    }

    @GetMapping("assignmentDetail")
    public ModelAndView chat(){
        ModelAndView modelAndView=new ModelAndView("assignmentDetail");
        return modelAndView;
    }

    @GetMapping("teacherChat")
    public ModelAndView dd(){
        return new ModelAndView("teacherChat");
    }
}
