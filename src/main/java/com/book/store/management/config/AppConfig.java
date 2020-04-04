package com.book.store.management.config;

import com.book.store.management.service.DynamoDBService;
import com.book.store.management.service.DynamoDBServiceImpl;
import com.book.store.management.service.S3Service;
import com.book.store.management.service.S3ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory() {
        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setStrictContentTypeMatch(false);
        factory.setMessageConverters(Collections.singletonList(messageConverter));
        return factory;
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(@Value("${sqs.maxNumberOfMessages}") int maxNumberOfMessages) {
        SimpleMessageListenerContainerFactory containerFactory = new SimpleMessageListenerContainerFactory();
        containerFactory.setMaxNumberOfMessages(maxNumberOfMessages);
        return containerFactory;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
