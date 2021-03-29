package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Course;
import jxrwxz.teachassistant.Teacher;
import jxrwxz.teachassistant.data.CourseRepository;
import jxrwxz.teachassistant.data.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class TeacherController {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private TeacherRepository teacherRepository;

    //主页上点击我的后跳转页面，老师和学生的都包括了
    @GetMapping("mine")
    public ModelAndView mine(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if(session==null){
            return new ModelAndView("login");
        }
        String identity=(String)(session.getAttribute("identity"));
        ModelAndView modelAndView=null;
        if(identity.equals("student")) {
            modelAndView = new ModelAndView("stuMine");
        }else if(identity.equals("teacher")){
            modelAndView =new ModelAndView("teaMine");
        }
        return modelAndView;
    }
    //查看教师自己教的所有课程
    @GetMapping("teaMyCourses")
    public List<Course> teaMyCourses(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        Teacher teacher=(Teacher)(session.getAttribute("login"));
        if(session!=null){
            return courseRepo.findAllByTeacherName(teacher.getName());
        }
        return null;
    }

    @PostMapping("addTeaProfile")
    public void addTeaProfile(HttpServletRequest request,@RequestParam("teaProfile")String teaProfile){
        HttpSession session = request.getSession(false);
        Teacher teacher=(Teacher)(session.getAttribute("login"));
        teacherRepository.updateTeaProfile(teaProfile,teacher.getId());
    }
    @GetMapping("teaProfile")
    public String teaProfile(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        Teacher teacher=(Teacher)(session.getAttribute("login"));
        Teacher temp=(Teacher)(teacherRepository.findById(teacher.getId()).get());
        return "{\"teaProfile\":\""
                + temp.getProfile()
                +"\"}";
    }

}
