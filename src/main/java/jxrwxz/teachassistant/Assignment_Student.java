package jxrwxz.teachassistant;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Assignment_Student {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="ASSIGNMENT_ID")
    private Long assignmentId;

    @Column(name="STUDENT_ID")
    private Long studentId;

    @Column(name="COURSE_ID")
    private Long courseId;

    @Column(name="NAME")
    private String name;

    @Column(name="SERIAL_NUMBER")
    private Integer serialNumber;

    @Column(name="COURSE_NAME")
    private String courseName;

    private Date expireAt;

    public Assignment_Student(){}
}
