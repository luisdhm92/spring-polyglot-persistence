package rest.mvc.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rest.mvc.example.domain.Article;
import rest.mvc.example.model.ArticleDTO;
import rest.mvc.example.model.ArticleListDTO;
import rest.mvc.example.service.ArticleService;
import rest.mvc.example.service.RabbitMQSender;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ArticleController.BASE_URL)
public class ArticleController {
    public static final String BASE_URL = "/article";
    @Autowired
    RabbitMQSender rabbitMQSender;
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/add")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleDTO newArticle) {
        rabbitMQSender.send(newArticle);
        ArticleDTO savedArticle = articleService.addArticle(newArticle);
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedArticle.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<ArticleListDTO> findArticleByName(@PathVariable("name") String articleName) {
        List<ArticleDTO> fetchedArticles = articleService.getByName(articleName);
        return ResponseEntity.ok(new ArticleListDTO(fetchedArticles));
    }
}
