package rest.mvc.example.service;

import rest.mvc.example.model.ArticleDTO;

public interface RabbitMQSender {

    void send(ArticleDTO articleDTO);

}
