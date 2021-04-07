package jxrwxz.teachassistant.web;


import jxrwxz.teachassistant.Course;
import jxrwxz.teachassistant.Teacher;
import jxrwxz.teachassistant.data.CourseRepository;
import jxrwxz.teachassistant.data.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private TeacherRepository teaRepo;

    private Long courseId;

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
        Long teacherId=teacher.getId();
        String teacherName=teacher.getName();
        String courseName=request.getParameter("courseName");
        String introduction=request.getParameter("introduction");
        Course course=new Course(teacherName,courseName,introduction,teacherId);
        return courseRepo.save(course);
    }

    @GetMapping("/courseDetail/{courseId}")
    public ModelAndView coursePage(@PathVariable("courseId") Long courseId){
        this.courseId=courseId;
        ModelAndView modelAndView=new ModelAndView("courseDetail");
        return modelAndView;
//        return "{\"courseId\":\"" + courseId + "\"}";
    }

    @GetMapping("/courseDetail")
    public String courseDetail(){
        Optional<Course> courseTemp=courseRepo.findById(courseId);
        Course course=courseTemp.get();
        Long teacherId=course.getTeacherId();
        Teacher teacher=teaRepo.findById(teacherId).get();
        return "{\"name\":\"" + course.getName()
                +"\",\"introduction\":\"" + course.getIntroduction()
                +"\",\"numberOfStudents\":\"" + course.getNumberOfStudents()
                +"\",\"teacherName\":\"" + teacher.getName()
                +"\",\"teacherProfile\":\"" + teacher.getProfile()
                +"\",\"school\":\"" + teacher.getSchoolName()
                +"\",\"courseId\":\"" + course.getId()
                +"\",\"teacherId\":\"" + teacher.getId()
                + "\"}";
    }


    @GetMapping("teacherChat")
    public ModelAndView dd(){
        return new ModelAndView("teacherChat");
    }
}
