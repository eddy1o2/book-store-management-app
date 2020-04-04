package com.book.store.management.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.QueueMessageVisibility;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MessageProcessorService implements InitializingBean {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private DynamoDBService dynamoDBService;

    @MessageMapping
    @SqsListener(value = "sqs.book.event", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void onMessage(@Payload S3EventNotification eventNotification,
                          @Header("Visibility") QueueMessageVisibility messageVisibility) throws Exception {

        if (eventNotification == null || eventNotification.getRecords() == null) {
            return;
        }
        log.info("Received {} objects.", eventNotification.getRecords().size());

        try {
            eventNotification.getRecords().forEach(record -> {
                String bucket = record.getS3().getBucket().getName();
                String key = record.getS3().getObject().getKey();
                log.info("Getting object from s3://{}/{}", bucket, key);

            });
        } catch (Exception e) {
            log.error("Cannot handle file", e);
            log.info("Set visibility of message back to 0");
            messageVisibility.extend(0).get(1, TimeUnit.SECONDS);
            throw e;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(s3Service, "s3Service must not be null");
        Assert.notNull(dynamoDBService, "dynamoDBService must not be null");
    }
}
