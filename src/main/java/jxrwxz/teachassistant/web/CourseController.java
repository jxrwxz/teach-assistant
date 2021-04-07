package jxrwxz.teachassistant.web;


import jxrwxz.teachassistant.Course;
import jxrwxz.teachassistant.Teacher;
import jxrwxz.teachassistant.data.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseRepository courseRepo;

    @ResponseBody
    @GetMapping("/course/recent")
    public List<Course> recentCourses() {
//        Pageable pageable= PageRequest.of(0,16);
        return courseRepo.findRecentCourses();
    }

    @ResponseBody
    @GetMapping("/course/all")
    public List<Course> findAll(){
        List<Course> list = courseRepo.findCourses();
        return list;
    }

    @ResponseBody
    @GetMapping("/course/questCourse")
    public List<Course> findQuestCourse(){
        List<Course> list = courseRepo.findQuestCourse();
        return list;
    }


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

    @ResponseBody
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

    @ResponseBody
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
    public String courseList(){
        return "courseList";
    }

    @GetMapping("/courseQuest")
    public String courseQuest(){
        return "courseQuest";
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

    @ResponseBody
    @PostMapping("/course")
    @ResponseStatus(HttpStatus.CREATED)
    public Course addCourse(HttpServletRequest request) {
        Teacher teacher = (Teacher) request.getSession(false).getAttribute("login");
        String teacherName = teacher.getName();
        String courseName = request.getParameter("courseName");
        String introduction = request.getParameter("introduction");
        Course course = new Course(teacherName, courseName, introduction);
        return courseRepo.save(course);
    }

    @GetMapping("mine")
    public ModelAndView mine() {
        ModelAndView modelAndView = new ModelAndView("mine");
        return modelAndView;
    }

    @GetMapping("assignmentDetail")
    public ModelAndView chat() {
        ModelAndView modelAndView = new ModelAndView("assignmentDetail");
        return modelAndView;
    }

    @GetMapping("teacherChat")
    public ModelAndView dd() {
        return new ModelAndView("teacherChat");
    }
}
