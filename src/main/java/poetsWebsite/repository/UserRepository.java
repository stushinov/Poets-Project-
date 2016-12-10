package poetsWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poetsWebsite.entity.User;

/**
 * Created by Admin on 8.12.2016 г..
 */
public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
