package poetsWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poetsWebsite.entity.Category;

/**
 * Created by Admin on 21.12.2016 г..
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
