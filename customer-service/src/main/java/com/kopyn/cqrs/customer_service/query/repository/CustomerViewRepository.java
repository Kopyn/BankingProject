package com.kopyn.cqrs.customer_service.query.repository;

import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerViewRepository extends ReactiveMongoRepository<CustomerView, String> {
}
