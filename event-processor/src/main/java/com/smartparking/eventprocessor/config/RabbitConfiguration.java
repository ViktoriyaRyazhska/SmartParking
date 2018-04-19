package com.smartparking.eventprocessor.config;

import com.smartparking.eventprocessor.config.properties.RabbitProperties;
import org.springframework.amqp.core.AmqpAdmin;
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
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(@Autowired ConnectionFactory connectionFactory,
                                         @Autowired MessageConverter messageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
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

    @Bean
    public Queue spotNumberChangeQueue() {
        return new Queue(rabbitProperties.getSpotNumberChangeQueueName());
    }
}