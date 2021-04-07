package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Assignment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssignmentRepository extends CrudRepository<Assignment,Long> {

    List<Assignment> findAllAssignmentsByTeacherId(Long teacherId);

    List<Assignment> findAllByCourseId(Long CourseId);
}
