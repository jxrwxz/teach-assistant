package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Long>{

    List<Course> findByName(String name);

    List<Course> findByTeacherName(String teacherName);

    @Query(value = "select * from course ORDER BY ID desc LIMIT 16",nativeQuery = true)
    List<Course> findRecentCourses();
}
