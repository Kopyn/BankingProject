package com.kopyn.cqrs.customer_service.query.repository;

import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerViewRepository extends ReactiveMongoRepository<CustomerView, String> {
    Mono<CustomerView> findByCustomerId(String uuid);
}
