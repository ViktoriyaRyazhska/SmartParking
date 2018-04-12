package com.smartparking.config;

import com.smartparking.config.properties.RabbitProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Bean
    public AmqpAdmin amqpAdmin(@Autowired ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(@Autowired ConnectionFactory connectionFactory,
                                     @Autowired MessageConverter jsonMessageConverter) {
        final RabbitTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
        amqpTemplate.setMessageConverter(jsonMessageConverter);
        return amqpTemplate;
    }

    @Bean
    public Queue spotAddQueue() {
        return new Queue(rabbitProperties.getParkingAddQueueName());
    }

    @Bean
    public Queue spotDeleteQueue() {
        return new Queue(rabbitProperties.getSpotDeleteQueueName());
    }

    @Bean
    public Queue parkingAddQueue() {
        return new Queue(rabbitProperties.getParkingAddQueueName());
    }

    @Bean
    public Queue parkingDeleteQueue() {
        return new Queue(rabbitProperties.getParkingDeleteQueueName());
    }

    @Bean
    public Queue parkingTokenChangeQueue() {
        return new Queue(rabbitProperties.getParkingTokenChangeQueueName());
    }
}