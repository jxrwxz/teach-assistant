package jxrwxz.teachassistant;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Course_Student {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="STUDENT_ID")
    private Long studentId;

    @Column(name="COURSE_ID")
    private Long courseId;

    @Column(name="TEACHER_NAME")
    private String teacherName;

    @Column(name="STUDENT_NAME")
    private String studentName;

//    @Column(name="COURSE_NAME")
//    private String courseName;

    @Column(name="COMMENT")
    private String comment;

    @Column(name="COMMENT_IMAGE")
    private String commentImage;

}
