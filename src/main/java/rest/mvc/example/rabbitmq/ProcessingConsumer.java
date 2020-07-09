package rest.mvc.example.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rest.mvc.example.model.ArticleDTO;

import java.util.List;

@Component
public class ProcessingConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ProcessingConsumer.class);

    @RabbitListener(queues = "${rabbitmq.processing.queue}")
    public void receiveMessage(ArticleDTO articleDTO) throws Exception {
        logger.info("Received message for processing: " + articleDTO);
        logger.info("Start processing");
        List<String> emails = SimpleDataExtractor.extractEmails(articleDTO.getFullTextContent());
        emails.forEach(System.out::println);
        logger.info("End processing");
    }
}