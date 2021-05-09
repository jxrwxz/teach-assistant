package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Assignment_Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface AssignmentStudentRepository extends CrudRepository<Assignment_Student,Long> {

    @Query(value="select assignment_student.ID, assignment_student.STUDENT_ID, assignment_student.ASSIGNMENT_ID, assignment.COURSE_ID, assignment.`NAME`, assignment.SERIAL_NUMBER, assignment.EXPIRE_AT,course.`NAME`as COURSE_NAME from (assignment left join course on assignment.COURSE_ID=course.ID) inner join assignment_student on assignment.ID=assignment_student.ASSIGNMENT_ID and assignment_student.STUDENT_ID=?1 where assignment_student.FINISHED='0'",nativeQuery = true)
    List<Assignment_Student> findAllUndoAssignmentsByStudentId(Long studentId);

    @Modifying
    @Transactional
    @Query(value = "insert into assignment_student (STUDENT_ID,ASSIGNMENT_ID,FINISHED) values (?1,?2,?3)",nativeQuery = true)
    void saveAssignment(Long studentId,Long assignmentId,Integer finished);

    @Query(value="select assignment_student.ID, assignment_student.STUDENT_ID, assignment_student.ASSIGNMENT_ID, assignment.COURSE_ID, assignment.`NAME`, assignment.SERIAL_NUMBER, assignment.EXPIRE_AT,course.`NAME`as COURSE_NAME from (assignment left join course on assignment.COURSE_ID=course.ID) inner join assignment_student on assignment.ID=assignment_student.ASSIGNMENT_ID and assignment_student.STUDENT_ID=?1 where assignment_student.FINISHED='1'",nativeQuery = true)
    List<Assignment_Student> findAllFinishedAssignmentsByStudentId(Long studentId);

    @Modifying
    @Transactional
    @Query(value = "update assignment_student set FINISHED='1' where ID=?1",nativeQuery = true)
    void finishedAssignment(Long assignmentStudentId);

}