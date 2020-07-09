package rest.mvc.example.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rest.mvc.example.domain.AppUser;
import rest.mvc.example.domain.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {
    List<Article> findByName(String name);

    List<Article> findByRatingBetween(Double start, Double end);

    List<Article> findByAuthor(AppUser author);
}
