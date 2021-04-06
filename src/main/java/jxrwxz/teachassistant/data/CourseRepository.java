package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Long>{


    @Query(value = "select * from course ORDER BY ID desc LIMIT 16",nativeQuery = true)
    List<Course> findRecentCourses();

    @Query(value= "select * from course order by NUMBER_OF_STUDENTS desc LIMIT 16",nativeQuery = true)
    List<Course> findPopularCourses();

    List<Course> findAllByTeacherId(Long teacherId);
}
