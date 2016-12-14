package poetsWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poetsWebsite.entity.Article;

/**
 * Created by Admin on 14.12.2016 г..
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
