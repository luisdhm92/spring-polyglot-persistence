package rest.mvc.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.mvc.example.domain.Article;

@RestController
@RequestMapping(ElasticController.BASE_URL)
public class ElasticController {
    public static final String BASE_URL = "/elastic";

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @PutMapping("/clear-indices")
    public ResponseEntity<String> clearIndices() {
        elasticsearchTemplate.deleteIndex(Article.class);
        elasticsearchTemplate.createIndex(Article.class);
        elasticsearchTemplate.putMapping(Article.class);
        elasticsearchTemplate.refresh(Article.class);

        return ResponseEntity.ok("Indices cleared");
    }
}

