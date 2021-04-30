package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface StudentRepository extends CrudRepository<Student,Long> {
    Student findByNameAndPassword(String name, String password);

    @Transactional
    @Modifying
    @Query(value = "update student set PASSWORD=?1 where ID=?2 ",nativeQuery = true)
    public void changePassword(String password,long sid);

}
