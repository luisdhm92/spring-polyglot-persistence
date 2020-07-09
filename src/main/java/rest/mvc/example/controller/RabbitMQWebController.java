package rest.mvc.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.mvc.example.model.ArticleDTO;
import rest.mvc.example.service.RabbitMQSender;

@RestController
@RequestMapping(RabbitMQWebController.BASE_URL)
public class RabbitMQWebController {
    public static final String BASE_URL = "/rabbitmq";

    @Autowired
    RabbitMQSender rabbitMQSender;

    @PostMapping(value = "/producer")
    public ResponseEntity<String> objectProducer(@RequestBody ArticleDTO newArticle) {

        rabbitMQSender.send(newArticle);

        return ResponseEntity.ok("Message with object sent to the RabbitMQ Successfully");
    }
}
