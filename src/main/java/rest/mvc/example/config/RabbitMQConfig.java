package rest.mvc.example.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    String exchange;

    @Value("${rabbitmq.logging.queue}")
    String loggingQueue;

    @Value("${rabbitmq.processing.queue}")
    String processingQueue;


    @Bean
    Queue loggingQueue() {
        return new Queue(loggingQueue, false);
    }

    @Bean
    Queue processingQueue() {
        return new Queue(processingQueue, false);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(exchange);
    }

    @Bean
    Binding loggingBinding(Queue loggingQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(loggingQueue).to(fanoutExchange);
    }

    @Bean
    Binding processingBinding(Queue processingQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(processingQueue).to(fanoutExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
