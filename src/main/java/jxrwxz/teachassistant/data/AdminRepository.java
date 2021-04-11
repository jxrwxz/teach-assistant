package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Admin;
import org.springframework.data.repository.CrudRepository;


public interface AdminRepository extends CrudRepository<Admin,Long>{

    Admin findByNameAndPassword(String name, String password);
}
