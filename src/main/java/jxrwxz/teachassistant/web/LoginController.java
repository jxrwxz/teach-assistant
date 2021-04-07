package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Admin;
import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.Teacher;
import jxrwxz.teachassistant.data.AdminRepository;
import jxrwxz.teachassistant.data.StudentRepository;
import jxrwxz.teachassistant.data.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping
@CrossOrigin(origins="*")
public class LoginController {

    @Autowired
    private StudentRepository stuRepo;

    @Autowired
    private TeacherRepository teaRepo;

    @Autowired
    private AdminRepository admRepo;

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
        if(identity.equals("admin")){
            Admin admin=admRepo.findByNameAndPassword(name,password);
            if(admin!=null){
                request.getSession().setAttribute("login",admin);
                request.getSession().setAttribute("identity","admin");
                return "adm";
            }
        }
        return "login";
    }


    @GetMapping("/login")
    public String userLogin(){
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/adm")
    public String adm(){
        return "adm";}

}
