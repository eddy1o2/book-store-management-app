package com.book.store.management.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class LocalConfig {
    @Value("${aws.region}")
    private String region;

    @Bean
    public AmazonS3 amazonS3(@Value("${s3.endpoint}") String endpoint) {
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
        builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, this.region));
        builder.withPathStyleAccessEnabled(true);
        return builder.build();
    }

    @Bean
    public AmazonSQSAsync amazonSQS(@Value("${sqs.endpoint}") String endpoint) {
        AmazonSQSAsyncClientBuilder builder = AmazonSQSAsyncClientBuilder.standard();
        builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, this.region));
        return builder.build();
    }

    @Bean
    public AmazonDynamoDBAsync amazonDynamoDB(@Value("${dynamodb.endpoint}") String endpoint) {
        AmazonDynamoDBAsyncClientBuilder builder = AmazonDynamoDBAsyncClientBuilder.standard();
        builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, this.region));
        return builder.build();
    }

}
