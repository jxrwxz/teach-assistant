package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.data.StudentRepository;
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

    @PostMapping("/login")
    public String processLogin(HttpServletRequest request){
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        Student student=stuRepo.findByNameAndPassword(name,password);
        if(student!=null){
            request.getSession().setAttribute("login",student);
            request.getSession().setAttribute("user","student");
            return "home";
        }
        return "login";
    }
    @GetMapping("/login")
    public String studentLogin(){
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

}
