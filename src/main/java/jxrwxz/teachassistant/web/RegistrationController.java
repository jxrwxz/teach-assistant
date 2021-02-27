package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.data.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins="*")
public class RegistrationController {

    @Autowired
    private StudentRepository studentRepo;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Student stuRegister(@RequestParam("name") String name,
                               @RequestParam("password") String password,
                               @RequestParam("phoneNumber") String phoneNumber){
        Student student=new Student(name, password, phoneNumber);
        System.out.println(student.getName());
        return studentRepo.save(student);
    }

    @GetMapping("/register")
    public ModelAndView stuRegisterForm(){
        ModelAndView modelAndView=new ModelAndView("register");
        return modelAndView;
    }

    @GetMapping("/loginOrNot")
    public Student loginOrNot(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if(session!=null) {
            Student student = (Student) session.getAttribute("login");
            System.out.println(student.getName());
            return student;
        }
        return null;
    }

}

