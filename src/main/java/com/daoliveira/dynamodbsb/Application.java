package com.daoliveira.dynamodbsb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.amazonaws.services.dynamodbv2.util.TableUtils.TableNeverTransitionedToStateException;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, // No JPA
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableDynamoDBRepositories(basePackages = "com.daoliveira.dynamodbsb.repository")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner createTables(ConfigurableApplicationContext ctx, AmazonDynamoDB amazonDynamoDB,
			DynamoDBMapper dynamoDBMapper) {
		return (args) -> {
			ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
					false);
			scanner.addIncludeFilter(new AnnotationTypeFilter(DynamoDBTable.class));
			scanner.findCandidateComponents(this.getClass().getPackageName()).forEach(bd -> {
				try {
					CreateTableRequest ctr = dynamoDBMapper
						.generateCreateTableRequest(Class.forName(bd.getBeanClassName()))
						.withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
					TableUtils.createTableIfNotExists(amazonDynamoDB, ctr);
					TableUtils.waitUntilActive(amazonDynamoDB, ctr.getTableName());
				} catch (TableNeverTransitionedToStateException | InterruptedException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		};
	}

	/*@Override
	public void run(String... args) throws Exception {
		dynamoDBMapper = new DynamoDBMapper(dynamoDB);

		// Creating table if doesn't exist
		CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Product.class);
		tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		TableUtils.createTableIfNotExists(dynamoDB, tableRequest);

		// Adding initial data
		//Product product = new Product(null, "Shirts Ma Long", "35865873645");
		//product = productRepository.save(product);
		//System.out.format("Product saved with id %s\n", product.getId());

		// Querying one product
		Optional<Product> productQueried = productRepository.findById("af05c6be-6fc7-450d-9481-441212a6272c");
		productQueried.ifPresentOrElse(
			p->System.out.format("Product retrieved: %s\n", p), 
			()->System.out.println("Product not found"));
		
		// Querying all products
		productRepository.findAll().forEach(
			p->System.out.format("Product retrieved: %s\n", p));

	}*/

}
