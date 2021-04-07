package jxrwxz.teachassistant.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class StudentController {

    private Long teacherId;

    @GetMapping("/contactTea/{teacherId}")
    public ModelAndView contactTea(@PathVariable("teacherId") Long teacherId){
        this.teacherId=teacherId;
        return new ModelAndView("contactTea");
    }

    @GetMapping("/getContactTeaId")
    public String getContactTeaId(){
        return "{\"teacherId\":\"" + teacherId + "\"}";
    }
}
