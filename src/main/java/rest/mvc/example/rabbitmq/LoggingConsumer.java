package rest.mvc.example.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rest.mvc.example.model.ArticleDTO;

@Component
public class LoggingConsumer {
    private static final Logger logger = LoggerFactory.getLogger(LoggingConsumer.class);

    @RabbitListener(queues = "${rabbitmq.logging.queue}")
    public void receiveMessage(ArticleDTO articleDTO) throws Exception {
        logger.info("Received message for logging: " + articleDTO);
    }
}
