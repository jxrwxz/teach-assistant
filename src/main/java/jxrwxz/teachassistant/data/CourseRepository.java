package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Long>,PagingAndSortingRepository<Course,Long> {

    @Query(value="select * from course where NAME=?1 ",nativeQuery = true)
    List<Course> findByName(String name);

    @Query(value="select NAME from course where ID=?1",nativeQuery = true)
    String findCourseNameById(Long Id);

    @Query(value = "select * from course ORDER BY ID desc LIMIT 9",nativeQuery = true)
    List<Course> findRecentCourses();

    @Query(value = "select * from course where PROVED=1 ",nativeQuery = true)
    List<Course> findCourses();

    @Query(value = "select * from course where PROVED=0 ",nativeQuery = true)
    List<Course> findQuestCourse();

    @Query(value = "SELECT COUNT(*) FROM course where PROVED=1",nativeQuery = true)
    Long countCourse();

    @Query(value = "SELECT COUNT(*) FROM course where PROVED=0",nativeQuery = true)
    Long countQuestCourse();

    @Transactional
    @Modifying
    @Query(value = "update course set PROVED=1 where ID=?1 ",nativeQuery = true)
    public void agreeCourse(long id);

    @Transactional
    @Modifying
    @Query(value = "update course set NAME=?1,TEACHER_NAME=?2,INTRODUCTION=?3  where ID=?4 ",nativeQuery = true)
    public void updateCourse(String name,String teacherName,String introduction,long id);

    @Transactional
    @Modifying
    @Query(value = "select * from course where PROVED=1 limit ?1,?2 ",nativeQuery = true)
    public List<Course> queryAllByLimit(int offset,int limit);

    @Transactional
    @Modifying
    @Query(value = "select * from course where PROVED=0 limit ?1,?2 ",nativeQuery = true)
    public List<Course> questAllByLimit(int offset,int limit);

    @Query(value= "select * from course order by NUMBER_OF_STUDENTS desc LIMIT 9",nativeQuery = true)
    List<Course> findPopularCourses();

    List<Course> findAllByTeacherId(Long teacherId);

    @Transactional
    @Modifying
    @Query(value = "select * from course,course_student where STUDENT_ID=?1 AND course.ID=course_student.COURSE_ID",nativeQuery = true)
    List<Course> findAllByStudentId(Long studentId);

    @Transactional
    @Modifying
    @Query(value = "select * from course where course.ID NOT IN(SELECT COURSE_ID FROM course_student where STUDENT_ID=?1)",nativeQuery = true)
    List<Course> findAllByNotStudentId(Long studentId);


    @Transactional
    @Modifying
    @Query(value = "delete from course_student where COURSE_ID=?1 and STUDENT_ID=?2",nativeQuery = true)
    void exitcourses(long cid,long sid);


    @Query(value="select TEACHER_NAME from course where course.ID=?1",nativeQuery = true)
    String findTeacherNameByCourseId(Long courseId);

}
