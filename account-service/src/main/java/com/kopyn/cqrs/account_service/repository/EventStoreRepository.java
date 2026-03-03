package com.kopyn.cqrs.account_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventStoreRepository extends ReactiveMongoRepository<String, String> {
    Flux<String> findByAggregateId(String aggregateId);
}
