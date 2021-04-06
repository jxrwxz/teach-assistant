package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Assignment;
import jxrwxz.teachassistant.data.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepo;

    @GetMapping("assignmentDetail")
    public ModelAndView chat(){
        ModelAndView modelAndView=new ModelAndView("assignmentDetail");
        return modelAndView;
    }


}
