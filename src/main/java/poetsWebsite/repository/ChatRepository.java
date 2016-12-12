package poetsWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poetsWebsite.entity.Chat;

/**
 * Created by Admin on 12.12.2016 г..
 */
public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
