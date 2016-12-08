package poetsWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poetsWebsite.entity.Role;

/**
 * Created by Admin on 8.12.2016 г..
 */
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByName(String name);
}
