package com.kopyn.cqrs.customer_service.query.repository;

import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface CustomerViewRepository extends MongoRepository<CustomerView, String> {
}
