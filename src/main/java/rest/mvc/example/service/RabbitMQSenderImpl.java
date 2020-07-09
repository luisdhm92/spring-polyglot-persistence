package rest.mvc.example.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rest.mvc.example.model.ArticleDTO;

@Service
public class RabbitMQSenderImpl implements RabbitMQSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    public void send(ArticleDTO articleDTO) {
        rabbitTemplate.convertAndSend(exchange, "", articleDTO);
    }
}
