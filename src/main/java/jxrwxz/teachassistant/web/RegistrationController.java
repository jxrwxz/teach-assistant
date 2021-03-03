package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.Teacher;
import jxrwxz.teachassistant.data.StudentRepository;
import jxrwxz.teachassistant.data.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
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

    @Autowired
    private TeacherRepository teacherRepo;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void stuRegister(@RequestParam("name") String name,
                               @RequestParam("password") String password,
                               @RequestParam("phoneNumber") String phoneNumber,
                               @RequestParam("identity") String identity,
                               @RequestParam("schoolName") String schoolName){
        if(identity.equals("student")) {
            Student student = new Student(name, password, phoneNumber,schoolName);
            studentRepo.save(student);
        }else if(identity.equals("teacher")){
            Teacher teacher=new Teacher(name,password,phoneNumber,schoolName);
            teacherRepo.save(teacher);
        }
    }

    @GetMapping("/register")
    public ModelAndView stuRegisterForm(){
        ModelAndView modelAndView=new ModelAndView("register");
        return modelAndView;
    }

    @GetMapping("/loginOrNot")
    public String loginOrNot(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        String name="";
        if(session!=null) {
            String identity=(String)session.getAttribute("identity");
            if(identity.equals("student")) {
                Student student = (Student) session.getAttribute("login");
                name=student.getName();
                System.out.println(student.getName());
            }
            if(identity.equals("teacher")){
                Teacher teacher=(Teacher) session.getAttribute("login");
                System.out.println(teacher.getName());
                name=teacher.getName();
            }
        }

        return "{\"name\":\"" + name + "\"}";
    }

}

