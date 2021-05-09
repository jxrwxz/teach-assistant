package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Course_Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CourseStudentRepository extends CrudRepository<Course_Student,Long> {

    @Query(value = "select * from course_student where course_student.COURSE_ID=?1",nativeQuery = true)
    List<Course_Student> findAllCommentsByCourseId(Long courseId);

    @Query(value="select course_student.COMMENT from course_student where course_student.COURSE_ID=?1 and course_student.STUDENT_ID=?2",nativeQuery = true)
    String findByCourseIdAndStudentId(Long courseId,Long studentId);

    @Transactional
    @Modifying
    @Query(value="update course_student set course_student.COMMENT=?1,course_student.COMMENT_IMAGE=?2 where course_student.COMMENT=?3 and course_student.COMMENT_IMAGE=?4",nativeQuery = true)
    void updateCommentByCourseIdAndStudentId(String commentContent,String commentImage,Long courseId,Long studentId);


    @Query(value="select course_student.ID from course_student where course_student.COURSE_ID=?1 and course_student.STUDENT_ID=?2",nativeQuery = true)
    Long findRecordByCourseIdAndStudentId(Long courseId,Long studentId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO course_student(COURSE_ID,STUDENT_ID,TEACHER_NAME,STUDENT_NAME) VALUES (?1,?2,?3,?4) ",nativeQuery = true)
    void attendcourses(long courseId,long studentId,String teacherName,String studentName);

}
