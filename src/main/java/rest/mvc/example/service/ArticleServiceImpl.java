package rest.mvc.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.mvc.example.domain.AppUser;
import rest.mvc.example.domain.Article;
import rest.mvc.example.mapper.ArticleMapper;
import rest.mvc.example.model.ArticleDTO;
import rest.mvc.example.repository.ArticleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleMapper articleMapper;

    public List<ArticleDTO> getByName(String name) {

        return articleRepository.findByName(name).stream().map(article -> {
            ArticleDTO articleDTO = articleMapper.articleToArticleDTO(article);
            return articleDTO;
        }).collect(Collectors.toList());
    }

    public List<ArticleDTO> getByRatingInterval(Double start, Double end) {
        return articleRepository.findByRatingBetween(start, end).stream().map(article -> {
            ArticleDTO articleDTO = articleMapper.articleToArticleDTO(article);
            return articleDTO;
        }).collect(Collectors.toList());
    }

    public ArticleDTO addArticle(ArticleDTO article) {
        Article target = articleMapper.articleDTOToArticle(article);
        Article savedArticle = articleRepository.save(target);
        return articleMapper.articleToArticleDTO(savedArticle);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public List<ArticleDTO> findByAuthor(AppUser author) {
        return articleRepository.findByAuthor(author).stream().map(article -> {
            ArticleDTO articleDTO = articleMapper.articleToArticleDTO(article);
            return articleDTO;
        }).collect(Collectors.toList());
    }
}
