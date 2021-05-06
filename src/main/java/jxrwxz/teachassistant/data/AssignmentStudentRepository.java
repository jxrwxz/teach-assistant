package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Assignment_Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssignmentStudentRepository extends CrudRepository<Assignment_Student,Long> {

    @Query(value="select assignment_student.ID, assignment_student.STUDENT_ID, assignment_student.ASSIGNMENT_ID, assignment.COURSE_ID, assignment.`NAME`, assignment.SERIAL_NUMBER, assignment.EXPIRE_AT,course.`NAME`as COURSE_NAME from (assignment left join course on assignment.COURSE_ID=course.ID) inner join assignment_student on assignment.ID=assignment_student.ASSIGNMENT_ID and assignment_student.STUDENT_ID=?1 where assignment_student.FINISHED='0'",nativeQuery = true)
    List<Assignment_Student> findAssignmentsByStudentId(Long studentId);
}
