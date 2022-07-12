package com.oli.steven.spring.mongo.api.repository;

import com.oli.steven.spring.mongo.api.model.Salaries;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SalariesRepository extends MongoRepository<Salaries, Integer> {
}
