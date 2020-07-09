package rest.mvc.example.service;

import rest.mvc.example.domain.AppUser;
import rest.mvc.example.model.ArticleDTO;

import java.util.List;

public interface ArticleService {
    List<ArticleDTO> getByName(String name);

    List<ArticleDTO> getByRatingInterval(Double start, Double end);

    ArticleDTO addArticle(ArticleDTO article);

    void deleteArticle(Long id);

    List<ArticleDTO> findByAuthor(AppUser author);
}
