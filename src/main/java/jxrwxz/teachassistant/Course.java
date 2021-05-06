package jxrwxz.teachassistant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Long teacherId;

    @NotNull
    private Integer proved;

    @NotNull
    private Integer numberOfStudents;

    private String introduction;

    @Column(name="COMMENTS_NUMBER")
    private Integer commentsNumber;

    @Column(name="MATERIAL")
    private String material;

    public void addNumberOfStudents(){
        numberOfStudents++;
    }


    @PrePersist
    void createdAt(){
        this.createdAt=new Date();
        this.commentsNumber=new Integer(0);
    }

    public void addCommentsNumber(){
        commentsNumber++;
    }

    public Course(String teacherName,String name,String introduction,Long teacherId){
        this.teacherName=teacherName;
        this.name=name;
        this.introduction=introduction;
        this.numberOfStudents=new Integer(0);
        this.proved=new Integer(0);
        this.teacherId=teacherId;
    }

    public Course(){}

}
