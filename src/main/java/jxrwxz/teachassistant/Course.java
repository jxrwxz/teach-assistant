package jxrwxz.teachassistant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.String;

import jxrwxz.teachassistant.data.CourseRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="CREATED_AT")
    private Date createdAt;

    @NotNull
    private String name;

    @NotNull
    private String teacherName;

    @NotNull
    private Integer proved;

    @NotNull
    private Integer numberOfStudents;

    private String introduction;

    public void addNumberOfStudents(){
        numberOfStudents++;
    }

    @PrePersist
    void createdAt(){
        this.createdAt=new Date();
    }

    public Course(String teacherName,String name,String introduction){
        this.teacherName=teacherName;
        this.name=name;
        this.introduction=introduction;
        this.numberOfStudents=new Integer(0);
        this.proved=new Integer(0);
    }

    public Course(){}

    /*@Autowired
    private CourseRepository courseRepo;
    public List<Course> queryAllByLimit(int offset, int limit){
        return courseRepo.queryAllByLimit((offset-1)*limit,limit);
    };*/



}
