package jxrwxz.teachassistant.web;


import com.sun.org.apache.xpath.internal.operations.Mod;
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
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

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

//    @ResponseBody
//    @GetMapping("/course/all")
//    public List<Course> findAll(){
//        List<Course> list = courseRepo.findCourses();
//        return list;
//    }

//    @ResponseBody
//    @GetMapping("/course/questCourse")
//    public List<Course> findQuestCourse(){
//        List<Course> list = courseRepo.findQuestCourse();
//        return list;
//    }


    @Transactional
    @PostMapping("/course/courseChange")
    public void editCourse(HttpServletRequest request,HttpServletResponse response) {
        String name = request.getParameter("name");
        String teacherName = request.getParameter("teacherName").toString();
        String introduction = request.getParameter("introduction").toString();
        long id = Integer.parseInt(request.getParameter("id").toString());
        try {
            courseRepo.updateCourse(name,teacherName,introduction,id);
            response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @ResponseBody
    @GetMapping("/course/coursePage")
    public Map<String,Object> getCourseList(HttpServletRequest request,HttpServletResponse response) {
        Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
        List<Course> courseList = courseRepo.queryAllByLimit((currentPage-1)*pageSize,currentPage*pageSize);
        long total = courseRepo.countCourse();
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("total", total);
        ret.put("rows", courseList);
        //System.out.println(ret);
        return ret;
    }

//    @ResponseBody
    @GetMapping("/course/questcoursePage")
    public Map<String,Object> getquestCourseList(HttpServletRequest request,HttpServletResponse response) {
        Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
        List<Course> courseList = courseRepo.questAllByLimit((currentPage-1)*pageSize,currentPage*pageSize);
        long total = courseRepo.countQuestCourse();
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("total", total);
        ret.put("rows", courseList);
        //System.out.println(ret);
        return ret;
    }

    @GetMapping("/courseList")
    public ModelAndView courseList(){
        return new ModelAndView("courseList");
    }

    @GetMapping("/courseQuest")
    public ModelAndView courseQuest(){
        return new ModelAndView("courseQuest");
    }

    @ResponseBody
    @PostMapping("/courseAgree")
    public void agreeCourse(@RequestParam(value = "ids[]") Long[] ids,HttpServletResponse response) throws IOException {
        try {
            for (Long id:ids) {
                courseRepo.agreeCourse(id);
            }
            response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @PostMapping("/courseDelete")
    @DeleteMapping
    public void deleteCourse(@RequestParam(value = "ids[]") Long[] ids,HttpServletResponse response) throws IOException {
        try {
            for (Long id:ids) {
                courseRepo.deleteById(id);
            }
            response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
