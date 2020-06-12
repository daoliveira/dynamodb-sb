package com.example.dynamodbsb.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String endpoint;

    @Value("${amazon.dynamodb.region}")
    private String region;

    @Value("${amazon.aws.accesskey}")
    private String accessKey;

    @Value("${amazon.aws.secretkey}")
    private String secretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBAsyncClientBuilder.standard()
            .withEndpointConfiguration(new EndpointConfiguration(endpoint, region))
            .withCredentials(awsCredentialsProvider())
            .build();
        return amazonDynamoDB;
    }
    
    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }
}