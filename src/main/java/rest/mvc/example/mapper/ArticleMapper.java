package rest.mvc.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import rest.mvc.example.domain.Article;
import rest.mvc.example.model.ArticleDTO;

@Mapper
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDTO articleToArticleDTO(Article article);

    Article articleDTOToArticle(ArticleDTO articleDTO);

}
