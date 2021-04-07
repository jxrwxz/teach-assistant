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
import java.util.List;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepo;

    @PostMapping("/allAssignments")
    public List<Assignment> allAssignments(HttpServletRequest request){
        Long courseId=Long.parseLong(request.getParameter("courseId"));
        List<Assignment> assignments=assignmentRepo.findAllByCourseId(courseId);
        Date now=new Date();
        for(Assignment a:assignments){
            if(a.getExpireDate().after(now)) {
                a.setAnswer("");
            }
        }
        return assignments;
    }


}
