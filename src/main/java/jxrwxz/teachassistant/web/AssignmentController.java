package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Assignment;
import jxrwxz.teachassistant.Assignment_Student;
import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.data.AssignmentRepository;
import jxrwxz.teachassistant.data.AssignmentStudentRepository;
import jxrwxz.teachassistant.data.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepo;

    @Autowired
    private AssignmentStudentRepository assignmentStudentRepository;

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

    @GetMapping("/allUndoAssignments")
    public List<Assignment_Student> allUndoAssignments(HttpServletRequest request){
        Student temp=(Student)(request.getSession(false).getAttribute("login"));
        Long studentId=temp.getId();
        List<Assignment_Student> assignment_students=assignmentStudentRepository.findAssignmentsByStudentId(studentId);
        Date now=new Date();
        Iterator<Assignment_Student> iterator=assignment_students.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getExpireAt().before(now)) {
                iterator.remove();
            }
        }


        return assignment_students;

    }


}
