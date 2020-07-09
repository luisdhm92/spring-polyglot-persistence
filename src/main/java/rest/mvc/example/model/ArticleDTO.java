package rest.mvc.example.model;

import lombok.Data;
import rest.mvc.example.domain.Topic;

import java.util.List;

@Data
public class ArticleDTO {
    private Long id;

    private String name;

    private Double rating;

    private String fullTextContent;

    private List<Topic> topics;

    private SimpleAppUserDTO author;
}
