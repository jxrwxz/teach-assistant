package jxrwxz.teachassistant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

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





}
