package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Assignment;
import jxrwxz.teachassistant.Assignment_Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssignmentRepository extends CrudRepository<Assignment,Long> {

    List<Assignment> findAllAssignmentsByTeacherId(Long teacherId);

    List<Assignment> findAllByCourseId(Long CourseId);

}
