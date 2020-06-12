package com.example.dynamodbsb.repository;

import com.example.dynamodbsb.entity.Category;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CategoryRepository extends CrudRepository<Category, String> {
    
}