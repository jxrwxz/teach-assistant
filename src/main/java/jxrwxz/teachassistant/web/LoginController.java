package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.Teacher;
import jxrwxz.teachassistant.data.StudentRepository;
import jxrwxz.teachassistant.data.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
@CrossOrigin(origins="*")
public class LoginController {

    @Autowired
    private StudentRepository stuRepo;

    @Autowired
    private TeacherRepository teaRepo;

    @PostMapping("/login")
    public String processLogin(HttpServletRequest request){
        String identity=request.getParameter("identity");
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        if(identity.equals("student")){
            Student student=stuRepo.findByNameAndPassword(name,password);
            if(student!=null){
                request.getSession().setAttribute("login",student);
                request.getSession().setAttribute("identity","student");
                return "home";
            }
        }
        if(identity.equals("teacher")){
            Teacher teacher=teaRepo.findByNameAndPassword(name,password);
            if(teacher!=null){
                request.getSession().setAttribute("login",teacher);
                request.getSession().setAttribute("identity","teacher");
                return "home";

            }
        }
        return "login";
    }
    @GetMapping("/login")
    public String userLogin(){
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if(session==null){
            return "login";
        }
        return "home";
    }

}
