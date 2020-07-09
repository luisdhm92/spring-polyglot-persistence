package rest.mvc.example.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "article-store", type = "article", shards = 1, replicas = 0)
public class Article {
    @Id
    private Long id;

    private String name;

    private Double rating;

    private String fullTextContent;

    @Field(type = FieldType.Nested)
    private List<Topic> topics;


    @Field(type = FieldType.Nested)
    private AppUser author;
}
