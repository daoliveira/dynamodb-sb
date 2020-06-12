package com.example.dynamodbsb.repository;

import com.example.dynamodbsb.entity.Promotion;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PromotionRepository extends CrudRepository<Promotion, String> {
    
}