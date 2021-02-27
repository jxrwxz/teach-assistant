package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,Long> {
    Student findByNameAndPassword(String name, String password);
}
