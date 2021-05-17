package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.Assignment;
import jxrwxz.teachassistant.Assignment_Student;
import jxrwxz.teachassistant.Course;
import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private AssignmentRepository assignmentRepo;

    @Autowired
    private CourseStudentRepository courseStudentRepo;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentStudentRepository assignmentStudentRepo;

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

    //查看学生自己参加的所有课程
    @GetMapping("/stuMyCourses")
    public List<Course> stuMyCourses(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        if(session!=null){
            return courseRepo.findAllByStudentId(student.getId());
        }
        return null;
    }
    //查看学生自己参加的所有课程
    @GetMapping("/preCourses")
    public List<Course> preCourses(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        if(session!=null){
            return courseRepo.findAllByNotStudentId(student.getId());
        }
        return null;
    }

    //参加课程
    @PostMapping("/attendCourses")
    public String attendCourses(HttpServletRequest request) throws ParseException {
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        Long studentId=student.getId();
        Long courseId=Long.parseLong(request.getParameter("courseId"));
        String teacherName=request.getParameter("teacherName");
        if(courseStudentRepo.findRecordByCourseIdAndStudentId(courseId,student.getId())==null){
            courseStudentRepo.attendcourses(courseId,studentId,teacherName,student.getName());
            List<Assignment> assignments=assignmentRepository.findAllByCourseId(courseId);
            Iterator iterator=assignments.iterator();
            Assignment assignmentTemp;
            while(iterator.hasNext()){
                assignmentTemp=(Assignment)(iterator.next());
                assignmentStudentRepo.saveAssignment(studentId,assignmentTemp.getId(),0);
            }
            return "{\"content\":\"参加课程成功\"}";
        }
        else return "{\"content\":\"不能重复参加课程\"}";
    }

    //退出课程
    @PostMapping("/exitCourses")
    public void exitCourses(HttpServletRequest request) throws ParseException {
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        Long courseId=Long.parseLong(request.getParameter("courseId"));
        courseRepo.exitcourses(courseId,student.getId());
    }

    //修改密码
    @PostMapping("/changePassword")
    public void changePassword(HttpServletRequest request) throws ParseException {
        HttpSession session=request.getSession(false);
        Student student=(Student)(session.getAttribute("login"));
        String password=request.getParameter("password");
        studentRepo.changePassword(password,student.getId());
    }

    //添加学生
    @GetMapping("/addStudent")
    public ModelAndView addStudent(){
        return new ModelAndView("addStudent");
    }

    @ResponseBody
    @GetMapping("/student/queststudentPage")
    public Map<String,Object> getquestCourseList(HttpServletRequest request, HttpServletResponse response) {
        Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
        List<Student> studentList = studentRepo.findAll((currentPage-1)*pageSize,currentPage*pageSize);
        long total = studentRepo.count();
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("total", total);
        ret.put("rows", studentList);
        //System.out.println(ret);
        return ret;
    }

    @ResponseBody
    @PostMapping("/studentDelete")
    @DeleteMapping
    public void deleteStudent(@RequestParam(value = "ids[]") Long[] ids,HttpServletResponse response) throws IOException {
        try {
            for (Long id:ids) {
                studentRepo.deleteById(id);
            }
            response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @ResponseBody
    @Transactional
    @PostMapping("/student/studentChange")
    public void editCourse(HttpServletRequest request,HttpServletResponse response) {
        String name = request.getParameter("name");
        String password = request.getParameter("password").toString();
        long id = Integer.parseInt(request.getParameter("id").toString());
        try {
            studentRepo.updateStudent(name,password,id);
            response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/import")
    public void exImport(@RequestParam(value = "filename") MultipartFile file, HttpSession session,HttpServletResponse response) throws IOException {

        boolean a = false;

        String fileName = file.getOriginalFilename();

        try {
            a = studentRepo.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("addStudent");
    }
}
