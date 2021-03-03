package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher,Long> {
    Teacher findByNameAndPassword(String name,String password);
}
