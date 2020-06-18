package com.example.dynamodbsb;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.daoliveira.dynamodbsb.repository.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class ProductIntegrationTest extends AbstractIntegrationTest {
    
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AmazonDynamoDB dynamoDB;

    @Autowired
    ProductRepository repository;

    @Before
    public void clear() {
        //Clear all items from the table before every test
        repository.findAll().forEach(p -> repository.delete(p));
    }

    @Test
    public void productTableExists() {
        assertThat(dynamoDB.listTables().getTableNames()).contains("product");
    }
    
}