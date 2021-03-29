package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Teacher;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TeacherRepository extends CrudRepository<Teacher,Long> {
    Teacher findByNameAndPassword(String name,String password);

    @Transactional
    @Modifying
    @Query(value="update teacher set PROFILE=?1 where ID=?2",nativeQuery = true)
    void updateTeaProfile(String teaProfile,Long ID);

    Optional<Teacher> findById(Long ID);
}
