package com.example.dynamodbsb;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.daoliveira.dynamodbsb.Application;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.GenericContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
    private static final int DYNAMO_PORT = 8000;
    @ClassRule
    public static GenericContainer dynamoDb =
        new GenericContainer<>("amazon/dynamodb-local")
            .withExposedPorts(DYNAMO_PORT);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            String endpoint = String.format("amazon.dynamodb.endpoint=http://%s:%s",
                dynamoDb.getContainerIpAddress(),
                dynamoDb.getMappedPort(DYNAMO_PORT));

            TestPropertyValues.of(endpoint).applyTo(configurableApplicationContext);
        }
    }
}